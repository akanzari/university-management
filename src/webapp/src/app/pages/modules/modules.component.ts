import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { Module } from 'src/app/core/models';
import { CreateModuleRequest } from 'src/app/core/models/request/create-module-request.model';
import { ModuleService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { ModuleModalComponent } from './module-modal/module-modal.component';

@Component({
    selector: 'modules',
    templateUrl: './modules.component.html',
    styleUrls: ['./modules.component.scss'],
    providers: [DatePipe]
})
export class ModulesComponent implements OnInit {

    public config: ConfigColumn;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private datePipe: DatePipe,
        private moduleService: ModuleService) {
    }

    ngOnInit() {
        this.moduleService.getModules().subscribe(modules => this.initModulesColomns(modules))
    }

    public openModal() {
        const modal: NgbModalRef = this.initPopUp(ModuleModalComponent);
        modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
            this.spinner.show();
            if (dataValue.action === ActionEnum.CREATE) {
                concat(
                    this.moduleService.addModule(dataValue.value as CreateModuleRequest).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.moduleService.getModules()
                ).subscribe((modules: Module[]) => {
                    this.spinner.hide();
                    this.config = { ...this.config, value: modules };
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteClass((event.value as Module)) : null);
        } else {
            const modal: NgbModalRef = this.initPopUp(ModuleModalComponent);
            modal.componentInstance.editModule = event.value as Module;
            modal.componentInstance.triggerSave.subscribe((dataValue: DataValue) => {
                this.spinner.show();
                if (dataValue.action === ActionEnum.UPDATE) {
                   /* concat(
                        this.moduleService.updateModule(dataValue.value as UpdateClassRequest).pipe(switchMapTo(EMPTY)),
                        timer(1000).pipe(switchMapTo(EMPTY)),
                        this.moduleService.getModules()
                    ).subscribe((modules: Module[]) => {
                        this.spinner.hide();
                        this.config = { ...this.config, value: modules };
                        modal.componentInstance.setIsSaved({ isSaved: true });
                    }, error => {
                        this.spinner.hide();
                        if (error.error.code === 701) {
                            modal.componentInstance.setIsSaved({ isSaved: false, code: error.error.code });
                        }
                    })*/
                }
            });
        }
    }

    private deleteClass(event): void {
        this.spinner.show();
        concat(
            this.moduleService.deleteModule(event.classId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.moduleService.getModules()
        ).subscribe((modules: Module[]) => {
            this.spinner.hide();
            this.modalService.dismissAll();
            this.config = { ...this.config, value: modules };
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

    private initModulesColomns(result): void {
        this.config = {
            id: "class",
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
                    header: "Designation",
                    field: "designation",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Nbr.Heures",
                    field: "nbrHours",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Type Epreuve",
                    field: "examType",
                    filterable: true,
                    sortable: true,
                },
                {
                    header: "Semestre",
                    field: "semester",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Période",
                    field: "periods",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Classe",
                    field: "classs.label",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Enseignant",
                    field: "teacher.fullName",
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