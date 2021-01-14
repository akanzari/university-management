import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { Classs, CreateClassRequest, UpdateClassRequest } from 'src/app/core/models';
import { ClassService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { ClassModalComponent } from './class-modal/class-modal.component';

@Component({
    templateUrl: './classes.component.html',
    styleUrls: ['./classes.component.scss'],
    providers: [DatePipe]
})
export class ClassesComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private classService: ClassService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.classService.getClasses().subscribe(classes => {
            this.initClassesColomns(classes);
            this.spinner.hide();
        });
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(ClassModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.classService.addClass(dataValue.value as CreateClassRequest).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.classService.getClasses()
                ).subscribe((classes: Classs[]) => {
                    this.spinner.hide();
                    this.config = { ...this.config, value: classes };
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteClass((event.value as Classs)) : null);
        } else {
            const modal: NgbModalRef = this.initPopUp(ClassModalComponent);
            modal.componentInstance.editClass = event.value as Classs;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                    concat(
                        this.classService.updateClass(dataValue.value as UpdateClassRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.classService.getClasses()
                    ).subscribe((classes: Classs[]) => {
                        this.spinner.hide();
                        this.config = { ...this.config, value: classes };
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
            this.classService.deleteClass(event.classId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.classService.getClasses()
        ).subscribe((classes: Classs[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: classes };
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

    private initClassesColomns(result): void {
        this.config = {
            id: "class",
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
                    header: "Classe",
                    field: "classId",
                    filterable: true,
                    sortable: true,
                    width: "10"
                },
                {
                    header: "Description",
                    field: "label",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "E-mail",
                    field: "email",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Spécialié",
                    field: "speciality",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Catégorie",
                    field: "category",
                    filterable: true,
                    sortable: true,
                    width: "10"
                }
            ]
        }
    }
}