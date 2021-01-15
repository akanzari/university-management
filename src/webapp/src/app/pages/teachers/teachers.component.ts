import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { TeacherService } from 'src/app/core/services';
import { ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { Teacher } from 'src/app/core/models/teacher.model';

@Component({
    templateUrl: './teachers.component.html',
    styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private spinner: NgxSpinnerService,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.teacherService.getTeachers().subscribe((teachers: Teacher[]) => {
            this.initTeachersColomns(teachers);
            this.spinner.hide();
        });
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
                    field: "departement.label",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nbr de surveillance",
                    field: "nbrSurveillance",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nbr heuere surveillance",
                    field: "nbrHeureSurveillance",
                    filterable: true,
                    sortable: true,
                    width: "13"
                }
            ]
        }
    }
}