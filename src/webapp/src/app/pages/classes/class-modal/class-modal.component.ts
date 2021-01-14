import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Classs, CreateClassRequest, UpdateClassRequest } from 'src/app/core/models';
import { Speciality } from 'src/app/core/models/speciality.model';
import { RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './class-modal.component.html',
    styleUrls: ['./class-modal.component.scss']
})
export class ClassModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editClass: Classs;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public specialities$: Observable<Speciality[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.specialities$ = this.refService.getSpecialities();
        if (this.editClass) {
            this.form.patchValue({
                code: this.editClass.code,
                label: this.editClass.label,
                nbrStudents: this.editClass.nbrStudents,
                nbrGroups: this.editClass.nbrGroups,
                specialityId: this.editClass.speciality.specialityId
            })
        }
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "La classe " + this.form.get("label").value + " ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "La classe " + this.form.get("label").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value as CreateClassRequest };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            console.log(this.form.value);
            let arg = this.form.value as UpdateClassRequest;
            arg.classId = this.editClass.classId;
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
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
            nbrStudents: [null, Validators.required],
            nbrGroups: [null, Validators.required],
            specialityId: [null, Validators.required]
        })
    }

}