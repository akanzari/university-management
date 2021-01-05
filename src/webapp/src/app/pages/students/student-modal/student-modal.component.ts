import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { CreateUserRequest, SpecificUserResponse, UpdateUserRequest, User } from 'src/app/core/models';
import { IAMService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './student-modal.component.html',
    styleUrls: ['./student-modal.component.scss']
})
export class StudentModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editUser: User;

    public form: FormGroup;

    public users$: Observable<SpecificUserResponse[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.initForm();
        this.users$ = this.iamService.getUserByRole('STUDENT');
        if (this.editUser) {
            this.form.patchValue({
                fullName: this.editUser.firstName,
                email: this.editUser.lastName,
                class: this.editUser.email
            })
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value as CreateUserRequest };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            let arg = new UpdateUserRequest(this.editUser.id, this.form.get("email").value, this.form.get("role").value);
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public onChangeFullName($event): void {
        if ($event) {
            this.form.get("email").setValue($event.email);
        } else {
            this.form.get("email").setValue(null);
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
            fullName: [null, Validators.required],
            email: [null, Validators.required],
            class: [null, [Validators.required]]
        })
    }

}