import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, timer } from 'rxjs';
import { Exam, CreateExamRequest, UpdateExamRequest, Classs, Module, SemesterEnum, ExamTypeEnum, DsexEnum, SessionEnum, Room, SpecificUserResponse } from 'src/app/core/models';
import { Teacher } from 'src/app/core/models/teacher.model';
import { ClassService, ModuleService, IAMService, RoomService, RefService, TeacherService } from 'src/app/core/services';
import { ActionEnum } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { TSMap } from 'typescript-map';

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
    public sessions: any[];

    public levels: any[];

    public disabled: TSMap<number, boolean> = new TSMap<number, boolean>();
    public disabledRoom: TSMap<number, boolean> = new TSMap<number, boolean>();
    public disabledTeacher: TSMap<number, boolean> = new TSMap<number, boolean>();

    public disabledClass: boolean = true;
    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;
    public inProgress: boolean = false;

    public rooms: Room[];
    public teachers: Teacher[];

    public department$: Observable<any[]>;
    public blocs$: Observable<any[]>;

    public classes: Classs[] = [];
    public classesBuffer: Classs[] = [];
    public loadingClasses = false;

    public modules: Module[] = [];
    public modulesBuffer: Module[] = [];
    public loadingModules = false;

    public selectedClassesLenght: number;
    public bufferSize = 10;
    public numberOfItemsFromEndBeforeFetchingMore = 10;

    constructor(private fb: FormBuilder,
        private activeModal: NgbActiveModal,
        private classService: ClassService,
        private refService: RefService,
        private moduleService: ModuleService,
        private roomService: RoomService,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.initForm();
        this.disabled.set(0, true);
        this.disabledRoom.set(0, true);
        this.disabledTeacher.set(0, true);
        this.department$ = this.refService.getDepartements();
        this.blocs$ = this.refService.getBlocs();
        this.refService.getLevels().subscribe(levels => {
            this.levels = levels;
            let sessions: any[] = [];
            this.levels.forEach(level => sessions.push(level.session));
            this.sessions = [...new Set([].concat.apply([], sessions))];
        })
        this.moduleService.getModules().subscribe(modules => {
            this.modules = modules;
            this.modulesBuffer = this.modules.slice(0, this.bufferSize);
        })
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
                supervisorId: this.editExam.supervisor.teacherId
            })
        }
    }

    public onScrollToEndClasses() {
        this.fetchMoreClasses();
    }

    public onScrollClasses({ end }) {
        if (this.loadingClasses || this.classes.length <= this.classesBuffer.length) {
            return;
        }

        if (end + this.numberOfItemsFromEndBeforeFetchingMore >= this.classesBuffer.length) {
            this.fetchMoreClasses();
        }
    }

    private fetchMoreClasses() {
        const len = this.classesBuffer.length;
        const more = this.classes.slice(len, this.bufferSize + len);
        this.loadingClasses = true;
        setTimeout(() => {
            this.loadingClasses = false;
            this.classesBuffer = this.classesBuffer.concat(more);
        }, 200)
    }

    public onScrollToEndModules() {
        this.fetchMoreModules();
    }

    public onScrollModules({ end }) {
        if (this.loadingModules || this.modules.length <= this.modulesBuffer.length) {
            return;
        }

        if (end + this.numberOfItemsFromEndBeforeFetchingMore >= this.modulesBuffer.length) {
            this.fetchMoreModules();
        }
    }

    private fetchMoreModules() {
        const len = this.modulesBuffer.length;
        const more = this.modules.slice(len, this.bufferSize + len);
        this.loadingModules = true;
        setTimeout(() => {
            this.loadingModules = false;
            this.modulesBuffer = this.modulesBuffer.concat(more);
        }, 200)
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
            let arg = this.form.value;
            arg.assignClasses.forEach(element => {
                element.examHour = +element.examHour.replace(":", "");
            })
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: this.form.value };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            let arg = this.form.value as UpdateExamRequest;
            arg.classId = this.editExam.examId;
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public onChangeClass(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        let classes = assignClassControl.get("classes").value
        this.selectedClassesLenght = classes.length;
    }

    public onRemoveClass(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        assignClassControl.get("rooms").setValue(null);
        assignClassControl.get("teachers").setValue(null);
    }

    public onClearClass(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        assignClassControl.get("rooms").setValue(null);
        assignClassControl.get("teachers").setValue(null);
    }

    public onChangeDate(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        if (assignClassControl.get("examDate").value && assignClassControl.get("examHour").value && assignClassControl.get("examDuration").value) {
            this.disabled.set(index, false);
        } else {

            this.disabled.set(index, true);
        }
    }

    public onChangeHour(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        if (assignClassControl.get("examDate").value && assignClassControl.get("examHour").value && assignClassControl.get("examDuration").value) {
            this.disabled.set(index, false);
        } else {

            this.disabled.set(index, true);
        }
    }

    public onChangeDuration(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        if (assignClassControl.get("examDate").value && assignClassControl.get("examHour").value && assignClassControl.get("examDuration").value) {
            this.disabled.set(index, false);
        } else {

            this.disabled.set(index, true);
        }
    }

    public onChangeBoc(event, index) {
        this.onClearBloc(event, index);
    }

    public onRemoveBloc(event, index) {
        this.onClearBloc(event, index);
    }

    public onClearBloc(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        let examHour = +assignClassControl.get("examHour").value.replace(":", "");
        let arg = { blocs: assignClassControl.get("bloc").value, effectDate: assignClassControl.get("examDate").value, hour: examHour };
        this.roomService.getRoomsByBlocs(arg).subscribe(rooms => {
            this.rooms = rooms;
            assignClassControl.get("rooms").setValue(null);
            if (this.rooms.length > 0) {
                this.disabledRoom.set(index, false);
            } else {
                this.disabledRoom.set(index, true);
            }
        });
    }

    public onChangeDepartmenet(event, index) {
        this.onClearDepartmenet(event, index);
    }

    public onRemoveDepartmenet(event, index) {
        this.onClearDepartmenet(event, index);
    }

    public onClearDepartmenet(event, index) {
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        this.teacherService.getTeachersByUps(assignClassControl.get("up").value).subscribe(teachers => {
            this.teachers = teachers;
            assignClassControl.get("teachers").setValue(null);
            if (this.teachers.length > 0) {
                this.disabledTeacher.set(index, false);
            } else {
                this.disabledTeacher.set(index, true);
            }
        });
    }

    public onChangeLavel(event) {
        if (event) {
            this.classService.getClasses().subscribe(classes => {
                let indexLevel = event.map(item => item.label.charAt(0));
                this.classes = classes.filter(el => indexLevel.some(item => item === el.classId.charAt(0)));
                this.classesBuffer = this.classes.slice(0, this.bufferSize);
                this.disabledClass = false;
            })
            if (event.every(item => item.id < 5)) {
                let level = this.levels.find(level => level.id === 1);
                if (level) {
                    this.sessions = level.session;
                    let disabledLevel = this.levels.find(level => level.id === 5);
                    if (disabledLevel && disabledLevel.disabled === false) {
                        disabledLevel.disabled = true;
                        this.levels = [...this.levels];
                    }
                }
            } else {
                let level = this.levels.find(level => level.id === 5);
                if (level) {
                    this.sessions = level.session;
                    let disabledLevels = this.levels.filter(level => level.id < 5);
                    if (disabledLevels.length > 0) {
                        disabledLevels.forEach(item => item.disabled = true);
                        this.levels = [...this.levels];
                    }
                }
            }
        } else {
            this.onClearLavel();
        }
    }

    public onRemoveLavel() {
        this.disabledClass = true;
        if (this.form.get("levels").value && this.form.get("levels").value.length > 0) {
            if (this.form.get("levels").value.every(item => item.id < 5)) {
                let level = this.levels.find(level => level.id === 1);
                this.sessions = level.session;
                let disabledLevel = this.levels.find(level => level.id === 5);
                if (disabledLevel.disabled === false) {
                    disabledLevel.disabled = true;
                    this.levels = [...this.levels];
                }
            }
        } else {
            this.onClearLavel();
        }
    }

    public onClearLavel() {
        this.refService.getLevels().subscribe(levels => {
            this.form.get("session").setValue(null);
            this.levels = levels;
            let sessions: any[] = [];
            this.levels.forEach(level => sessions.push(level.session));
            this.sessions = [...new Set([].concat.apply([], sessions))];
        });
    }

    public onChangeSession(event) {
        if (event) {
            this.refService.getLevels().subscribe(levels => {
                let result = levels.filter(level => level.session.some(item => item === event));
                this.levels = result;
            })
        } else {
            this.refService.getLevels().subscribe(levels => {
                this.form.get("levels").setValue(null);
                this.levels = levels;
                let sessions: any[] = [];
                this.levels.forEach(level => sessions.push(level.session));
                this.sessions = [...new Set([].concat.apply([], sessions))];
            })
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
        this.disabled.set(index + 1, true);
        this.disabledRoom.set(index + 1, true);
        this.disabledTeacher.set(index + 1, true);
        const assignClassControl: FormArray = <FormArray>this.form.controls['assignClasses'];
        assignClassControl.push(this.initAssignClasses());
    }

    public removeAssignClass(index: number): void {
        this.disabled.delete(index + 1);
        this.disabledRoom.delete(index + 1);
        this.disabledTeacher.delete(index + 1);
        const assignClassControl: FormArray = <FormArray>this.form.controls['assignClasses'];
        if (assignClassControl.length > 1) {
            assignClassControl.removeAt(index);
        }
    }

    public isDisabledAssignClass(index: number): Boolean {
        let disabled: Boolean = true;
        const assignClassControl = (<FormArray>this.form.controls['assignClasses']).at(index);
        if (assignClassControl.valid) {
            disabled = false;
        }
        return disabled;
    }

    private initForm(): void {
        this.form = this.fb.group({
            levels: [null, Validators.required],
            session: [null, Validators.required],
            assignClasses: this.fb.array([
                this.initAssignClasses()
            ])
        })
    }

    private initAssignClasses(): FormGroup {
        return this.fb.group({
            module: [null, Validators.required],
            classes: [null, Validators.required],
            teachers: [null, Validators.required],
            examDate: [null, Validators.required],
            examHour: [null, Validators.required],
            rooms: [null, Validators.required],
            examDuration: [null, Validators.required],
            bloc: [null, Validators.required],
            up: [null, Validators.required]
        })
    }

}