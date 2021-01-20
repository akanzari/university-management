import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Teacher } from 'src/app/core/models/teacher.model';
import { RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './teacher-modal.component.html',
    styleUrls: ['./teacher-modal.component.scss']
})
export class TeacherModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editTeacher: Teacher;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public disabledUP: boolean = true;
    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public ups: string[];

    public departmenets$: Observable<any[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.departmenets$ = this.refService.getDepartements();
        if (this.editTeacher) {
            this.form.patchValue({
                teacherId: this.editTeacher.teacherId,
                fullName: this.editTeacher.fullName,
                email: this.editTeacher.email,
                phones: this.editTeacher.phones,
                departement: this.editTeacher.departement
            })
        }
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "L'enseignant " + this.form.get("fullName").value + " ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "La enseignant " + this.form.get("fullName").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            /*let arg = new UpdateUserRequest(this.editUser.id, this.form.get("email").value, this.form.get("role").value);
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);*/
        }
    }

    public onChangeFullName($event): void {
        if ($event) {
            this.form.get("email").setValue($event.email);
        } else {
            this.form.get("email").setValue(null);
        }
    }

    public onChangeDepartment(event) {
        if (event) {
            this.ups = event.up;
            this.disabledUP = false;
        } else {
            this.form.get("up").setValue(null);
            this.disabledUP = true;
        }
    }

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    public isElementNotValid(field: string): boolean {
        return this.form.get(field) && !this.form.get(field).hasError("required") && this.form.get(field).invalid;
    }

    private initForm(): void {
        this.form = this.fb.group({
            teacherId: [null, Validators.required],
            fullName: [null, Validators.required],
            phone: [null, [Validators.required, Validators.maxLength(8), Validators.minLength(8), Validators.pattern("^[0-9]*$")]],
            email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
            cin: [null, [Validators.required, Validators.maxLength(8), Validators.minLength(8), Validators.pattern("^[0-9]*$")]],
            departement: [null, Validators.required],
            up: [null, Validators.required]
        })
    }

}