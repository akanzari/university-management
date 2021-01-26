import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
    public inProgress: boolean = false;

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
            let form = this.form.value;
            let result: CreateDisponibilityRequest[] = [];
            if (form.disponibilities && form.disponibilities.length > 0) {
                form.disponibilities.forEach(element => {
                    result.push(new CreateDisponibilityRequest(element.startDate, element.endDate, +element.startHour.replace(":", ""), +element.endHour.replace(":", ""), element.motif));
                });
            }
            let arg = new CreateTeacherRequest(form.teacherId, form.fullName, form.email, form.phone, form.up, form.departement, result);
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: arg };
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
            teacherId: [null, Validators.required],
            fullName: [null, Validators.required],
            phone: [null, [Validators.required, Validators.maxLength(8), Validators.minLength(8), Validators.pattern("^[0-9]*$")]],
            email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
            departement: [null, Validators.required],
            up: [null, Validators.required],
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

export class CreateTeacherRequest {
    public teacherId: string;
    public fullName: string;
    public email: string;
    public phone: string;
    public up: string;
    public departement: string;
    public disponibilities: CreateDisponibilityRequest[];

    constructor(teacherId: string, fullName: string, email: any, phone: string, up: string, departement: string, disponibilities: CreateDisponibilityRequest[]) {
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.up = up;
        this.departement = departement;
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