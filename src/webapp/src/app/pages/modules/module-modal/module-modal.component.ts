import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Classs, Module, UpdateClassRequest, ExamTypeEnum } from 'src/app/core/models';
import { Teacher } from 'src/app/core/models/teacher.model';
import { ClassService, RefService, TeacherService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { TSMap } from 'typescript-map';

@Component({
    templateUrl: './module-modal.component.html',
    styleUrls: ['./module-modal.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ModuleModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editModule: Module;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public bufferSize = 20;
    public numberOfItemsFromEndBeforeFetchingMore = 10;
    
    public loading = false;
    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;
    public inProgress: boolean = false;

    public disabledPeriod: TSMap<number, boolean> = new TSMap<number, boolean>();

    public periods: any[];
    public examTypes: string[];
    public teachersBuffer = [];

    public semesters$: Observable<any[]>;
    public typeExam$: Observable<any[]>;
    public class$: Observable<Classs[]>;
    public teachers: Teacher[];

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private classService: ClassService,
        private teacherService: TeacherService,
        private refService: RefService) {
    }

    ngOnInit() {
        this.initForm();
        this.disabledPeriod.set(0, true);
        this.semesters$ = this.refService.getSemeters();
        this.typeExam$ = this.refService.getTypeExam();
        this.examTypes = Object.keys(ExamTypeEnum).map(key => ExamTypeEnum[key]);
        this.class$ = this.classService.getClasses();
        this.teacherService.getTeachers().subscribe(teachers => {
            this.teachers = teachers;
            this.teachersBuffer = this.teachers.slice(0, this.bufferSize);
        });
        if (this.editModule) {
            this.form.patchValue({
                designation: this.editModule.designation,
                nbrHours: this.editModule.nbrHours,
                examType: this.editModule.examType,
                semester: this.editModule.semester,
                periods: this.editModule.periods,
                classId: this.editModule.classs.classId,
                teacherId: this.editModule.teacher.teacherId
            })
        }
    }
    
    private fetchMore() {
        const len = this.teachersBuffer.length;
        const more = this.teachers.slice(len, this.bufferSize + len);
        this.loading = true;
        setTimeout(() => {
            this.loading = false;
            this.teachersBuffer = this.teachersBuffer.concat(more);
        }, 200)
    }

    onScrollToEnd() {
        this.fetchMore();
    }

    onScroll({ end }) {
        if (this.loading || this.teachers.length <= this.teachersBuffer.length) {
            return;
        }

        if (end + this.numberOfItemsFromEndBeforeFetchingMore >= this.teachersBuffer.length) {
            this.fetchMore();
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
            console.log(this.form.value);
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value };
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

    public onChangeSemester(event, index) {
        if (event) {
            this.disabledPeriod.set(index, false);
            this.periods = event.periods;
        } else {
            this.disabledPeriod.set(index, true);
            const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
            assignClassControl.get("periodId").setValue(null);
        }
    }

    public reset() {
        this.form.reset();
        this.closeModal();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    public addAssignClass(index: number): void {
        this.disabledPeriod.set(index + 1, true);
        const assignClassControl: FormArray = <FormArray>this.form.controls['assignClasses'];
        assignClassControl.push(this.initAssignClasses());
    }

    public removeAssignClass(index: number): void {
        this.disabledPeriod.delete(index + 1);
        const assignClassControl: FormArray = <FormArray>this.form.controls['assignClasses'];
        if (assignClassControl.length > 1) {
            assignClassControl.removeAt(index);
        }
    }

    private initForm(): void {
        this.form = this.fb.group({
            moduleId: [null, Validators.required],
            designation: [null],
            assignClasses: this.fb.array([
                this.initAssignClasses()
            ])
        })
    }

    private initAssignClasses(): FormGroup {
        return this.fb.group({
            coefficient: [null],
            nbrHour: [null],
            semesterId: [null],
            periodId: [null],
            classId: [null],
            typeExam: [null],
            teacherIds: [null]
        })
    }

}