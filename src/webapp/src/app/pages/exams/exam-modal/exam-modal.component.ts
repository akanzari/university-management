import { DatePipe } from '@angular/common';
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
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class ExamModalComponent implements OnInit {

    @Output()
    public triggerSave: EventEmitter<DataValue> = new EventEmitter();

    @Input()
    public editExam: any;

    public form: FormGroup;

    public endHourExam: string;
    public saveError: string;
    public saveSuccess: string;

    public dsex: string[];
    public examTypes: string[];
    public semesters: string[];
    public sessions: any[];

    public levels: any[];

    public disabled: boolean = true;
    public disabledRoom: boolean = true;
    public disabledTeacher: boolean = true;

    public disabledClass: boolean = true;
    public showLoaderError: boolean = false;
    public showLoaderSuccess: boolean = false;
    public inProgress: boolean = false;

    public groupValueFn;

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
        private datePipe: DatePipe,
        private moduleService: ModuleService,
        private roomService: RoomService,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.initForm();
        this.department$ = this.refService.getDepartements();
        this.blocs$ = this.refService.getBlocs();
        this.refService.getLevels().subscribe(levels => {
            this.levels = levels;
            let sessions: any[] = [];
            this.levels.forEach(level => sessions.push(level.session));
            this.sessions = [...new Set([].concat.apply([], sessions))];
            if (this.editExam) {
                this.levels.filter(item => item.id == this.editExam.class.substring(0, 1)).map(item => item.id);
                this.form.get("levels").setValue(this.levels.filter(item => item.id == this.editExam.class.substring(0, 1)).map(item => item.id));
                this.onChangeLavel(this.levels.filter(item => item.id == this.editExam.class.substring(0, 1)));
            }
        })
        this.moduleService.getModules().subscribe(modules => {
            this.modules = modules;
            this.modulesBuffer = this.modules.slice(0, this.bufferSize);
        })
        if (this.editExam) {
            this.teacherService.getUpByTeachers(this.editExam.teachersNames).subscribe(items => {
                this.form.get("up").setValue(items);
                this.disabledTeacher = false;
                let arg = { ups: items, effectDate: this.editExam.examDate, hour: this.editExam.examHour.replace("H:", "") };
                this.teacherService.getTeachersByUps(arg).subscribe(teachers => {
                    this.teachers = this.editExam.teachersC.concat(teachers);
                })
            });
            this.roomService.getBlocByRooms(this.editExam.roomsNames).subscribe(items => {
                this.form.get("bloc").setValue(items);
                this.disabledRoom = false;
                let arg = { blocs: items, effectDate: this.editExam.examDate, hour: this.editExam.examHour.replace("H:", "") };
                this.roomService.getRoomsByBlocs(arg).subscribe(rooms => {
                    this.rooms = this.editExam.roomsC.concat(rooms);
                })
            });
            this.onChangeSession(this.editExam.sessionT);
            let date = this.datePipe.transform(this.editExam.examDate.toLocaleString().substring(0, 10), "yyyy-MM-dd");
            this.form.patchValue({
                session: this.editExam.sessionT,
                module: this.editExam.moduleI,
                classes: [this.editExam.classC.classId],
                teachers: this.editExam.teachersC.map(item => item.teacherId),
                examDate: date,
                examHour: this.editExam.examHour.replace("H", ""),
                examDuration: this.editExam.examDuration.replace(" min", ""),
                rooms: this.editExam.roomsC.map(item => item.classRoomId)
            })
            if (this.form.get("examDate").value && this.form.get("examHour").value && this.form.get("examDuration").value) {
                let form = Object.assign(this.form.value);
                const examHour = +form.examHour.replace(':', '');
                const hour = String((form.examDuration / 60).toString().split('.')[0]);
                const min = String(form.examDuration % 60);
                const result = examHour + (+hour.concat(min === "0" ? "00" : min));
                this.endHourExam = result.toString().substring(0, 2) + "H:" + result.toString().substring(2, 4);
                this.disabled = false;
            }
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
            this.saveSuccess = "L'épreuve est ajouté avec succès";
            timer(1000).subscribe(() => this.reset());
        } else {
            if (event.code === 703) {
                this.showLoaderError = true;
                this.saveError = "Le nombre des salles est insuffisant";
                timer(3000).subscribe(() => this.showLoaderError = false);
            } else if (event.code === 704) {
                this.showLoaderError = true;
                this.saveError = "Le nombre des enseignant est insuffisant";
                timer(3000).subscribe(() => this.showLoaderError = false);
            } else if (event.code === 705) {
                this.showLoaderError = true;
                this.saveError = "La module seléctionnée déja existe dans cette session";
                timer(3000).subscribe(() => this.showLoaderError = false);
            }
        }
    }

    public save() {
        if (this.form.valid) {
            let arg = Object.assign(this.form.value);
            arg.examHour = +arg.examHour.toString().replace(":", "");
            let dataValue: DataValue = { action: ActionEnum.CREATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public update() {
        if (this.form.valid) {
            let arg = this.form.value;
            arg.examId = this.editExam.examId;
            arg.examHour = +arg.examHour.toString().replace(":", "");
            let dataValue: DataValue = { action: ActionEnum.UPDATE, value: arg };
            this.triggerSave.emit(dataValue);
        }
    }

    public onChangeModule(event) {
        if (this.form.get("module").value) {
            this.classService.getClassesByModule(this.form.get("module").value).subscribe(items => {
                this.classes = items;
            })
        }
    }

    public onChangeClass(event) {
        let classes = this.form.get("classes").value
        this.selectedClassesLenght = classes.length;
    }

    public onRemoveClass(event) {
        this.form.get("rooms").setValue(null);
        this.form.get("teachers").setValue(null);
    }

    public onClearClass(event) {
        this.form.get("rooms").setValue(null);
        this.form.get("teachers").setValue(null);
    }

    public onChangeDate(event) {
        if (this.form.get("examDate").value && this.form.get("examHour").value && this.form.get("examDuration").value) {
            this.disabled = false;
        } else {
            this.disabled = true;
        }
    }

    public onChangeHour(event) {
        if (this.form.get("examDate").value && this.form.get("examHour").value && this.form.get("examDuration").value) {
            this.disabled = false;
        } else {
            this.disabled = true;
        }
    }

    public onChangeDuration(event) {
        if (this.form.get("examDate").value && this.form.get("examHour").value && this.form.get("examDuration").value) {
            let form = Object.assign(this.form.value);
            const examHour = +form.examHour.replace(':', '');
            const hour = String((form.examDuration / 60).toString().split('.')[0]);
            const min = String(form.examDuration % 60);
            const result = examHour + (+hour.concat(min === "0" ? "00" : min));
            this.endHourExam = result.toString().substring(0, 2) + "H:" + result.toString().substring(2, 4);
            this.disabled = false;
        } else {
            this.disabled = true;
            this.endHourExam = null;
        }
    }

    public onChangeBoc(event) {
        this.onClearBloc(event);
    }

    public onRemoveBloc(event) {
        this.onClearBloc(event);
    }

    public onClearBloc(event) {
        let examHour = +this.form.get("examHour").value.replace(":", "");
        let arg = { blocs: this.form.get("bloc").value, effectDate: this.form.get("examDate").value, hour: examHour };
        this.roomService.getRoomsByBlocs(arg).subscribe(rooms => {
            this.rooms = rooms;
            this.form.get("rooms").setValue(null);
            if (this.rooms.length > 0) {
                this.disabledRoom = false;
            } else {
                this.disabledRoom = true;
            }
        });
    }

    public onChangeDepartmenet(event) {
        this.onClearDepartmenet(event);
    }

    public onRemoveDepartmenet(event) {
        this.onClearDepartmenet(event);
    }

    public onClearDepartmenet(event) {
        let examHour = +this.form.get("examHour").value.replace(":", "");
        let arg = { ups: this.form.get("up").value, effectDate: this.form.get("examDate").value, hour: examHour };
        this.teacherService.getTeachersByUps(arg).subscribe(teachers => {
            this.teachers = teachers;
            this.form.get("teachers").setValue(null);
            if (this.teachers && this.teachers.length > 0) {
                this.disabledTeacher = false;
            } else {
                this.disabledTeacher = true;
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

    private initForm(): void {
        this.form = this.fb.group({
            levels: [null, Validators.required],
            session: [null, Validators.required],
            module: [null, Validators.required],
            classes: [null, Validators.required],
            teachers: [null],
            examDate: [null, Validators.required],
            examHour: [null, Validators.required],
            examDuration: [null, Validators.required],
            rooms: [null],
            bloc: [null, Validators.required],
            up: [null, Validators.required]
        })
    }

}