import { DatePipe } from '@angular/common';
import { Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { DisponibilityService } from 'src/app/core/services';
import { CmTbaleContainerComponent } from 'src/app/shared/components/cm-table-container/cm-table-container.component';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';

@Component({
    templateUrl: './disponibility.component.html',
    styleUrls: ['./disponibility.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class DisponibilityModalComponent implements OnInit {

    @ViewChild(CmTbaleContainerComponent) child: CmTbaleContainerComponent;

    @Input()
    private roomId: string;

    public hideAddButton: boolean = false;

    public config: ConfigColumn;

    constructor(private activeModal: NgbActiveModal,
        private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private disponibilityService: DisponibilityService) {
    }

    ngOnInit() {
        this.disponibilityService.getDisponibilitiesByRoom(this.roomId).subscribe(items => {
            this.initDisponibiliesColomns(items && items.length > 0 ? this.initTable(items) : []);
        })
    }

    private initTable(array): DisponibilityDTO[] {
        let result: DisponibilityDTO[] = [];
        if (array && array.length > 0) {
            array.forEach(item => {
                let endHour;
                let startHour;
                if (item.endHour.toString().length === 3) {
                    endHour = "0" + item.endHour.toString();
                } else if (item.endHour.toString().length === 1) {
                    endHour = "000" + item.endHour.toString();
                } else if (item.endHour.toString().length === 2) {
                    endHour = "00" + item.endHour.toString();
                } else {
                    endHour = item.endHour.toString();
                }
                if (item.startHour.toString().length === 3) {
                    startHour = "0" + item.startHour.toString();
                } else if (item.startHour.toString().length === 1) {
                    startHour = "000" + item.startHour.toString();
                } else if (item.startHour.toString().length === 2) {
                    startHour = "00" + item.startHour.toString();
                } else {
                    startHour = item.startHour.toString();
                }
                result.push(new DisponibilityDTO(item.startDate, item.endDate, startHour.substring(0, 2) + "H:" + startHour.substring(2, 4), endHour.substring(0, 2) + "H:" + endHour.substring(2, 4), item.motif, item.disponibilityId));
            })
        }
        return result;
    }

    public getArrayForm(event: DataValue) {
        if (event.action === ActionEnum.CREATE) {
            this.spinner.show();
            let arg = new CreateDisponibilityRequest(event.value.startDate, event.value.endDate, +event.value.startHour.replace(":", ""), +event.value.endHour.replace(":", ""), event.value.motif);
            concat(
                this.disponibilityService.addDisponibilityToRoom(this.roomId, arg).pipe(switchMapTo(EMPTY)),
                timer(1000).pipe(switchMapTo(EMPTY)),
                this.disponibilityService.getDisponibilitiesByRoom(this.roomId)
            ).subscribe((items: any[]) => {
                this.spinner.hide();
                this.config = { ...this.config, value: this.initTable(items) };
            }, error => {
                this.spinner.hide();
            })
        } else if (event.action === ActionEnum.UPDATE) {
            this.spinner.show();
            let arg = new CreateDisponibilityRequest(event.value.startDate, event.value.endDate, +event.value.startHour.replace(":", ""), +event.value.endHour.replace(":", ""), event.value.motif, event.value.disponibilityId);
            concat(
                this.disponibilityService.updateDisponibility(arg).pipe(switchMapTo(EMPTY)),
                timer(1000).pipe(switchMapTo(EMPTY)),
                this.disponibilityService.getDisponibilitiesByRoom(this.roomId)
            ).subscribe((items: any[]) => {
                this.spinner.hide();
                this.config = { ...this.config, value: this.initTable(items) };
            }, error => {
                this.spinner.hide();
            })
        } else if (event.action === ActionEnum.DELETE) {
            const modal: NgbModalRef = this.modalService.open(RemovePopupComponent,
                {
                    size: 'lg',
                    ariaLabelledBy: 'modal-basic-title',
                    keyboard: false,
                    backdrop: 'static',
                    centered: true
                });
            modal.componentInstance.config = { title: "Confirmation de suppression", message: "Est-ce que vous confirmez la suppression définitive ?" };
            modal.componentInstance.sendData.subscribe((data: boolean) => this.deleteModule(this.roomId, event.value.disponibilityId));
        }
    }

    private deleteModule(roomId, disponibilityId) {
        this.spinner.show();
        concat(
            this.disponibilityService.deleteDisponibilityFromRoom(roomId, disponibilityId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.disponibilityService.getDisponibilitiesByRoom(this.roomId)
        ).subscribe((items: any[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: this.initTable(items) };
        }, error => {
            this.spinner.hide();
        })
    }

    public getHideAddButton(event: boolean) {
        setTimeout(() => { this.hideAddButton = event }, 0)
    }

    public closeModal(): void {
        this.activeModal.close();
    }

    private initDisponibiliesColomns(result): void {
        this.config = {
            id: "disponibilityT",
            value: result,
            addable: true,
            sortableBy: "exactDate",
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
                    header: "Date début",
                    field: "startDate",
                    type: "calendar",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Date fin",
                    field: "endDate",
                    type: "calendar",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Heure début",
                    field: "startHour",
                    type: "time",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Heure fin",
                    field: "endHour",
                    type: "time",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Motif",
                    field: "motif",
                    type: "text",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }

}

export class DisponibilityDTO {
    public startDate: Date;
    public endDate: Date;
    public startHour: string;
    public endHour: string;
    public motif: string;
    public disponibilityId: string;

    constructor(startDate: Date, endDate: Date, startHour: string, endHour: string, motif: string, disponibilityId?: string) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.motif = motif;
        this.disponibilityId = disponibilityId;
    }
}

export class CreateDisponibilityRequest {
    public startDate: Date;
    public endDate: Date;
    public startHour: number;
    public endHour: number;
    public motif: string;
    public disponibilityId: string;

    constructor(startDate: Date, endDate: Date, startHour: number, endHour: number, motif: string, disponibilityId?: string) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.motif = motif;
        this.disponibilityId = disponibilityId;
    }
}