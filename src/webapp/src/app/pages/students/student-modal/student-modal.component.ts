import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { timer } from 'rxjs';
import { CreateUserRequest, User } from 'src/app/core/models';
import { ClassService } from 'src/app/core/services';
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

    public saveError: string;
    public saveSuccess: string;

    public showLoaderSuccess: boolean = false;
    public showLoaderError: boolean = false;

    public classes: any[];

    constructor(private fb: FormBuilder,
        private classService: ClassService,
        private activeModal: NgbActiveModal) {
    }

    ngOnInit() {
        this.initForm();
        this.classService.getClasses().subscribe(classes => this.classes = classes);
    }

    public setIsSaved(event) {
        console.log(event);
        
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "L'étudiant " + this.form.get("fullName").value + " ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "L'étudiant " + this.form.get("fullName").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value as CreateUserRequest };
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
            fullName: [null, Validators.required],
            email: [null, [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
            cin: [null, Validators.required],
            classId: [null, [Validators.required]]
        })
    }

}