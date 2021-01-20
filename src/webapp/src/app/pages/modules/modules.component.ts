import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { concat, EMPTY, timer } from "rxjs";
import { switchMapTo } from "rxjs/operators";
import { Module } from 'src/app/core/models';
import { ModuleService } from 'src/app/core/services';
import { ActionEnum, ConfigColumn } from 'src/app/shared/components/cm-table-container/models/config-column.model';
import { DataValue } from 'src/app/shared/components/cm-table-container/models/data-value.model';
import { RemovePopupComponent } from 'src/app/shared/components/comfirmation-popup/remove/remove-popup.component';
import { AssignClassesModalComponent } from './assign-classes/assign-classes.component';
import { ModuleModalComponent } from './module-modal/module-modal.component';

@Component({
    selector: 'modules',
    templateUrl: './modules.component.html',
    styleUrls: ['./modules.component.scss']
})
export class ModulesComponent implements OnInit {

    public config: ConfigColumn;

    public moduleLength: number

    public form: FormGroup;

    constructor(private modalService: NgbModal,
        private spinner: NgxSpinnerService,
        private fb: FormBuilder,
        private moduleService: ModuleService) {
    }

    ngOnInit() {
        this.spinner.show();
        this.initForm();
        this.moduleService.getModules().subscribe(modules => {
            this.initModulesColomns(modules);
            this.moduleLength = modules.length;
            this.spinner.hide();
        });
    }

    public openModal() {
        const modal: NgbModalRef = this.modalService.open(ModuleModalComponent,
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
                    this.moduleService.addModule(dataValue.value).pipe(switchMapTo(EMPTY)),
                    timer(1000).pipe(switchMapTo(EMPTY)),
                    this.moduleService.getModules()
                ).subscribe((modules: Module[]) => {
                    this.spinner.hide();
                    this.moduleLength = modules.length;
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
            modal.componentInstance.sendData.subscribe((data: boolean) => data ? this.deleteModule((event.value as Module)) : null);
        } else if (event.action === ActionEnum.UPDATE) {
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
        } else {
            this.spinner.show();
            this.moduleService.getAssignClassesByModule(event.value.moduleId).subscribe(items => {
                const modal: NgbModalRef = this.modalService.open(AssignClassesModalComponent,
                    {
                        size: 'lg',
                        windowClass: 'modal-adaptive',
                        ariaLabelledBy: 'modal-basic-title',
                        keyboard: false,
                        backdrop: 'static',
                        centered: true
                    });
                    modal.componentInstance.assignClasses = items;
                    this.spinner.hide();
            })
        }
    }

    private deleteModule(event): void {
        this.spinner.show();
        concat(
            this.moduleService.deleteModule(event.moduleId).pipe(switchMapTo(EMPTY)),
            timer(1000).pipe(switchMapTo(EMPTY)),
            this.moduleService.getModules()
        ).subscribe((modules: Module[]) => {
            this.spinner.hide();
            this.moduleLength = modules.length;
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

    public onSearch() {
        if (this.form.valid) {
        }
    }

    public reset() {
        this.form.reset();
        this.spinner.show();
        this.moduleService.getModules().subscribe(modules => {
            this.config = { ...this.config, value: modules };
            this.moduleLength = modules.length;
            this.spinner.hide();
        });
    }

    private initForm(): void {
        this.form = this.fb.group({
            moduleId: [null],
            typeExam: [null],
            semester: [null],
            period: [null]
        })
    }

    private initModulesColomns(result): void {
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
                    name: ActionEnum.DELETE,
                    icon: {
                        class: "icon-trash size-16",
                        tooltip: "Supprimer"
                    }
                }
            ],
            columns: [

                {
                    header: "Module",
                    field: "moduleId",
                    filterable: true,
                    sortable: true
                }, 
                {
                    header: "Designation",
                    field: "designation",
                    filterable: true,
                    sortable: true
                },
                {
                    header: "Information complémentaire",
                    field: "nothing",
                    link: {
                        text: "Information complimentaire"
                    },
                    filterable: true,
                    sortable: true
                }
            ]
        }
    }
}