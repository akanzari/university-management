import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { RefService, TeacherService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { Teacher } from 'src/app/core/models/teacher.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { Department } from 'src/app/core/models';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeacherModalComponent } from './teacher-modal/teacher-modal.component';
import { DisponibilityModalComponent } from './disponibility-modal/disponibility.component';

@Component({
    templateUrl: './teachers.component.html',
    styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements OnInit {

    public config: ConfigColumn;

    public teachersLength: number;

    public form: FormGroup;

    public departements$: Observable<Department[]>;

    constructor(private fb: FormBuilder,
        private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private refService: RefService,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.initForm();
        this.spinner.show();
        this.departements$ = this.refService.getDepartements();
        this.teacherService.getTeachers().subscribe((teachers: Teacher[]) => {
            this.teachersLength = teachers.length;
            this.initTeachersColomns(teachers);
            this.spinner.hide();
        });
    }

    public onSearch() {
        if (this.form.valid) {
            this.spinner.show();
            this.teacherService.searchTeachers(this.form.value).subscribe(teachers => {
                this.teachersLength = teachers.length;
                this.config = { ...this.config, value: teachers };
                this.spinner.hide();
            });
        }
    }

    public getArrayForm(event: DataValue) {
        if (event.action === ActionEnum.LINK) {
            const modal: NgbModalRef = this.modalService.open(DisponibilityModalComponent,
                {
                    size: 'xl',
                    ariaLabelledBy: 'modal-basic-title',
                    keyboard: false,
                    backdrop: 'static',
                    centered: true
                });
                modal.componentInstance.teacherId = event.value.teacherId;
        }
    }

    public openModal() {
        const modal: NgbModalRef = this.modalService.open(TeacherModalComponent,
            {
                size: 'xl',
                ariaLabelledBy: 'modal-basic-title',
                keyboard: false,
                backdrop: 'static',
                centered: true
            });
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.teacherService.addTeacher(dataValue.value).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.teacherService.getTeachers()
                ).subscribe((teachers: Teacher[]) => {
                    this.spinner.hide();
                    this.teachersLength = teachers.length;
                    this.config = { ...this.config, value: teachers };
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

    public reset() {
        this.form.reset();
        this.spinner.show();
        this.teacherService.getTeachers().subscribe(teachers => {
            this.teachersLength = teachers.length;
            this.config = { ...this.config, value: teachers };
            this.spinner.hide();
        });
    }

    private initForm(): void {
        this.form = this.fb.group({
            teacherId: [null],
            cin: [null],
            departement: [null],
            classs: [null]
        })
    }


    private initTeachersColomns(result): void {
        this.config = {
            id: "teacher",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [30, 35, 40, 45]
            },
            columns: [
                {
                    header: "N°",
                    field: "teacherId",
                    filterable: true,
                    sortable: true,
                    width: "10"
                },
                {
                    header: "Enseignant",
                    field: "fullName",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Téléphones",
                    field: "phone",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "E-mail",
                    field: "email",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "UP",
                    field: "up",
                    filterable: true,
                    sortable: true,
                    width: "10"
                },
                {
                    header: "Disponibilités",
                    field: "disponibilities",
                    link: {
                        text: "Liste disponibilités"
                    },
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}