import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { RoomService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { Bloc, CreateRoomRequest, Room, UpdateRoomRequest } from 'src/app/core/models';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { RoomModalComponent } from './room-modal/room-modal.component';
import { Site } from 'src/app/core/models/site.modal';

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
        this.roomService.getRooms().subscribe((rooms: Room[]) => this.initRoomsColomns(this.initTable(rooms)));
    }

    private initTable(rooms: Room[]): RoomTable[] {
        console.log(rooms);

        let table: RoomTable[] = [];
        rooms.forEach((room: Room) => {
            let date;
            let hour;
            if (room.startDate && room.endDate) {
                date = this.datePipe.transform(room.startDate, 'dd/MM/yyyy') + " à " + this.datePipe.transform(room.endDate, 'dd/MM/yyyy');
            }
            if (room.startHour != '0' && room.endHour != '0') {
                hour = this.getHoure(+room.startHour, +room.endHour);
            }
            table.push(new RoomTable(room.classRoomId, room.label, room.capacity, room.site, room.bloc, date, hour, room.reason, room.startDate, room.endDate, room.startHour, room.endHour));
        })
        return table;
    }

    private getHoure(s: number, e: number): string {
        let result: string;
        if (s >= 0 && e >= 0) {
            let str1 = s ? s.toString() : '00:00';
            let str2 = e ? e.toString() : '00:00';
            if (str1.length === 3) {
                str1 = "0" + str1;
            }
            if (str2.length === 3) {
                str2 = "0" + str2;
            }
            result = str1.substring(0, 2).concat(":").concat(str1.slice(-2)) + " à " + str2.substring(0, 2).concat(":").concat(str2.slice(-2));
        }
        return result;
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
                    this.config = { ...this.config, value: this.initTable(rooms) };
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
                        this.config = { ...this.config, value: this.initTable(rooms) };
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
            this.roomService.deleteRoom(event.classRoomId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.roomService.getRooms()
        ).subscribe((rooms: Room[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: this.initTable(rooms) };
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
                    header: "Salle",
                    field: "label",
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
                    field: "site.label",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Bloc",
                    field: "bloc.label",
                    filterable: true,
                    sortable: true,
                    width: "11"
                },
                {
                    header: "Date indisponibilité",
                    field: "dates",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Heure indisponibilité",
                    field: "hours",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Motif indisponibilité",
                    field: "reason.label",
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