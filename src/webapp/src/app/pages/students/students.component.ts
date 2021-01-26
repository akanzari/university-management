import { Component } from '@angular/core';
import { Validators } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { ClassService, StudentService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { StudentModalComponent } from './student-modal/student-modal.component';

@Component({
    selector: 'students',
    templateUrl: './students.component.html',
    styleUrls: ['./students.component.scss']
})
export class StudentsComponent {

    public config: ConfigColumn;

    public studentsLength: number;

    constructor(private modalService: NgbModal,
        private classService: ClassService,
        private spinner: NgxSpinnerService,
        private studentService: StudentService) {
    }

    ngOnInit() {
        this.studentService.getStudents().subscribe(students => {
            this.studentsLength = students.length;
            this.classService.getClasses().subscribe(classes => {
                this.initStudentColomns(classes, students);
            });
        });
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(StudentModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.studentService.addStudent(dataValue.value).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.studentService.getStudents()
                ).subscribe((students: any[]) => {
                    this.spinner.hide();
                    this.studentsLength = students.length;
                    this.config = { ...this.config, value: students };
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteModule((event.value)) : null);
        } else if (event.action === ActionEnum.UPDATE) {
        }
    }

    private deleteModule(event): void {
        this.spinner.show();
        concat(
            this.studentService.deleteStudent(event.studentId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.studentService.getStudents()
        ).subscribe((students: any[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.studentsLength = students.length;
            this.config = { ...this.config, value: students };
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

    private initStudentColomns(classes,  result): void {
        this.config = {
            id: "user",
            value: result,
            sortableBy: "cin",
            pagination: {
                paginate: true,
                rowsPerPage: 10,
                rowsPerPageOptions: [10, 15, 20, 25]
            },
            actions: [
                {
                    name: ActionEnum.UPDATE
                },
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
                    header: "Etudiant",
                    field: "fullName",
                    type: "text",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Email",
                    field: "email",
                    type: "text",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "CIN",
                    field: "cin",
                    type: "number",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Classe",
                    field: "classs",
                    type: "monoselect",
                    monoselectConfig: { type: "objects", options: classes, bindLabel: "label" },
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}