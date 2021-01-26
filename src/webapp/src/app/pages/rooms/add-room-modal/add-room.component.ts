import { DatePipe } from '@angular/common';
import { Component, EventEmitter, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { PoleEnum } from 'src/app/core/models';
import { RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './add-room.component.html',
    styleUrls: ['./add-room.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class RoomModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;
    public disabledBloc: boolean = true;
    public inProgress: boolean = false;

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
        this.sites$ = Object.keys(PoleEnum).map(key => PoleEnum[key]);
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
            let result: CreateDisponibilityRequest[] = [];
            if (this.form.value.disponibilities) {
                this.form.value.disponibilities.forEach(element => {
                    result.push(new CreateDisponibilityRequest(element.startDate, element.endDate, +element.startHour.replace(":", ""), +element.endHour.replace(":", ""), element.motif));
                });
            }
            const form = this.form.value;
            let arg: CreateRoomRequest = new CreateRoomRequest(form.label, form.capacity, form.pole, form.blocId, result);
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: arg };
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


    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    public addDisponibility(index) {
        const disponibilityControl: FormArray = <FormArray>this.form.controls['disponibilities'];
        disponibilityControl.push(this.initDisponibilities());
    }

    public removeDisponibility(index: number): void {
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
            startDate: [null],
            endDate: [null],
            startHour: [null],
            endHour: [null],
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
    public startDate: Date;
    public endDate: Date;
    public startHour: number;
    public endHour: number;
    public motif: string;

    constructor(startDate: Date, endDate: Date, startHour: number, endHour: number, motif: string) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.motif = motif;
    }
}