import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IAMService } from 'src/app/core/services';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { DatePipe } from "@angular/common";
import { UserModalComponent } from './user-modal/user-modal.component';
import { CreateUserRequest, UpdateUserRequest, User } from 'src/app/core/models';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss'],
    providers: [DatePipe]
})
export class UsersComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.iamService.getUsers().subscribe(users => this.initUserColomns(users))
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(UserModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.iamService.addUser(dataValue.value as CreateUserRequest).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.iamService.getUsers()
                ).subscribe((users: User[]) => {
                    this.spinner.hide();
                    modal.componentInstance.setIsSaved({ isSaved: true });
                    this.config = { ...this.config, value: users };
                })
            }
        });
    }

    public getArrayForm(event: DataValue) {
        console.log(event);
        
        if (event.action === ActionEnum.DELETE) {
            const modal: NgbModalRef = this.initPopUp(RemovePopupComponent);
            modal.componentInstance.config = { title: "Confirmation de suppression", message: "Est-ce que vous confirmez la suppression définitive ?" };
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteUser((event.value as User)) : null);
        } else {
            const modal: NgbModalRef = this.initPopUp(UserModalComponent);
            modal.componentInstance.editUser = event.value as User;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                    concat(
                        this.iamService.updateUser(dataValue.value as UpdateUserRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.iamService.getUsers()
                    ).subscribe((users: User[]) => {
                        this.spinner.hide();
                        modal.componentInstance.setIsSaved({ isSaved: true });
                        this.config = { ...this.config, value: users };
                    })
                }
            });
        }
    }

    private deleteUser(event): void {
        this.spinner.show();
        concat(
            this.iamService.deleteUser(event.id).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.iamService.getUsers()
        ).subscribe((users: User[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: users };
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

    private initUserColomns(result): void {
        this.config = {
            id: "user",
            value: result,
            sortableBy: "username",
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
                    header: "Nom d'utilisateur",
                    field: "username",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nom",
                    field: "firstName",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Prénom",
                    field: "lastName",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Email",
                    field: "email",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Role",
                    field: "roles",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Date de création",
                    field: "createdTimestamp",
                    pipe: {
                        function: this.datePipe
                    },
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Profil",
                    field: "",
                    icon: {
                        class: "infos",
                        tooltip: "Détail"
                    }
                }
            ]
        }
    }
}