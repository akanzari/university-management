import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { CreateUserRequest, UpdateUserRequest, User } from 'src/app/core/models';
import { IAMService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './user-modal.component.html',
    styleUrls: ['./user-modal.component.scss']
})
export class UserModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editUser: User;

    public form: FormGroup;

    public roles$: Observable<string[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.initForm();
        this.roles$ = this.iamService.getRoles();
        if (this.editUser) {
            this.form.patchValue({
                firstname: this.editUser.firstName,
                lastname: this.editUser.lastName,
                email: this.editUser.email,
                role: this.editUser.roles[0]
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

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initForm(): void {
        this.form = this.fb.group({
            firstname: [null, Validators.required],
            lastname: [null, Validators.required],
            email: [null, [Validators.required, Validators.email]],
            role: [null, Validators.required],
        })
    }

}