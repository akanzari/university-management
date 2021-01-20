import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin } from 'rxjs';
import { ClassService, RefService, TeacherService } from 'src/app/core/services';
import { CmTbaleContainerComponent } from 'src/app/shared/components/cm-table-container/cm-table-container.component';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';

@Component({
    templateUrl: './assign-classes.component.html',
    styleUrls: ['./assign-classes.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class AssignClassesModalComponent implements OnInit {

    @ViewChild(CmTbaleContainerComponent) child: CmTbaleContainerComponent;

    public hideAddButton: boolean = false;

    public config: ConfigColumn;

    @Input()
    public assignClasses: any[];

    constructor(private refService: RefService,
        private spinner: NgxSpinnerService,
        private classService: ClassService,
        private datePipe: DatePipe,
        private teacherService: TeacherService,
        private activeModal: NgbActiveModal) {
    }

    public getHideAddButton(event: boolean) {
        setTimeout(() => { this.hideAddButton = event }, 0)
    }

    ngOnInit() {
        this.spinner.show();
        forkJoin([
            this.refService.getSemeters(),
            this.teacherService.getTeachers(),
            this.refService.getTypeExam(),
            this.classService.getClasses()]).subscribe(result => {
                this.spinner.hide();
                let table: DisponibilityTable[] = [];
                this.assignClasses.forEach(item => {
                    let semester = (item.semester.code == "1" ? "S1" : "S2") + " - " + this.datePipe.transform(item.semester.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(item.semester.endDate, "dd/MM/yyyy");
                    let period = item.period && item.period.code ? (item.period.code == "1" ? "P1" : "P2") + " - " + this.datePipe.transform(item.period.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(item.period.endDate, "dd/MM/yyyy") : null;
                    table.push(new DisponibilityTable(item.classs.classId, item.coefficient, item.nbrHour, semester, period, item.typeExam, item.teachers));
                });
                this.initDisponibiliesColomns(table, result[0], result[2], result[1], result[3]);
            });
    }

    getArrayForm(event) {
        console.log(event);
    }

    public onChangeSelectFromTable(data) {
        if (data.field === "semester") {
            let disabledColumns = this.config.columns.filter(item => item.hasOwnProperty("disabled"));
            let column = this.config.columns.find(item => item.field === "period");
            if (data.event) {
                if (column && column.monoselectConfig) {
                    let form: FormGroup = data.form;
                    form.get("period").value ? form.get("period").setValue(null) : null;
                }
                column.monoselectConfig.options = data.event.periods;
                if (disabledColumns) {
                    disabledColumns.forEach(element => element.disabled = false);
                }                console.log(data.event.periods);
                
            } else {
                if (disabledColumns) {
                    let form: FormGroup = data.form;
                    form.get("period").value ? form.get("period").setValue(null) : null;
                    disabledColumns.forEach(element => element.disabled = true);
                }
            }
        }
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initDisponibiliesColomns(result, semeters, typeExams, teachers, classes): void {
        this.config = {
            id: "disponibility",
            value: result,
            addable: true,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [20, 25, 30, 35]
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
                    header: "Class",
                    field: "classe",
                    type: "monoselect",
                    placeholder: "-- Classe --",
                    monoselectConfig: { type: "objects", options: classes, bindLabel: "classId" },
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Coefficient",
                    field: "coefficient",
                    type: "number",
                    placeholder: "-- Coefficient --",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Nombre d'heures",
                    field: "nbrHour",
                    type: "number",
                    placeholder: "-- Nombre d'heures --",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Enseignants",
                    field: "teachers",
                    type: "multiselect",
                    placeholder: "-- Enseignants --",
                    multiselectConfig: { type: "objects", options: teachers, bindLabel: "fullName" },
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Semestre",
                    field: "semester",
                    type: "monoselect",
                    placeholder: "-- Semester --",
                    monoselectConfig: { type: "objects", options: semeters, bindLabel: "label" },
                    event: true,
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Période",
                    field: "period",
                    type: "monoselect",
                    placeholder: "-- Période --",
                    monoselectConfig: { type: "objects", options: null, bindLabel: "label" },
                    disabled: true,
                    editable: true,
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Type d'épreuve",
                    field: "typeExam",
                    type: "monoselect",
                    placeholder: "-- Type d'épreuve --",
                    monoselectConfig: { type: "strings", options: typeExams },
                    filterable: true,
                    sortable: true,
                    width: "11"
                }
            ]
        }
    }
}

export class DisponibilityTable {
    public classe: string;
    public coefficient: string;
    public nbrHour: string;
    public semester: string;
    public period: string;
    public typeExam: string;
    public teachers: any[];

    constructor(classe: string, coefficient: string, nbrHour: string, semester: string, period: string, typeExam: string, teachers: any[]) {
        this.classe = classe;
        this.coefficient = coefficient;
        this.nbrHour = nbrHour;
        this.semester = semester;
        this.period = period;
        this.typeExam = typeExam;
        this.teachers = teachers;
    }
}