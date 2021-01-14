import { Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { WizardComponent } from 'angular-archwizard';
import { Observable, timer } from 'rxjs';
import { Classs, CreateStudentRequest, CreateTeacherRequest, CreateUserRequest, Department, UpdateUserRequest, User } from 'src/app/core/models';
import { ClassService, IAMService, RefService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './user-modal.component.html',
    styleUrls: ['./user-modal.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class UserModalComponent implements OnInit {

    @ViewChild(WizardComponent)
    public wizard: WizardComponent;

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editUser: User;

    public userForm: FormGroup;

    public studentForm: FormGroup;

    public teacherForm: FormGroup;

    public role: string;
    public saveError: string;
    public saveSuccess: string;
    public userId: string;

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public roles$: Observable<string[]>;
    public classes$: Observable<Classs[]>;
    public departments$: Observable<Department[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private iamService: IAMService,
        private classService: ClassService,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initUserForm();
        this.roles$ = this.iamService.getRoles();
        if (this.editUser) {
            this.userForm.patchValue({
                firstName: this.editUser.firstName,
                lastName: this.editUser.lastName,
                email: this.editUser.email,
                role: this.editUser.roles[0]
            })
        }
    }

    public setIsUserSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "L'utilisateur " + this.userForm.get("firstName").value + " " + this.userForm.get("lastName").value + " ajouté avec succès";
            if (this.userForm.get("role").value === "STUDENT") {
                this.showLoaderSuccess = false;
                this.classes$ = this.classService.getClasses();
                timer(1000).subscribe(() => this.wizard.goToStep(1));
            } else if (this.userForm.get("role").value === "TEACHER") {
                this.showLoaderSuccess = false;
                this.departments$ = this.refService.getDepartements();
                timer(1000).subscribe(() => this.wizard.goToStep(1));
            } else {
                this.reset();
            }
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "L'utilisateur déjà existe avec l'email " + this.userForm.get("email").value;
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public setUserId(userId: string) {
        this.userId = userId;
    }

    public setIsStudentSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "Le profil d'étudiant " + this.userForm.get("firstName").value + " " + this.userForm.get("lastName").value + " est mettre à jour";
            timer(1000).subscribe(() => this.resetStudent());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "Le cin " + this.userForm.get("studentForm").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public setIsTeacherSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "Le profil d'enseignant " + this.userForm.get("firstName").value + " " + this.userForm.get("lastName").value + " est mettre à jour";
            timer(1000).subscribe(() => this.resetTeacher());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "Le téléphone " + this.teacherForm.get("phone").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.userForm.valid) {
            let dataValue: DataValue = { type: "user", action: ActionEnum.CREATE, value: this.userForm.value as CreateUserRequest };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.userForm.valid) {
            let arg = new UpdateUserRequest(this.editUser.id, this.userForm.get("email").value, this.userForm.get("role").value);
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public saveStudent() {
        if (this.studentForm.valid) {
            let arg = this.studentForm.value as CreateStudentRequest;
            arg.userId = this.userId;
            arg.classId = this.studentForm.value.class.classId;
            let dataValue: DataValue = { type: "student", action: ActionEnum.CREATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public saveTeacher() {
        if (this.teacherForm.valid) {
            let arg = this.teacherForm.value as CreateTeacherRequest;
            arg.userId = this.userId;
            arg.departmentId = this.teacherForm.value.department.departmentId;
            let dataValue: DataValue = { type: "teacher", action: ActionEnum.CREATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public keyPress(event) {
        const pattern = /[0-9]/;
        let inputChar = String.fromCharCode(event.charCode);
        if (!pattern.test(inputChar)) {
            event.preventDefault();

        }
    }

    public onChangeRole(event) {
        this.role = event;
        if (this.role === "STUDENT") {
            this.initStudentForm();
        } else if (this.role === "TEACHER") {
            this.initTeacherForm();
        }
    }

    public onClearRole() {
        this.role = null;
    }

    public reset() {
        this.userForm.reset();
        this.closeModal();
    }

    public resetStudent() {
        this.studentForm.reset();
        this.closeModal();
    }

    public resetTeacher() {
        this.teacherForm.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initUserForm(): void {
        this.userForm = this.fb.group({
            firstName: [null, Validators.required],
            lastName: [null, Validators.required],
            email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
            role: [null, Validators.required],
        })
    }

    private initStudentForm(): void {
        this.studentForm = this.fb.group({
            cin: [null, [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(8), Validators.maxLength(8)]],
            class: [null, Validators.required]
        })
    }

    private initTeacherForm(): void {
        this.teacherForm = this.fb.group({
            phone: [null, [Validators.required, Validators.pattern("^[0-9]*$"), Validators.minLength(9), Validators.maxLength(9)]],
            grade: [null, Validators.required],
            department: [null, Validators.required]
        })
    }

}