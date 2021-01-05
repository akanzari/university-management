import { Component } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { IAMService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { StudentModalComponent } from './student-modal/student-modal.component';

@Component({
    selector: 'students',
    templateUrl: './students.component.html',
    styleUrls: ['./students.component.scss']
})
export class StudentsComponent {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private iamService: IAMService) {
    }

    ngOnInit() {
        this.iamService.getUserByRole('STUDENT').subscribe(users => {
            
        });
    }

    public getArrayForm(event: DataValue) {
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(StudentModalComponent);
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

    private initStudentColomns(result): void {
        this.config = {
            id: "user",
            value: result,
            sortableBy: "cin",
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
                    header: "N°CIN",
                    field: "cin",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Etudiant",
                    field: "fullName",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Email",
                    field: "email",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Classe",
                    field: "classe",
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}