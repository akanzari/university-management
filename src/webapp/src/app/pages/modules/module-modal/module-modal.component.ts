import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Classs, Module, UpdateClassRequest, SpecificUserResponse, PeriodEnum, SemesterEnum, ExamTypeEnum } from 'src/app/core/models';
import { CreateModuleRequest } from 'src/app/core/models/request/create-module-request.model';
import { ClassService, IAMService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './module-modal.component.html',
    styleUrls: ['./module-modal.component.scss']
})
export class ModuleModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editModule: Module;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public periods: string[];
    public semesters: string[];
    public examTypes: string[];

    public class$: Observable<Classs[]>;
    public teacher$: Observable<SpecificUserResponse[]>;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private classService: ClassService,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.initForm();
        this.periods = Object.keys(PeriodEnum).map(key => PeriodEnum[key]);
        this.semesters = Object.keys(SemesterEnum).map(key => SemesterEnum[key]);
        this.examTypes = Object.keys(ExamTypeEnum).map(key => ExamTypeEnum[key]);
        this.class$ = this.classService.getClasses();
        this.teacher$ = this.iamService.getUserByRole("TEACHER");
        if (this.editModule) {
            this.form.patchValue({
                designation: this.editModule.designation,
                nbrHours: this.editModule.nbrHours,
                examType: this.editModule.examType,
                semester: this.editModule.semester,
                periods: this.editModule.periods,
                classId: this.editModule.classs.classId,
                teacherId: this.editModule.teacher.userId
            })
        }
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "La module " + this.form.get("designation").value + " ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "La module " + this.form.get("designation").value + "  déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value as CreateModuleRequest };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            let arg = this.form.value as UpdateClassRequest;
            arg.classId = this.editModule.moduleId;
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
    onChangeFullName(ee) {
        console.log(this.form);
        
    }
    private initForm(): void {
        this.form = this.fb.group({
            designation: [null, Validators.required],
            nbrHours: [null, [Validators.required, Validators.pattern("^[0-9]*$")]],
            examType: [null, Validators.required],
            semester: [null, Validators.required],
            periods: [null, Validators.required],
            classId: [null, Validators.required],
            teacherId: [null, Validators.required]
        })
    }

}