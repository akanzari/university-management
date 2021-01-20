import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin } from 'rxjs';
import { RefService } from 'src/app/core/services';
import { CmTbaleContainerComponent } from 'src/app/shared/components/cm-table-container/cm-table-container.component';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';

@Component({
    templateUrl: './disponibility.component.html',
    styleUrls: ['./disponibility.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class DisponibilityModalComponent implements OnInit {

    @ViewChild(CmTbaleContainerComponent) child: CmTbaleContainerComponent;

    public hideAddButton: boolean = false;

    public config: ConfigColumn;

    @Input()
    public disponibilities;

    constructor(private refService: RefService,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private activeModal: NgbActiveModal) {
    }

    public onChangeSelectFromTable(data) {

    }

    public getHideAddButton(event: boolean) {
        setTimeout(() => { this.hideAddButton = event }, 0)
    }

    ngOnInit() {
        this.spinner.show();
        forkJoin([
            this.refService.getSemeters(),
            this.refService.getSeances(),
            this.refService.getDays()]).subscribe(result => {
                this.spinner.hide();
                let table: DisponibilityTable[] = [];
                this.disponibilities.forEach(item => {
                    let semester = (item.semester.code == "1" ? "S1" : "S2") + " - " + this.datePipe.transform(item.semester.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(item.semester.endDate, "dd/MM/yyyy");
                    let seance = item.seance.seanceId + " - " + item.seance.startHour + " à " + item.seance.endHour;
                    table.push(new DisponibilityTable(item.disponibilityId, seance, semester, item.day, item.motif));
                });
                this.initDisponibiliesColomns(table, result[0], result[1], result[2]);
            });
    }

    getArrayForm(event) {
        console.log(event);

    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initDisponibiliesColomns(result, semeters, seances, days): void {
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
                    header: "Semestre",
                    type: "monoselect",
                    monoselectConfig: { type: "objects", options: semeters, bindLabel: "label" },
                    field: "semester",
                    placeholder: "-- Semestre --",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Jour",
                    field: "day",
                    type: "monoselect",
                    monoselectConfig: { type: "objects", options: days, bindLabel: "label" },
                    placeholder: "-- Jour --",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Séance",
                    field: "seance",
                    type: "monoselect",
                    monoselectConfig: { type: "objects", options: seances, bindLabel: "label" },
                    placeholder: "-- Séance --",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Motif",
                    field: "motif",
                    type: "text",
                    placeholder: "-- Motif --",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}

export class DisponibilityTable {
    public disponibilityId: string;
    public semester: string;
    public seance: string;
    public day: string;
    public motif: string;

    constructor(disponibilityId: string, seance: string, semester: string, day: string, motif: string) {
        this.disponibilityId = disponibilityId;
        this.seance = seance;
        this.semester = semester;
        this.day = day;
        this.motif = motif;
    }
}