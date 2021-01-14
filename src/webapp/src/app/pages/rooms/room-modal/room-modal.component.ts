import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { empty, Observable, timer } from 'rxjs';
import { Bloc, CreateRoomRequest, Room, UpdateRoomRequest } from 'src/app/core/models';
import { Site } from 'src/app/core/models/site.modal';
import { BlocService, RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './room-modal.component.html',
    styleUrls: ['./room-modal.component.scss'],
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

    public sites$: Observable<Site[]>;
    public blocs$: Observable<Bloc[]>;
    public reasonRooms$: Observable<any[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private blocService: BlocService,
        private datePipe: DatePipe,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.sites$ = this.refService.getSites();
        this.reasonRooms$ = this.refService.getReasonRooms();
        if (this.editRoom) {
            this.disabledBloc = false;
            this.blocs$ = this.blocService.getBlocs(this.editRoom.site.siteId);
            this.form.patchValue({
                label: this.editRoom.label,
                capacity: this.editRoom.capacity,
                siteId: this.editRoom.site.siteId,
                blocId: this.editRoom.bloc.blocId,
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
            const form = this.form.value;
            let arg: CreateRoomRequest = new CreateRoomRequest(form.label, form.capacity, form.blocId, form.siteId, form.startDate, form.endDate, form.startHour, form.endHour, form.reasonId);
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
            this.blocs$ = this.blocService.getBlocs(event.siteId);
        } else {
            this.disabledBloc = true;
            this.form.get("blocId").setValue(null);
            this.blocs$ = empty();
        }
    }

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initForm(): void {
        this.form = this.fb.group({
            label: [null, Validators.required],
            capacity: [null, Validators.required],
            siteId: [null, Validators.required],
            blocId: [null, Validators.required],
            startDate: [null],
            endDate: [null],
            startHour: [null],
            endHour: [null],
            reasonId: [null]
        }, {
            validator: [this.dateLessThan('startDate', 'endDate', { 'loadDate': true }),
            this.hourLessThan('startHour', 'endHour', { 'loadHour': true })]
        },
        )
    }

    private dateLessThan(firstDate: string, secondDate: string, validatorField: { [key: string]: boolean }): ValidatorFn {
        return (c: AbstractControl): { [key: string]: boolean } | null => {
            const date1 = c.get(firstDate).value;
            const date2 = c.get(secondDate).value;
            if ((!date1 && date2) || (date1 && date2 && date1 > date2)) {
                return validatorField;
            }
            return null;
        };
    }

    private hourLessThan(firstHour: string, secondHour: string, validatorField: { [key: string]: boolean }): ValidatorFn {
        return (c: AbstractControl): { [key: string]: boolean } | null => {
            const hour1 = c.get(firstHour).value;
            const hour2 = c.get(secondHour).value;
            if ((!hour1 && hour2) || (hour1 && hour2 && hour1 > hour2)) {
                return validatorField;
            }
            return null;
        };
    }

}