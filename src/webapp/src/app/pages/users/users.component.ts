import { Component, OnInit } from '@angular/core';
import { IAMService } from 'src/app/core/services';
import { Observable } from "rxjs";
import { DatePipe } from "@angular/common";
import { ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { TSMap } from 'typescript-map';
import { Teacher } from 'src/app/core/models/teacher.model';
import { UserResponse } from 'src/app/core/models';

@Component({
    selector: 'users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss'],
    providers: [DatePipe]
})
export class UsersComponent implements OnInit {

    public config: ConfigColumn;
    
    public teacher$: Observable<Teacher>;

    public users: UserResponse[];

    constructor(private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.iamService.getUsers().subscribe(users => {
            this.users = users;
            this.initUserColomns(users);
            this.spinner.hide();
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