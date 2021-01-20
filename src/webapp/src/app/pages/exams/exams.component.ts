import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { CreateExamRequest, Exam, UpdateExamRequest } from 'src/app/core/models';
import { ExamService } from 'src/app/core/services';
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

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private examService: ExamService) {
    }

    ngOnInit() {
        this.examService.getExams().subscribe(exams => this.initExmasColomns(exams));
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
                ).subscribe((exams: Exam[]) => {
                    this.spinner.hide();
                    this.config = { ...this.config, value: exams };
                    modal.componentInstance.setIsSaved({ isSaved: true });
                }, error => {
                    this.spinner.hide();
                    if (error.error.code === 701) {
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteClass((event.value as Exam)) : null);
        } else {
            const modal: NgbModalRef = this.initPopUpCrud(ExamModalComponent);
            modal.componentInstance.editExam = event.value as Exam;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                    concat(
                        this.examService.updateExam(dataValue.value as UpdateExamRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.examService.getExams()
                    ).subscribe((exams: Exam[]) => {
                        this.spinner.hide();
                        this.config = { ...this.config, value: exams };
                        modal.componentInstance.setIsSaved({ isSaved: true });
                    }, error => {
                        this.spinner.hide();
                        if (error.error.code === 701) {
                            modal.componentInstance.setIsSaved({ isSaved: false, code: error.error.code });
                        }
                    })
                }
            });
        }
    }

    private deleteClass(event): void {
        this.spinner.show();
        concat(
            this.examService.deleteExam(event.examId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.examService.getExams()
        ).subscribe((exams: Exam[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: exams };
        })
    }

    private initPopUp(content: any): NgbModalRef {
        return this.modalService.open(content,
            {
                size: 'lg',
                ariaLabelledBy: 'modal-basic-title',
                keyboard: false,
                backdrop: 'static',
                centered: true
            });
    }

    private initPopUpCrud(content: any): NgbModalRef {
        return this.modalService.open(content,
            {
                size: 'lg',
                windowClass: 'modal-adaptive ',
                ariaLabelledBy: 'modal-basic-title',
                keyboard: false,
                backdrop: 'static',
                centered: true
            });
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
            actions: [
                {
                    name: ActionEnum.DELETE,
                    icon: {
                        class: "icon-trash size-16",
                        tooltip: "Supprimer"
                    }
                }
            ],
            columns: [
                {
                    header: "Classe",
                    field: "label",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nombre des groupes",
                    field: "nbrStudents",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Nombre d'étudiants",
                    field: "nbrGroups",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Spécialiés",
                    field: "speciality.label",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Date de création",
                    field: "createdDate",
                    pipe: {
                        function: this.datePipe
                    },
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}