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

    @Input()
    private disponibilities: any[];

    public config: ConfigColumn;

    constructor(private activeModal: NgbActiveModal) {
    }

    ngOnInit() {
        let result: DisponibilityTable[] = [];
        this.disponibilities.forEach(item => {
            result.push(new DisponibilityTable(item.supervisionDate, item.supervisionDuration, item.supervisionHour));
        })
        this.initDisponibiliesColomns(result);
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initDisponibiliesColomns(result): void {
        this.config = {
            id: "disponibilityT",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [20, 25, 30, 35]
            },
            columns: [
                {
                    header: "Date d'examen",
                    field: "supervisionDate",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Heure d'examen",
                    field: "supervisionHour",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Durée d'examen",
                    field: "supervisionDuration",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }

}

export class DisponibilityTable {
    public supervisionDate: string;
    public supervisionDuration: string;
    public supervisionHour: string;

    constructor(supervisionDate: string, supervisionDuration: string, supervisionHour: string) {
        this.supervisionDate = supervisionDate;
        this.supervisionDuration = supervisionDuration + " min";
        this.supervisionHour = supervisionHour.toString().substring(0, 2) + "H:" + supervisionHour.toString().substring(2, 4);
    }
}