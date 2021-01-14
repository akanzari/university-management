import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { TeacherService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { Teacher } from 'src/app/core/models/teacher.model';

@Component({
    templateUrl: './teachers.component.html',
    styleUrls: ['./teachers.component.scss'],
    providers: [DatePipe]
})
export class TeachersComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.teacherService.getTeachers().subscribe((teachers: Teacher[]) => {
            this.initTeachersColomns(teachers);
            this.spinner.hide();
        });
    }

    public getArrayForm(event: DataValue) {
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
            /*actions: [
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
            ],*/
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
                    field: "phones",
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
                    header: "CIN",
                    field: "cin",
                    filterable: true,
                    sortable: true,
                    width: "10"
                },
                {
                    header: "Dépatement",
                    field: "departement",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nbr de surveillance",
                    field: "",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nbr heuere surveillance",
                    field: "",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}