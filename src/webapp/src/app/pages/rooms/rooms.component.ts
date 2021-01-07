import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { RoomService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { CreateRoomRequest, Room, UpdateRoomRequest } from 'src/app/core/models';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { RoomModalComponent } from './room-modal/room-modal.component';

@Component({
    selector: 'rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss'],
    providers: [DatePipe]
})
export class RoomsComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private roomService: RoomService) {
    }

    ngOnInit() {
        this.roomService.getRooms().subscribe((rooms: Room[]) => this.initRoomsColomns(rooms));
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(RoomModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.roomService.addRoom(dataValue.value as CreateRoomRequest).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.roomService.getRooms()
                ).subscribe((rooms: Room[]) => {
                    this.spinner.hide();
                    this.config = { ...this.config, value: rooms };
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteClass((event.value as Room)) : null);
        } else {
            const modal: NgbModalRef = this.initPopUp(RoomModalComponent);
            modal.componentInstance.editRoom = event.value as Room;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                    concat(
                        this.roomService.updateRoom(dataValue.value as UpdateRoomRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.roomService.getRooms()
                    ).subscribe((rooms: Room[]) => {
                        this.spinner.hide();
                        this.config = { ...this.config, value: rooms };
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
            this.roomService.deleteRoom(event.classId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.roomService.getRooms()
        ).subscribe((rooms: Room[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: rooms };
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

    private initRoomsColomns(result): void {
        this.config = {
            id: "room",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 10,
                rowsPerPageOptions: [10, 15, 20, 25]
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
                    header: "Code",
                    field: "code",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Salle",
                    field: "label",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Capacité",
                    field: "capacity",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Site",
                    field: "sites",
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