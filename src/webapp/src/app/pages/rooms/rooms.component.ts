import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { RefService, RoomService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { Bloc, CreateRoomRequest, Room, UpdateRoomRequest } from 'src/app/core/models';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { RoomModalComponent } from './add-room-modal/add-room.component';
import { Site } from 'src/app/core/models/site.modal';
import { DisponibilityModalComponent } from './disponibility-modal/disponibility.component';
import { UpdateRoomModalComponent } from './update-room-modal/update-room.component';

@Component({
    selector: 'rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.scss'],
    providers: [DatePipe]
})
export class RoomsComponent implements OnInit {

    public config: ConfigColumn;

    public roomsLength: number;

    constructor(private modalService: NgbModal,
        private refService: RefService,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private roomService: RoomService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.roomService.getRooms().subscribe((rooms: Room[]) => {
            this.initRoomsColomns(rooms);
            this.roomsLength = rooms.length;
            this.spinner.hide();
        });
    }

    public openModal() {
        const modal: NgbModalRef = this.modalService.open(RoomModalComponent,
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
                    this.roomService.addRoom(dataValue.value as CreateRoomRequest).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.roomService.getRooms()
                ).subscribe((rooms: Room[]) => {
                    this.spinner.hide();
                    this.roomsLength = rooms.length;
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
        } else if (event.action === ActionEnum.UPDATE) {
            const modal: NgbModalRef = this.initPopUp(UpdateRoomModalComponent);
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
                        this.roomsLength = rooms.length;
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
        } else if (event.action === ActionEnum.LINK) {
            this.spinner.show();
            this.roomService.getDisponibilitiesByRoom(event.value.classRoomId).subscribe(items => {
                const modal: NgbModalRef = this.modalService.open(DisponibilityModalComponent,
                    {
                        size: 'lg',
                        windowClass: 'modal-adaptive',
                        ariaLabelledBy: 'modal-basic-title',
                        keyboard: false,
                        backdrop: 'static',
                        centered: true
                    });
                modal.componentInstance.disponibilities = items;
                this.spinner.hide();
            })
        }
    }

    private deleteClass(event): void {
        this.spinner.show();
        concat(
            this.roomService.deleteRoom(event.classRoomId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.roomService.getRooms()
        ).subscribe((rooms: Room[]) => {
            this.spinner.hide();
            this.roomsLength = rooms.length;
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
                centered: false
            });
    }

    private initRoomsColomns(result): void {
        this.config = {
            id: "room",
            value: result,
            sortableBy: "code",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [30, 35, 40, 45]
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
                    header: "Salle",
                    field: "classRoomId",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Capacité",
                    field: "capacity",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Site",
                    field: "pole",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Bloc",
                    field: "bloc",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Disponibilités",
                    field: "nothing",
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

export class RoomTable {
    public classRoomId: string;
    public label: string;
    public capacity: string;
    public site: Site;
    public bloc: Bloc;
    public dates: string;
    public hours: string;
    public startDate: string;
    public endDate: string;
    public startHour: string;
    public endHour: string;
    public reason: any;

    constructor(classRoomId: string, label: string, capacity: string, site: Site, bloc: Bloc, dates: string, hours: string, reason: any, startDate: string, endDate: string, startHour: string, endHour: string) {
        this.classRoomId = classRoomId;
        this.label = label;
        this.capacity = capacity;
        this.site = site;
        this.bloc = bloc;
        this.dates = dates;
        this.hours = hours;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}