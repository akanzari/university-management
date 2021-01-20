import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { PoleEnum, Room, UpdateRoomRequest } from 'src/app/core/models';
import { RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { TSMap } from 'typescript-map';

@Component({
    templateUrl: './add-room.component.html',
    styleUrls: ['./add-room.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class RoomModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editRoom: Room;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;
    public disabledBloc: boolean = true;
    public inProgress: boolean = false;

    public disabledPeriod: TSMap<number, boolean> = new TSMap<number, boolean>();
    public disabledWeek: TSMap<number, boolean> = new TSMap<number, boolean>();

    public periods: any[];
    public weeks: any[];
    public sites$: string[];
    public blocs$: string[];

    public semesters$: Observable<any[]>;
    public seances$: Observable<any[]>;
    public days$: Observable<any[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.semesters$ = this.refService.getSemeters();
        this.seances$ = this.refService.getSeances();
        this.days$ = this.refService.getDays();
        this.disabledPeriod.set(0, true);
        this.disabledWeek.set(0, true);
        this.sites$ = Object.keys(PoleEnum).map(key => PoleEnum[key]);
        if (this.editRoom) {
            this.disabledBloc = false;
            this.form.patchValue({
                label: this.editRoom.label,
                capacity: this.editRoom.capacity,
                siteId: this.editRoom.pole,
                blocId: this.editRoom.bloc,
                startDate: this.editRoom.startDate ? this.formatDate(new Date(this.editRoom.startDate)) : null,
                endDate: this.editRoom.endDate ? this.formatDate(new Date(this.editRoom.endDate)) : null,
                startHour: this.editRoom.startHour ? this.editRoom.startHour.slice(0, 2) + ":" + this.editRoom.startHour.slice(2, 4) : null,
                endHour: this.editRoom.endHour ? this.editRoom.endHour.slice(0, 2) + ":" + this.editRoom.endHour.slice(2, 4) : null,
                reasonId: this.editRoom.reason.reasonRoomId
            })
        }
    }

    private formatDate(date) {
        const d = new Date(date);
        let month = '' + (d.getMonth() + 1);
        let day = '' + d.getDate();
        const year = d.getFullYear();
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        return [year, month, day].join('-');
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "La Salle " + this.form.get("label").value + " ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "La Salle " + this.form.get("label").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let disponibilities: CreateDisponibilityRequest[] = [];
            if (this.form.value.disponibilities) {
                this.form.value.disponibilities.forEach(element => disponibilities.push(new CreateDisponibilityRequest(element.day, element.seanceId, element.semesterId, element.weekId, element.periodId, element.motif)));
            }
            const form = this.form.value;
            let arg: CreateRoomRequest = new CreateRoomRequest(form.label, form.capacity, form.pole, form.blocId, disponibilities);
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            const form = this.form.value;
            let arg: UpdateRoomRequest = new UpdateRoomRequest(this.editRoom.classRoomId, form.label, form.capacity, form.blocId, form.siteId, form.startDate, form.endDate, form.startHour, form.endHour, form.reasonId);
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public onChangeSite(event): void {
        if (event) {
            this.disabledBloc = false;
            this.refService.getBlocs().subscribe(item => {
                let result = item.find(el => el.pole === Object.keys(PoleEnum)[Object.values(PoleEnum).indexOf(event)]);
                this.blocs$ = result.blocs;
            })
        } else {
            this.disabledBloc = true;
            this.form.get("blocId").setValue(null);
            this.blocs$ = [];
        }
    }

    public onChangeSemester(event, index) {
        if (event) {
            this.disabledPeriod.set(index, false);
            this.periods = event.periods;
        } else {
            this.disabledPeriod.set(index, true);
            const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
            assignClassControl.get("periodId").setValue(null);
        }
    }

    public onChangePeriod(event, index) {
        if (event) {
            this.disabledWeek.set(index, false);
            event.weeks.sort((a: any, b: any) => {
                return +new Date(a.startDate) - +new Date(b.startDate);
            });
            this.weeks = event.weeks;
        } else {
            this.disabledWeek.set(index, true);
            const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
            assignClassControl.get("weekId").setValue(null);
        }
    }

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    public addDisponibility(index) {
        this.disabledPeriod.set(index + 1, true);
        this.disabledWeek.set(index + 1, true);
        const disponibilityControl: FormArray = <FormArray>this.form.controls['disponibilities'];
        disponibilityControl.push(this.initDisponibilities());
    }

    public removeDisponibility(index: number): void {
        this.disabledPeriod.delete(index + 1);
        this.disabledWeek.delete(index + 1);
        const disponibilityControl: FormArray = <FormArray>this.form.controls['disponibilities'];
        if (disponibilityControl.length > 1) {
            disponibilityControl.removeAt(index);
        }
    }

    private initForm(): void {
        this.form = this.fb.group({
            label: [null, Validators.required],
            capacity: [null, Validators.required],
            pole: [null, Validators.required],
            blocId: [null, Validators.required],
            disponibilities: this.fb.array([
                this.initDisponibilities()
            ])
        })
    }

    private initDisponibilities(): FormGroup {
        return this.fb.group({
            semesterId: [null],
            periodId: [null],
            weekId: [null],
            day: [null],
            seanceId: [null],
            motif: [null]
        })
    }

}

export class CreateRoomRequest {
    public classRoomId: string;
    public label: string;
    public capacity: string;
    public pole: string;
    public bloc: string;
    public disponibilities: CreateDisponibilityRequest[];

    constructor(classRoomId: string, capacity: string, pole: any, bloc: string, disponibilities: CreateDisponibilityRequest[]) {
        this.classRoomId = classRoomId;
        this.label = classRoomId;
        this.capacity = capacity;
        this.pole = Object.keys(PoleEnum)[Object.values(PoleEnum).indexOf(pole)];
        this.bloc = bloc;
        this.disponibilities = disponibilities;
    }
}

export class CreateDisponibilityRequest {
    public day: string;
    public seanceId: string;
    public semesterId: string;
    public weekId: string;
    public periodId: string;
    public motif: string;

    constructor(day: string, seanceId: string, semesterId: string, weekId: string, periodId: string, motif: string) {
        this.day = day;
        this.seanceId = seanceId;
        this.semesterId = semesterId;
        this.weekId = weekId;
        this.periodId = periodId;
        this.motif = motif;
    }
}