import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { PoleEnum, Room } from 'src/app/core/models';
import { RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './update-room.component.html',
    styleUrls: ['./update-room.component.scss'],
    providers: [DatePipe]
})
export class UpdateRoomModalComponent implements OnInit {

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

    public sites$: string[];
    public blocs$: string[];

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.sites$ = Object.keys(PoleEnum).map(key => PoleEnum[key]);
        if (this.editRoom) {
            this.refService.getBlocs().subscribe(item => {
                let result = item.find(el => el.pole === Object.keys(PoleEnum)[Object.values(PoleEnum).indexOf(this.editRoom.pole as any)]);
                this.blocs$ = result.blocs;
            })
            this.disabledBloc = false;
            console.log(this.editRoom);
            
            this.form.patchValue({
                classRoomId: this.editRoom.classRoomId,
                capacity: this.editRoom.capacity,
                pole: this.editRoom.pole,
                blocId: this.editRoom.bloc
            })
        }
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "La Salle " + this.form.get("classRoomId").value + " modifié avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "La Salle " + this.form.get("classRoomId").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public update() {
        if (this.form.valid) {
            const form = this.form.value;
            console.log(form);
            
            let arg: CreateRoomRequest = new CreateRoomRequest(form.classRoomId, form.capacity, form.pole, form.blocId);
            console.log(arg);
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

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initForm(): void {
        this.form = this.fb.group({
            classRoomId: [null, Validators.required],
            capacity: [null, Validators.required],
            pole: [null, Validators.required],
            blocId: [null, Validators.required]
        })
    }

}

export class CreateRoomRequest {
    public classRoomId: string;
    public capacity: string;
    public pole: string;
    public bloc: string;

    constructor(classRoomId: string, capacity: string, pole: any, bloc: string) {
        this.classRoomId = classRoomId;
        this.capacity = capacity;
        this.pole = Object.keys(PoleEnum)[Object.values(PoleEnum).indexOf(pole)];
        this.bloc = bloc;
    }
}