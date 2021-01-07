import { Component, EventEmitter, Input, OnInit, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { WizardComponent } from 'angular-archwizard';
import { Observable, timer } from 'rxjs';
import { Class, CreateUserRequest, Department, UpdateUserRequest, User } from 'src/app/core/models';
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

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public roles$: Observable<string[]>;
    public classes$: Observable<Class[]>;
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

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "L'utilisateur " + this.userForm.get("firstName").value + " " + this.userForm.get("lastName").value + " ajouté avec succès";
            if (this.userForm.get("role").value === "STUDENT") {
                this.showLoaderSuccess = false;
                this.initStudentForm();
                this.classes$ = this.classService.getClasses();
            } else if (this.userForm.get("role").value === "TEACHER") {
                this.showLoaderSuccess = false;
                this.initTeacherForm();
                this.departments$ = this.refService.getDepartements();
            }
            timer(1000).subscribe(() => this.wizard.goToStep(1));
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "L'utilisateur " + this.userForm.get("firstName").value + " " + this.userForm.get("lastName").value + "  déjà existe";
            }
        }
    }

    public save() {
        if (this.userForm.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.userForm.value as CreateUserRequest };
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

    public onChangeRole(event) {
        this.role = event;
    }

    public onClearRole() {
        this.role = null;
    }

    public reset() {
        this.userForm.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initUserForm(): void {
        this.userForm = this.fb.group({
            firstName: [null, Validators.required],
            lastName: [null, Validators.required],
            email: [null, [Validators.required, Validators.email]],
            role: [null, Validators.required],
        })
    }

    private initStudentForm(): void {
        this.studentForm = this.fb.group({
            cin: [null, Validators.required],
            class: [null, Validators.required]
        })
    }

    private initTeacherForm(): void {
        this.teacherForm = this.fb.group({
            phone: [null, Validators.required],
            grade: [null, Validators.required],
            department: [null, Validators.required]
        })
    }

}