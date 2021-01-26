import { DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { Exam, UpdateExamRequest, User } from 'src/app/core/models';
import { ExamService, ExcelService, TokenService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { ExamModalComponent } from './exam-modal/exam-modal.component';

@Component({
    templateUrl: './exams.component.html',
    styleUrls: ['./exams.component.scss'],
    providers: [DatePipe]
})
export class ExamsComponent implements OnInit {

    @ViewChild('content')
    private teacherG: ElementRef;

    @ViewChild('content1')
    private roomG: ElementRef;

    public config: ConfigColumn;

    public isStudent: boolean;
    public isTeacher: boolean;

    public teachers: any;

    public rooms: any;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private excelService: ExcelService,
        private tokenService: TokenService,
        private examService: ExamService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.examService.getExams().subscribe(exams => {
            this.initExmasColomns(this.initTable(exams));
            this.tokenService.getProfile().then((user: User) => {
                this.spinner.hide();
                this.isStudent = user.roles.includes("STUDENT");
                this.isTeacher = user.roles.includes("TEACHER");
                if (this.isStudent === false && this.isTeacher === false) {
                    this.config = {
                        ...this.config, columns: [

                            {
                                header: "Classe",
                                field: "class",
                                filterable: true,
                                sortable: true,
                            },
                            {
                                header: "DSEX",
                                field: "session",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Type d'épreuve",
                                field: "typeExam",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Date d'épreuve",
                                field: "examDate",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Heure d'épreuve",
                                field: "examHour",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Durée d'épreuve",
                                field: "examDuration",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Module",
                                field: "module",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Surveillance",
                                field: "teachers",
                                badge: {
                                    class: "badge badge-valid",
                                    link: true
                                },
                                filterable: true,
                                sortable: true,
                                width: "10"
                            },
                            {
                                header: "Salles",
                                field: "rooms",
                                badge: {
                                    class: "badge badge-secondary",
                                    link: true
                                },
                                filterable: true,
                                sortable: true,
                                width: "10"
                            }
                        ]
                    };
                    this.config = {
                        ...this.config, actions: [
                            {
                                name: ActionEnum.UPDATE,
                                icon: {
                                    class: "icon-edit size-16",
                                    tooltip: "Modifier"
                                }
                            },
                            {
                                name: ActionEnum.DELETE,
                                icon: {
                                    class: "icon-trash size-16",
                                    tooltip: "Supprimer"
                                }
                            }
                        ]
                    };
                } else {
                    this.config = {
                        ...this.config, columns: [

                            {
                                header: "Classe",
                                field: "class",
                                filterable: true,
                                sortable: true,
                            },
                            {
                                header: "DSEX",
                                field: "session",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Type d'épreuve",
                                field: "typeExam",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Date d'épreuve",
                                field: "examDate",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Heure d'épreuve",
                                field: "examHour",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Durée d'épreuve",
                                field: "examDuration",
                                filterable: true,
                                sortable: true
                            },
                            {
                                header: "Module",
                                field: "module",
                                filterable: true,
                                sortable: true
                            }
                        ]
                    };
                }
            });
        });
    }

    private initTable(exams): any[] {
        let result: any[] = [];
        if (exams && exams.length > 0) {
            exams.forEach(exam => {
                if (exam.classes && exam.classes.length > 0) {
                    exam.classes.forEach(element => {
                        let session: string;
                        if (exam.session === "S1P1" || exam.session === "S2P1") {
                            session = "DS"
                        } else if (exam.session === "S1P2" || exam.session === "S2P2" || exam.session === "SP") {
                            session = "Examen"
                        } else if (exam.session === "SR") {
                            session = "Rattrapage"
                        }
                        result.push({
                            examId : exam.examId,
                            session: session,
                            sessionT: exam.session,
                            class: element.classId,
                            classC: element,
                            examDate: element.examDate,
                            typeExam: element.typeExam,
                            examHour: element.examHour.toString().substring(0, 2) + "H:" + element.examHour.toString().substring(2, 4),
                            examDuration: element.examDuration + " min",
                            module: element.module.designation,
                            moduleI: element.module.moduleId,
                            teachersId: element.teachers.map(teacher => teacher.teacherId),
                            teachersNames: element.teachers.map(teacher => teacher.fullName),
                            teachers: element.teachers.map(teacher => teacher.fullName).length,
                            rooms: element.rooms.map(room => room.classRoomId).length,
                            roomsNames: element.rooms.map(room => room.classRoomId),
                            teachersC: element.teachers,
                            roomsC: element.rooms
                        });
                    });
                }
            })
        }
        return result;
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUpCrud(ExamModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.examService.addExam(dataValue.value).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.examService.getExams()
                ).subscribe((exams: any[]) => {
                    this.spinner.hide();
                    this.config = { ...this.config, value: this.initTable(exams) };
                    modal.componentInstance.setIsSaved({ isSaved: true });
                }, error => {
                    this.spinner.hide();
                    if (error.error.code) {
                        modal.componentInstance.setIsSaved({ isSaved: false, code: error.error.code });
                    }
                })
            }
        });
    }

    public getArrayForm(event: DataValue) {
        if (event.action === ActionEnum.DELETE) {
            const modal: NgbModalRef = this.initPopUp(RemovePopupComponent);
            modal.componentInstance.config = { title: "Confirmation de suppression", message: "Est-ce que vous confirmez la suppression définitive ?" };
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteClass((event.value)) : null);
        } else if (event.action === ActionEnum.UPDATE) {
            const modal: NgbModalRef = this.initPopUpCrud(ExamModalComponent);
            modal.componentInstance.editExam = event.value;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                    concat(
                        this.examService.updateExam(dataValue.value as UpdateExamRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.examService.getExams()
                    ).subscribe((exams: any[]) => {
                        this.spinner.hide();
                        this.config = { ...this.config, value: this.initTable(exams) };
                        modal.componentInstance.setIsSaved({ isSaved: true });
                    }, error => {
                        this.spinner.hide();
                        if (error.error.code === 701) {
                            modal.componentInstance.setIsSaved({ isSaved: false, code: error.error.code });
                        }
                    })
                }
            });
        } else {
            this.teachers = null;
            this.rooms = null;
            if (event.field === "teachers") {
                if (this.isOdd(event.value.teachers) === 0) {
                    let nt = event.value.teachers / 2;
                    let ft = event.value.teachersNames.slice(0, nt);
                    let lt = event.value.teachersNames.slice(nt, nt * 2);
                    this.teachers = { g1: ft, g2: lt };
                } else {
                    let nt = Math.trunc(event.value.teachers / 2);
                    let ft = event.value.teachersNames.slice(0, nt);
                    let lt = event.value.teachersNames.slice(nt, nt * 2);
                    lt.push(event.value.teachersNames[event.value.teachers - 1]);
                    this.teachers = { g1: ft, g2: lt };
                }
                this.modalService.open(this.teacherG,
                    {
                        size: 'lg',
                        ariaLabelledBy: 'modal-basic-title',
                        keyboard: false,
                        backdrop: 'static',
                        centered: true
                    })
            } else {
                if (this.isOdd(event.value.rooms) === 0) {
                    let nr = event.value.rooms / 2;
                    let fr = event.value.roomsNames.slice(0, nr);
                    let lr = event.value.roomsNames.slice(nr, nr * 2);
                    this.rooms = { g1: fr, g2: lr };
                } else {
                    let nr = Math.trunc(event.value.rooms / 2);
                    let fr = event.value.roomsNames.slice(0, nr);
                    let lr = event.value.roomsNames.slice(nr, nr * 2);
                    lr.push(event.value.roomsNames[event.value.rooms - 1]);
                    this.rooms = { g1: fr, g2: lr };
                }
                this.modalService.open(this.roomG,
                    {
                        size: 'lg',
                        ariaLabelledBy: 'modal-basic-title',
                        keyboard: false,
                        backdrop: 'static',
                        centered: true
                    })
            }
        }
    }

    private isOdd(num) { return num % 2; }

    private deleteClass(event): void {
        this.spinner.show();
        concat(
            this.examService.deleteExam({ examId: event.examId, teacherIds: event.teachersId, roomIds: event.roomsNames, classe: event.class }).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.examService.getExams()
        ).subscribe((exams: any[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: this.initTable(exams) };
        })
    }

    private initPopUp(content: any): NgbModalRef {
        return this.modalService.open(content,
            {
                size: 'xl',
                ariaLabelledBy: 'modal-basic-title',
                keyboard: false,
                backdrop: 'static',
                centered: true
            });
    }

    private initPopUpCrud(content: any): NgbModalRef {
        return this.modalService.open(content,
            {
                size: 'xl',
                ariaLabelledBy: 'modal-basic-title',
                keyboard: false,
                backdrop: 'static',
                centered: true
            });
    }

    public export() {
        this.examService.getExams().subscribe(exams => this.excelService.exportAsExcelFile(exams));
    }

    private initExmasColomns(result): void {
        this.config = {
            id: "class",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 10,
                rowsPerPageOptions: [10, 15, 20, 25]
            },
            columns: []
        }
    }
}