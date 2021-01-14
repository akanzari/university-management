import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IAMService, StudentService } from 'src/app/core/services';
import { concat, EMPTY, Observable, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { DatePipe } from "@angular/common";
import { UserModalComponent } from './user-modal/user-modal.component';
import { CreateStudentRequest, CreateTeacherRequest, CreateUserRequest, UpdateUserRequest, User } from 'src/app/core/models';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { TeacherService } from 'src/app/core/services/teacher-service';
import { TSMap } from 'typescript-map';
import { Teacher } from 'src/app/core/models/teacher.model';

@Component({
    selector: 'users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss'],
    providers: [DatePipe]
})
export class UsersComponent implements OnInit {

    @ViewChild('content')
    private disableUser: ElementRef;

    @ViewChild('content1')
    private enableUser: ElementRef;

    public config: ConfigColumn;

    private userId: string;
    
    public teacher$: Observable<Teacher>;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private iamService: IAMService,
        private studentService: StudentService,
        private teacherService: TeacherService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.iamService.getUsers().subscribe(users => {
            this.initUserColomns(users);
            this.spinner.hide();
        });
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(UserModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                if (dataValue.type === "user") {
                    this.iamService.addUser(dataValue.value as CreateUserRequest).subscribe(id => {
                        modal.componentInstance.setUserId(id);
                        modal.componentInstance.setIsUserSaved({ isSaved: true });
                        this.iamService.getUsers().subscribe(users => {
                            this.spinner.hide();
                            this.config = { ...this.config, value: users };
                        })
                    }, error => {
                        this.spinner.hide();
                        if (error.error.code === 701) {
                            modal.componentInstance.setIsUserSaved({ isSaved: false, code: error.error.code });
                        }
                    })
                } else if (dataValue.type === "student") {
                    concat(
                        this.studentService.addStudent(dataValue.value as CreateStudentRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.iamService.getUsers()
                    ).subscribe((users: User[]) => {
                        this.spinner.hide();
                        modal.componentInstance.setIsStudentSaved({ isSaved: true });
                        this.config = { ...this.config, value: users };
                    })
                } else if (dataValue.type === "teacher") {
                    concat(
                        this.teacherService.addTeacher(dataValue.value as CreateTeacherRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.iamService.getUsers()
                    ).subscribe((users: User[]) => {
                        this.spinner.hide();
                        modal.componentInstance.setIsTeacherSaved({ isSaved: true });
                        this.config = { ...this.config, value: users };
                    })
                }
            }
        });
    }

    public getArrayForm(event: DataValue) {
        if (event.action === ActionEnum.DELETE) {
            const modal: NgbModalRef = this.initPopUp(RemovePopupComponent);
            modal.componentInstance.config = { title: "Confirmation de suppression", message: "Est-ce que vous confirmez la suppression définitive ?" };
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteUser((event.value as User)) : null);
        } else if (event.action === ActionEnum.UPDATE) {
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
                        modal.componentInstance.setIsUserSaved({ isSaved: true });
                        this.config = { ...this.config, value: users };
                    })
                }
            });
        } else if (event.action === ActionEnum.LOCK) {
            this.modalService.open(this.disableUser, { size: 'lg', centered: true });
            this.userId = event.value.id;
        } else if (event.action === ActionEnum.UNLOCK) {
            this.modalService.open(this.enableUser, { size: 'lg', centered: true });
            this.userId = event.value.id;
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

    public disabledUser() {
        this.spinner.show();
        concat(
            this.iamService.disabledUser(this.userId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.iamService.getUsers()
        ).subscribe((users: User[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: users };
        })
    }

    public enabledUser() {
        this.spinner.show();
        concat(
            this.iamService.enabledUser(this.userId).pipe(switchMapTo(EMPTY)),
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
        let statusMap: TSMap<string, string> = new TSMap<string, string>();
        statusMap.set("Activer", "badge badge-valid");
        statusMap.set("Désactiver", "badge badge-error");
        this.config = {
            id: "user",
            value: result,
            sortableBy: "username",
            pagination: {
                paginate: true,
                rowsPerPage: 20,
                rowsPerPageOptions: [20, 25, 30, 35]
            },
            /*conditionActions: {
                enable: { status: "Activer", name: ActionEnum.LOCK, icon: { class: "icons icon-lock size-16", tooltip: "Désactiver" } },
                disable: { status: "Désactiver", name: ActionEnum.UNLOCK, icon: { class: "icons icon-unlock size-16", tooltip: "Activer" } }
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
            ],*/
            columns: [
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
                    header: "Statut",
                    field: "status",
                    conditionClass: statusMap,
                    width: "10",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }

    getIconStyle(obj): string {
        if (!obj.banksData) {
            return "warning";
        } else {
            return "check color-valid";
        }
    }
}