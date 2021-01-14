import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Exam, CreateExamRequest, UpdateExamRequest, Classs, Module, SemesterEnum, ExamTypeEnum, DsexEnum, SessionEnum, Room, SpecificUserResponse } from 'src/app/core/models';
import { ClassService, ModuleService, IAMService, RoomService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';

@Component({
    templateUrl: './exam-modal.component.html',
    styleUrls: ['./exam-modal.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ExamModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editExam: Exam;

    public form: FormGroup;

    public saveError: string;
    public saveSuccess: string;

    public dsex: string[];
    public examTypes: string[];
    public semesters: string[];
    public sessions: string[];

    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;

    public class$: Observable<Classs[]>;
    public module$: Observable<Module[]>;
    public rooms$: Observable<Room[]>;
    public supervisors$: Observable<SpecificUserResponse[]>;


    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private classService: ClassService,
        private moduleService: ModuleService,
        private roomService: RoomService,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.initForm();
        this.class$ = this.classService.getClasses();
        this.module$ = this.moduleService.getModules();
        this.dsex = Object.keys(DsexEnum).map(key => DsexEnum[key]);
        this.examTypes = Object.keys(ExamTypeEnum).map(key => ExamTypeEnum[key]);
        this.semesters = Object.keys(SemesterEnum).map(key => SemesterEnum[key]);
        this.sessions = Object.keys(SessionEnum).map(key => SessionEnum[key]);
        this.rooms$ = this.roomService.getRooms();
        this.supervisors$ = this.iamService.getUserByRole("TEACHER");
        if (this.editExam) {
            this.form.patchValue({
                classId: this.editExam.classs.classId,
                moduleId: this.editExam.module.moduleId,
                date: this.editExam.examDate,
                hour: this.editExam.examHour,
                duration: this.editExam.examDuration,
                dsex: this.editExam.dsex,
                examType: this.editExam.examType,
                semester: this.editExam.semester,
                session: this.editExam.examSession,
                classRoomId: this.editExam.classRoom.classRoomId,
                supervisorId: this.editExam.supervisor.userId
            })
        }
    }

    public setIsSaved(event) {
        if (event.isSaved === true) {
            this.showLoaderSuccess = true;
            this.saveSuccess = "L'examen est ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 701) {
                this.showLoaderError = true;
                this.saveError = "L'examen est déjà existe";
                timer(2000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value as CreateExamRequest };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            console.log(this.form.value);
            let arg = this.form.value as UpdateExamRequest;
            arg.classId = this.editExam.examId;
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public onChangeClass(event) {
        if (event) {
            this.module$ = this.moduleService.getModulesByClass(event.classId);
        } else {
            this.module$ = this.moduleService.getModules();
        }
    }

    public onChangeModule(event) {
        if (event) {
            this.class$ = this.classService.getClassesByModule(event.moduleId);
        } else {
            this.class$ = this.classService.getClasses();
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
            classId: [null, Validators.required],
            moduleId: [null, Validators.required],
            date: [null, Validators.required],
            hour: [null, Validators.required],
            duration: [null, Validators.required],
            dsex: [null, Validators.required],
            examType: [null, Validators.required],
            semester: [null, Validators.required],
            session: [null, Validators.required],
            classRoomId: [null, Validators.required],
            supervisorId: [null, Validators.required]
        })
    }

}