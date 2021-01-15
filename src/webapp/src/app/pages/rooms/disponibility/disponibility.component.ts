import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable } from 'rxjs';
import { RefService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';

@Component({
    templateUrl: './disponibility.component.html',
    styleUrls: ['./disponibility.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class DisponibilityModalComponent implements OnInit {

    public config: ConfigColumn;

    @Input()
    public disponibilities;

    constructor(private refService: RefService,
        private spinner: NgxSpinnerService,
        private activeModal: NgbActiveModal) {
    }

    ngOnInit() {
        if (this.disponibilities && this.disponibilities.length > 0) {
            this.spinner.show();
            let disponibilities: DisponibilityTable[] = [];
            this.disponibilities.forEach(element => {

                let semester: Observable<any>;
                if (element.semester) {
                    semester = this.refService.getSemeterById(element.semester);
                }
                let seance: Observable<any>;
                if (element.seance) {
                    seance = this.refService.getSeanceById(element.seance);
                }
                let day: Observable<any>;
                if (element.day != 0) {
                    day = this.refService.getDayById(element.day);
                }

                forkJoin([semester, seance, day]).subscribe(result => {
                    this.spinner.hide();
                    disponibilities.push(new DisponibilityTable(element.year,
                        result[1].meetingId + " - " + result[1].startHour + " à " + result[1].endHour,
                        result[0].code == "1" ? "S1" : "S2" + " - " + result[0].startDate + " à " + result[0].endDate,
                        result[2].label,
                        element.motif));
                    this.initDisponibiliesColomns(disponibilities);
                })
            });
        }
    }

    getArrayForm(event) {

    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initDisponibiliesColomns(result): void {
        this.config = {
            id: "disponibility",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [20, 25, 30, 35]
            },
            actions: [
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
            ],
            columns: [
                {
                    header: "Année",
                    field: "year",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Séance",
                    field: "seance",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Semestre",
                    field: "semester",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Jour",
                    field: "day",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Motif",
                    field: "motif",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}

export class DisponibilityTable {
    public year: string;
    public seance: string;
    public semester: string;
    public day: string;
    public motif: string;

    constructor(year: string, seance: string, semester: string, day: string, motif: string) {
        this.year = year;
        this.seance = seance;
        this.semester = semester;
        this.day = day;
        this.motif = motif;
    }
}