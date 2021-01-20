import { Component, Input, EventEmitter, Output, OnChanges, SimpleChanges, Renderer2, ElementRef, QueryList, ViewChildren } from "@angular/core";
import { FormGroup, FormBuilder, AbstractControl, ValidatorFn, FormControl } from "@angular/forms";
import { DatePipe } from "@angular/common";
import { DisplayPipe } from "./pipes/display.pipe";
import * as moment from 'moment';
import { TSMap } from 'typescript-map';
import { ConfigColumn, Column, DefaultValue, ActionEnum, Action, Pipe } from './models/config-column.model';
import { DataValue } from "./models/data-value.model";
import { PagerService } from "./services/pager.service";
import { Dates } from './date-utils';

@Component({
    selector: 'cm-table-container',
    templateUrl: './cm-table-container.component.html',
    styleUrls: ['./cm-table-container.component.scss'],
    providers: [DatePipe, DisplayPipe]
})
export class CmTbaleContainerComponent implements OnChanges {

    public formGroup: FormGroup;

    @ViewChildren('sort')
    public spans: QueryList<ElementRef>;

    @Output()
    public sendData: EventEmitter<any> = new EventEmitter();

    @Output()
    public hideAddButtonChanged: EventEmitter<boolean> = new EventEmitter();

    @Output()
    public onChangeSelectFromTable: EventEmitter<any> = new EventEmitter();

    @Output()
    public onChangeCalendarFromTable: EventEmitter<any> = new EventEmitter();

    @Output()
    public selectedRowsChange: EventEmitter<any> = new EventEmitter();

    @Output()
    public onRowSelect: EventEmitter<any[]> = new EventEmitter();

    @Input()
    public config: ConfigColumn;

    public displayLine: boolean = false;
    public reverse: boolean = false;
    public principalChecked: boolean;
    public editableWithSelectMode: boolean;

    public getErrorForConditionValidation: string;
    public fieldForFilter: string;
    public valueForFilter: string;
    public fieldForSort: string;
    public dependLabel: string;
    public action: ActionEnum;

    public currentConfig: ConfigColumn;
    public columns: Column[];

    public allItems: any[];
    public pagedItems: any[];
    public tableSelectedRows: any[];
    public pager: any = {};

    public labelMap: TSMap<string, string> = new TSMap<string, string>();

    public columnsEditable: TSMap<string, boolean> = new TSMap<string, boolean>();

    public validationError: TSMap<string, string> = new TSMap<string, string>();

    public editableLigneMap: TSMap<string, boolean> = new TSMap<string, boolean>();

    public itemsChecked: TSMap<string, boolean> = new TSMap<string, any>();

    @Input()
    get selectedRows() {
        return this.tableSelectedRows;
    }

    set selectedRows(rows) {
        this.tableSelectedRows = rows;
        if (this.allItems && this.tableSelectedRows.length === 0) {
            this.principalChecked = false;
            this.allItems.forEach((item, index) => this.itemsChecked.set(this.currentConfig.id + "-" + index, null));
        }
        this.selectedRowsChange.emit(rows);
    }

    constructor(private renderer2: Renderer2,
        private fb: FormBuilder,
        private pagerService: PagerService,
        private datePipe: DatePipe) {
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes["config"] && changes["config"].currentValue) {
            this.formGroup = this.fb.group({});
            this.currentConfig = changes["config"].currentValue;
            this.columns = this.currentConfig.columns;
            this.allItems = this.currentConfig.value;

            if (this.allItems) {
                this.allItems.forEach((item, index) => {
                    this.editableLigneMap.set(this.currentConfig.id + "-" + index, false);
                    this.itemsChecked.set(this.currentConfig.id + "-" + index, null)
                });
            }

            if (this.currentConfig.hasOwnProperty("sortableBy")) {
                this.fieldForSort = this.currentConfig.sortableBy;
            } else {
                this.fieldForSort = this.currentConfig.columns[0].field;
            }

            if (this.currentConfig.hasOwnProperty("pagination")) {
                if (!this.currentConfig.pagination.hasOwnProperty("rowsPerPage")) {
                    this.currentConfig.pagination.rowsPerPage = 5;
                }
                if (!this.currentConfig.pagination.hasOwnProperty("rowsPerPageOptions")) {
                    this.currentConfig.pagination.rowsPerPageOptions = [5, 10, 15, 20, 25, 30];
                }
                if (this.pagedItems) {
                    this.setPage({ page: 1, totalItems: this.pager.pageSize });
                } else {
                    this.setPage({ page: 1 });
                }
            }

            if (this.currentConfig.initForm) {
                this.initForm();
            }

            if (this.currentConfig.hasOwnProperty("addable")) {
                this.updateUniqueOption();
                this.hideOrDisplayAddButton();
            }

            if (this.getUpdateAction() && !this.getUpdateAction().icon) {
                this.editableWithSelectMode = true;
            } else {
                this.editableWithSelectMode = false;
            }
        }
    }

    public arrayOfItems() {
        if (this.currentConfig.pagination) {
            return this.pagedItems;
        } else {
            return this.allItems;
        }
    }

    public notEditableCondition(b: string): boolean {
        if (this.currentConfig.notEditableCondition && b && this.currentConfig.notEditableCondition.value.includes(b)) {
            return false;
        } else {
            return true;
        }
    }

    public addLine(): void {
        if (this.currentConfig && this.currentConfig.hasOwnProperty("addable")) {
            this.action = ActionEnum.CREATE;
            this.displayLine = true;
            this.hideOrDisplayAddButton();
            this.initForm();
        }
    }

    public initForm(): void {
        if (this.currentConfig && this.currentConfig.globalValidaros) {
            let datesValidaros = this.currentConfig.globalValidaros.find(element => element.hasOwnProperty("dateLessThan"));
            if (datesValidaros) {
                this.formGroup.setValidators(this.dateLessThan(datesValidaros.dateLessThan[0], datesValidaros.dateLessThan[1], { 'loaddate': true }));
            }
        }
        this.columns.forEach(element => {
            if (element.validations && !element.validations.hasOwnProperty("condition") && element.defaultValue && Object.prototype.toString.call(element.defaultValue) != "[object Object]") {
                if (this.formGroup.get(element.field)) {
                    this.formGroup.get(element.field).setValue(element.defaultValue);
                    this.formGroup.get(element.field).setValidators(element.validations.validators);
                } else {
                    this.formGroup.addControl(element.field, this.fb.control(element.defaultValue, element.validations.validators));
                }
            } else if (element.validations && !element.validations.hasOwnProperty("condition") && !element.defaultValue) {
                if (this.formGroup.get(element.field)) {
                    this.formGroup.get(element.field).setValue(null);
                    this.formGroup.get(element.field).setValidators(element.validations.validators);
                } else {
                    this.formGroup.addControl(element.field, this.fb.control(null, element.validations.validators));
                }
            } else if (!element.validations && element.defaultValue && Object.prototype.toString.call(element.defaultValue) != "[object Object]") {
                if (this.formGroup.get(element.field)) {
                    this.formGroup.get(element.field).setValue(element.defaultValue);
                } else {
                    this.formGroup.addControl(element.field, this.fb.control(null, element.validations.validators));
                }
            } else {
                if (this.formGroup.get(element.field)) {
                    this.formGroup.get(element.field).setValue(null);
                } else {
                    this.formGroup.addControl(element.field, this.fb.control(null));
                }
            }
        })
    }

    public async onChangeItem(event, field: string) {
        this.setCalendarCondition(field);

        let columnsWithEvent = this.columns.filter(element => element.field === field && element.event);
        if (columnsWithEvent.length > 0) {
            this.onChangeSelectFromTable.emit({ event: event, field: field, form: this.formGroup });
        }

        let dependFromColumns = this.columns.filter(element => element.defaultValue && element.defaultValue.hasOwnProperty("dependFromColumn"));
        if (dependFromColumns.length > 0) {
            dependFromColumns.forEach(element => {
                if (field === (element.defaultValue as DefaultValue).dependFromColumn) {
                    if ((element.defaultValue as DefaultValue).value && (element.defaultValue as DefaultValue).value.includes(".")) {
                        if ((element.defaultValue as DefaultValue).format) {
                            if (element.type !== "label") {
                                this.formGroup.get(element.field).setValue((element.defaultValue as DefaultValue).format(event[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]));
                            } else {
                                this.labelMap.set(element.field, (element.defaultValue as DefaultValue).format(event[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]));
                            }
                        } else {
                            if (element.type !== "label") {
                                this.formGroup.get(element.field).setValue(event[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]);
                            } else {
                                this.labelMap.set(element.field, event[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]);
                            }
                        }
                    } else {
                        if ((element.defaultValue as DefaultValue).format) {
                            if (element.type !== "label") {
                                this.formGroup.get(element.field).setValue((element.defaultValue as DefaultValue).format(event[element.field]));
                            } else {
                                this.labelMap.set(element.field, (element.defaultValue as DefaultValue).format(event[element.field]));
                            }
                        } else {
                            if (element.type !== "label") {
                                this.formGroup.get(element.field).setValue(event[element.field]);
                            } else {
                                this.labelMap.set(element.field, event[element.field]);
                            }
                        }
                    }
                }
            })
        }

        let optionsConditionColumns = this.columns.filter(element => element.hasOwnProperty("optionsWithCondition"));
        if (optionsConditionColumns.length > 0) {
            optionsConditionColumns.forEach(element => {
                if (element.optionsWithCondition[0].dependFromColumn.includes(".") && element.optionsWithCondition[0].dependFromColumn.split('.')[0] === field) {
                    let result = element.optionsWithCondition.find(item => event[element.optionsWithCondition[0].dependFromColumn.split('.')[1]] == item.value);
                    element.monoselectConfig.options = result.values;
                    this.formGroup.get(element.field).setValue(null);
                } else if (!element.optionsWithCondition[0].dependFromColumn.includes(".") && element.optionsWithCondition[0].dependFromColumn === field) {
                    let result = element.optionsWithCondition.find(item => event[element.optionsWithCondition[0].dependFromColumn] == item.value);
                    this.formGroup.get(element.optionsWithCondition[0].dependFromColumn).setValue(null);
                    element.monoselectConfig.options = result.values;
                    this.formGroup.get(element.field).setValue(null);
                }
            });
        }

        if (event) {
            let validationsConditionColumns = this.columns.filter(element => element.hasOwnProperty("validationsWithCondition"));
            if (validationsConditionColumns.length > 0) {
                validationsConditionColumns.forEach(element => {
                    if (element.validationsWithCondition[0].dependFromColumn.includes(".") && element.validationsWithCondition[0].dependFromColumn.split('.')[0] === field) {
                        let result = element.validationsWithCondition.find(item => event[element.validationsWithCondition[0].dependFromColumn.split('.')[1]] == item.value);
                        this.formGroup.get(element.field).setValidators(result.validators);
                        this.getErrorForConditionValidation = result.error;
                    } else if (!element.validationsWithCondition[0].dependFromColumn.includes(".") && element.validationsWithCondition[0].dependFromColumn === field) {
                        let result = element.validationsWithCondition.find(item => event[element.validationsWithCondition[0].dependFromColumn] == item.value);
                        this.formGroup.get(element.field).setValidators(result.validators);
                        this.getErrorForConditionValidation = result.error;
                    }
                });
            }
        }

        let dependFromApis = this.columns.filter(element => element.defaultValue && field === (element.defaultValue as DefaultValue).pathVariable && (element.defaultValue as DefaultValue).hasOwnProperty("dependFromAPI"));
        if (dependFromApis.length > 0) {
            for (const element of dependFromApis) {
                if (this.formGroup.get((element.defaultValue as DefaultValue).pathVariable).value) {
                    let item = await (element.defaultValue as DefaultValue).dependFromAPI(this.formGroup.get((element.defaultValue as DefaultValue).pathVariable).value).toPromise();
                    if ((element.defaultValue as DefaultValue).value && (element.defaultValue as DefaultValue).value.includes(".")) {
                        if (element.type !== "label") {
                            this.formGroup.get(element.field).setValue(item[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]);
                            this.formGroup.get(element.field).updateValueAndValidity();
                        } else {
                            this.formGroup.get(element.field).setValue(item[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]);
                            this.formGroup.get(element.field).updateValueAndValidity();
                            this.labelMap.set(element.field, item[(element.defaultValue as DefaultValue).value.split('.')[0]][(element.defaultValue as DefaultValue).value.split('.')[1]]);
                        }
                    } else {
                        if ((element.defaultValue as DefaultValue).format) {
                            if (element.type !== "label") {
                                this.formGroup.get(element.field).setValue((element.defaultValue as DefaultValue).format(item[element.field]));
                                this.formGroup.get(element.field).updateValueAndValidity();
                            } else {
                                this.formGroup.get(element.field).setValue(item[element.field]);
                                this.formGroup.get(element.field).updateValueAndValidity();
                                this.labelMap.set(element.field, (element.defaultValue as DefaultValue).format(item[element.field]));
                            }
                        }
                    }
                } else {
                    this.labelMap.clear();
                }
            }
        }

    }

    private updateUniqueOption(): void {
        let monoselectsUnique = this.columns.filter(element => element.type === "monoselect" && element.unique);
        if (monoselectsUnique.length > 0) {
            monoselectsUnique.forEach(element => {
                if (element.monoselectConfig.options && element.unique) {
                    if (this.isString(element.unique)) {
                        if ((element.unique as string).includes(".")) {
                            element.monoselectConfig.options = element.monoselectConfig.options.filter(item => !this.currentConfig.value.map(str => str[element.field][(element.unique as string).split('.')[0]][(element.unique as string).split('.')[1]]).includes(item[(element.unique as string).split('.')[0]][(element.unique as string).split('.')[1]]));
                        } else {
                            element.monoselectConfig.options = element.monoselectConfig.options.filter(item => !this.currentConfig.value.map(str => str[element.field][(element.unique as string)]).includes(item[(element.unique as string)]));
                        }
                    } else {
                        element.monoselectConfig.options = element.monoselectConfig.options.filter(item => !this.currentConfig.value.map(str => str[element.field]).includes(item[element.field]));
                    }
                }
            })
        }
    }

    private hideOrDisplayAddButton(): void {
        if (this.currentConfig && this.currentConfig.hasOwnProperty("addable")) {
            let monoselectsUniqueAndEmptyOptions = this.columns.filter(element => element.type === "monoselect" && element.monoselectConfig && (!element.monoselectConfig.options || element.monoselectConfig.options.length == 0));
            if (monoselectsUniqueAndEmptyOptions.length > 0 || this.displayLine == true) {
                this.hideAddButtonChanged.emit(true);
            } else {
                this.hideAddButtonChanged.emit(false);
            }
        }
    }

    private validationsWithCondition(data: any, key: string): void {
        let columnValidationsWithCondition = this.columns.filter(item => item.validationsWithCondition);
        if (columnValidationsWithCondition && columnValidationsWithCondition.length > 0) {
            columnValidationsWithCondition.forEach(element => {
                let result = element.validationsWithCondition.find(item => data[key] == item.value);
                if (result) {
                    this.formGroup.get(element.field).setValue(data[element.field]);
                    this.formGroup.get(element.field).setValidators(result.validators);
                    this.getErrorForConditionValidation = result.error;
                    this.formGroup.get(element.field).updateValueAndValidity();
                }
            })
        }
    }

    public getEditableLigne(id: string): boolean {
        return this.editableLigneMap.get(id);
    }

    public selectItem(index: number, data: any, id: string) {
        this.editableLigneMap = this.editableLigneMap.filter((value, key) => key != id);
        this.editableLigneMap.forEach((item, key) => this.editableLigneMap.set(key, false));
        this.editableLigneMap.set(id, true);
        this.action = ActionEnum.UPDATE;
        this.initForm();
        this.columns.forEach(element => {
            if (element.editable == false) {
                this.columnsEditable.set(element.field, true);
            } else {
                this.columnsEditable.set(element.field, false);
            }
        })
        Object.keys(data).forEach(key => {
            if (data[key]) {

                let optionsConditionColumns = this.columns.filter(element => element.hasOwnProperty("optionsWithCondition"));
                if (optionsConditionColumns.length > 0) {
                    optionsConditionColumns.forEach(element => {
                        element.optionsWithCondition.forEach(item => {
                            if (item.value === data[key]) {
                                element.monoselectConfig.options = item.values;
                            }
                        });
                    });
                }

                if (this.formGroup.get(key)) {
                    if (moment(data[key], "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid()) {
                        this.formGroup.get(key).setValue(this.datePipe.transform(new Date(data[key]), "yyyy-MM-dd"));
                        this.labelMap.set(key, this.datePipe.transform(new Date(data[key]), "yyyy-MM-dd"));
                    } else {
                        this.validationsWithCondition(data, key);
                        let column = this.columns.find(element => element.type === "label" && element.field === key);
                        if (column) {
                            this.formGroup.get(key).setValue(data[key]);
                            if (column.defaultValue && (column.defaultValue as DefaultValue).value) {
                                if (Object.prototype.toString.call(data[key]) == "[object Object]" && column && (column.defaultValue as DefaultValue).value.includes(".")) {
                                    this.labelMap.set(key, data[key][(column.defaultValue as DefaultValue).value.split('.')[1]]);
                                } else {
                                    this.labelMap.set(key, data[key]);
                                }
                            } else {
                                this.labelMap.set(key, data[key]);
                            }
                        }
                        this.formGroup.get(key).setValue(data[key]);
                    }
                }
            } else {
                if (this.formGroup.get(key)) {
                    this.formGroup.get(key).setValue(null);
                }
                this.labelMap.set(key, null);
            }
        })
        this.hideAddButtonChanged.emit(true);
        let array = Object.keys(data).filter(item => !Object.keys(this.formGroup.value).includes(item));
        if (array.length > 0) {
            array.forEach(element => this.formGroup.addControl(element, new FormControl(data[element])));
        }
    }

    public sendItem(action: ActionEnum, data: any, index: number) {
        if (action == null) {
            action = ActionEnum.LINK;
        }
        let dataValue: DataValue = { action: action, value: data };
        this.sendData.emit(dataValue);
        if (action === ActionEnum.UPDATE && this.columns.every(column => column.hasOwnProperty("type"))) {
            this.selectItem(index, data, this.currentConfig.id + '-' + this.getIndex(index));
        }
        if (action === ActionEnum.DELETE && this.sendData.observers.length > 0) {
            this.updateUniqueOption();
            this.hideOrDisplayAddButton();
            this.resetLine(ActionEnum.DELETE);
        }
    }

    public confirm(): void {
        if (!this.isLineHasElementNotValid()) {
            this.sendData.emit({ action: this.action, value: this.formGroup.value });
            if (this.sendData.observers.length > 0) {
                this.resetLine();
            }
        }
    }

    public enterKeyup(): void {
        if (!this.isLineHasElementNotValid()) {
            this.sendData.emit({ action: this.action, value: this.formGroup.value });
            if (this.sendData.observers.length > 0) {
                this.resetLine();
            }
        }
    }

    public resetLine(action?: ActionEnum): void {
        this.action = null;
        this.formGroup.reset();
        this.formGroup.clearValidators();
        this.formGroup.updateValueAndValidity();
        Object.keys(this.formGroup.controls).forEach(item => {
            this.formGroup.get(item).clearValidators();
            this.formGroup.get(item).updateValueAndValidity();
        })
        this.displayLine = false;
        this.editableLigneMap.forEach((item, key) => this.editableLigneMap.set(key, false));
        if (action != ActionEnum.DELETE) {
            this.hideAddButtonChanged.emit(false);
        }
        this.labelMap.clear();
        this.columns.forEach(element => {
            this.columnsEditable.set(element.field, false);
        })
        let columnsHasDisabled = this.columns.filter(column => column.hasOwnProperty("disabled"));
        if (columnsHasDisabled) {
            columnsHasDisabled.forEach(column => column.disabled = true);
        }
    }

    public changeCalendar(field: string): void {
        let columnsWithEvent = this.columns.filter(element => element.field === field && element.event);
        if (columnsWithEvent.length > 0) {
            this.onChangeCalendarFromTable.emit({ field: field, form: this.formGroup });
        }

        let column = this.columns.find(element => element.field === field && element.validations && element.validations.errors.length > 0);
        if (column && this.formGroup.get(field).errors) {
            Object.keys(this.formGroup.get(field).errors).forEach(item => this.validationError.set(field, column.validations.errors.get(item)))
        }
        if (this.validationError.has(field) && !this.formGroup.get(field).errors) {
            this.validationError.delete(field);
        }
        this.setCalendarCondition(field);
    }

    private setCalendarCondition(field: string): void {
        let calendarCondition = this.columns.filter(element => element.validations && element.validations.hasOwnProperty("condition") && element.validations.condition.field === field);
        if (calendarCondition.length > 0) {
            calendarCondition.forEach(element => {
                if (element.validations.condition.is === "isNotEmpty") {
                    if (this.formGroup.get(field).value) {
                        this.formGroup.get(element.field).setValidators(element.validations.validators);
                    } else {
                        this.formGroup.get(element.field).setValidators(null);
                    }
                    this.formGroup.get(element.field).updateValueAndValidity();
                }
            })
        }
    }

    public getErrorFormLoadDate(): string {
        if (this.currentConfig && this.currentConfig.globalValidaros) {
            let datesValidaros = this.currentConfig.globalValidaros.find(element => element.hasOwnProperty("dateLessThan"));
            if (datesValidaros) {
                return datesValidaros.error;
            }
        }
    }

    public displayValue(value: string, column: Column): string {
        if (column && value) {
            if (column.field.includes(".")) {
                let array: string[] = column.field.split('.');
                let str: string = value[array[0]];
                if (str) {
                    for (let i = 1; i < array.length; i++) {
                        str = str[array[i]];
                    }
                }
                if (column.defaultValue && (column.defaultValue as DefaultValue).value) {
                    return this.getDataPipe(column.pipe, this.getDefaultValue(str, column));
                } else if (moment(str, "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid() || moment(str, "YYYY-MM-DD", true).isValid()) {
                    return this.getDataPipe(column.pipe, this.datePipe.transform(new Date(str), "dd/MM/yyyy"));
                } else {
                    return this.getDataPipe(column.pipe, str);
                }
            } else {
                if (column.multiselectConfig) {
                    if (column.multiselectConfig.type == 'objects') {
                        if (column.multiselectConfig.bindValue) {
                            return this.getDataPipe(column.pipe, value[column.field]);
                        } else {
                            return this.getDataPipe(column.pipe, value[column.field].map(element => element[column.multiselectConfig.bindLabel]).join(", "));
                        }
                    } else {
                        return this.getDataPipe(column.pipe, value[column.field]);
                    }
                } else if (column.monoselectConfig) {
                    return this.getDataPipe(column.pipe, value[column.field]);
                } else if (column.defaultValue && (column.defaultValue as DefaultValue).value) {
                    return this.getDataPipe(column.pipe, this.getDefaultValue(value[column.field], column));
                } else if (moment(value[column.field], "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid() || moment(value[column.field], "YYYY-MM-DD", true).isValid()) {
                    return this.getDataPipe(column.pipe, this.datePipe.transform(new Date(value[column.field]), "dd/MM/yyyy"));
                } else {
                    return this.getDataPipe(column.pipe, value[column.field]);
                }
            }
        }
    }

    private getDefaultValue(value: string, column: Column): string {
        if (Object.prototype.toString.call(value[column.field]) == "[object Object]" && (column.defaultValue as DefaultValue).value.includes(".")) {
            if (moment(value[column.field][(column.defaultValue as DefaultValue).value.split('.')[1]], "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid() ||
                moment(value[column.field][(column.defaultValue as DefaultValue).value.split('.')[1]], "YYYY-MM-DD", true).isValid()) {
                return this.getDataPipe(column.pipe, this.datePipe.transform(new Date(value[column.field][(column.defaultValue as DefaultValue).value.split('.')[1]]), "dd/MM/yyyy"));
            } else {
                return this.getDataPipe(column.pipe, value[column.field][(column.defaultValue as DefaultValue).value.split('.')[1]]);
            }
        } else {
            if (moment(value[column.field], "YYYY-MM-DDTHH:mm:ss.SSSZ", true).isValid() || moment(value[column.field], "YYYY-MM-DD", true).isValid()) {
                return this.getDataPipe(column.pipe, this.datePipe.transform(new Date(value[column.field]), "dd/MM/yyyy"));
            } else {
                return this.getDataPipe(column.pipe, value[column.field]);
            }
        }
    }

    private getDataPipe(pipe: Pipe, value): string {
        if (pipe) {
            let params = pipe.params ? pipe.params : [];
            return pipe.function.transform(value, params);
        } else {
            return value;
        }
    }

    public isElementRequired(field: string): boolean {
        return this.formGroup.get(field) && this.formGroup.get(field).hasError("required");
    }

    public isLineHasElementNotValid(): boolean {
        /*const a = Object.keys(this.formGroup.controls).filter(key => this.formGroup.get(key).invalid).length > 0 || this.isPropertiesNull(this.formGroup.value);
        const b = this.formGroup.invalid || a;
        console.log(this.formGroup.invalid + " --- " + a + " --- " + b);*/
        return Object.keys(this.formGroup.controls).filter(key => this.formGroup.get(key).invalid).length > 0 || this.isPropertiesNull(this.formGroup.value) || this.formGroup.invalid;
    }

    private isPropertiesNull(obj): boolean {
        for (var key in obj) {
            if (obj[key] !== null && obj[key] != "")
                return false;
        }
        return true;
    }

    public isInvalidElement(field: string): boolean {
        return this.formGroup.get(field).invalid && !this.formGroup.get(field).hasError("required");
    }

    private dateLessThan(firstDate: string, secondDate: string, validatorField: { [key: string]: boolean }): ValidatorFn {
        return (c: AbstractControl): { [key: string]: boolean } | null => {
            if (c.get(firstDate) && c.get(secondDate)) {
                let date1 = c.get(firstDate).value;
                let date2 = c.get(secondDate).value;
                if (Dates.toDate(date1).valid && Dates.toDate(date2).valid && Dates.toDate(date1).date && Dates.toDate(date2).date &&
                    Dates.toDate(date1).date.getTime() > Dates.toDate(date2).date.getTime()) {
                    return validatorField;
                }
            }
            return null;
        };
    }

    public keyupFilter(event, field): void {
        this.fieldForFilter = field;
        this.valueForFilter = event.target.value;
    }

    public sortArray(column): void {
        this.reverse = !this.reverse;
        this.fieldForSort = column.field;
        this.spans.forEach((elementRef: ElementRef) => {
            if (elementRef.nativeElement.id === 'sort-' + column.field) {
                if (elementRef.nativeElement.classList.contains('sorting_asc')) {
                    this.renderer2.removeClass(elementRef.nativeElement, "sorting_asc");
                    this.renderer2.addClass(elementRef.nativeElement, "sorting_desc");
                } else if (elementRef.nativeElement.classList.contains('sorting_desc')) {
                    this.renderer2.removeClass(elementRef.nativeElement, "sorting_desc");
                    this.renderer2.addClass(elementRef.nativeElement, "sorting_asc");
                }
            } else {
                this.renderer2.removeClass(elementRef.nativeElement, "sorting_desc");
                this.renderer2.addClass(elementRef.nativeElement, "sorting_asc");
            }
        })
    }

    public getUpdateAction(): Action {
        return this.currentConfig.actions ? this.currentConfig.actions.find(action => action.name === ActionEnum.UPDATE) : null;
    }

    public getDeleteAction(): Action {
        return this.currentConfig.actions ? this.currentConfig.actions.find(action => action.name === ActionEnum.DELETE) : null;
    }

    public geActionsWithIcon(): Action[] {
        this.currentConfig.columns.every(column => column.hasOwnProperty("sortable"));
        return this.currentConfig.actions ? this.currentConfig.actions.filter(action => action.icon) : null;
    }

    public isAllColumnSortable(): boolean {
        return this.currentConfig.columns.every(column => column.hasOwnProperty("sortable") && this.allItems.length > 1);
    }

    public displayAction(action: Action): boolean {
        let result: boolean = false;
        if (action.hasOwnProperty('condition')) {
            if (this.allItems.length > action.condition.min) {
                result = true;
            } else if (this.allItems.length < action.condition.max) {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

    public checkAllLignes(event): void {
        if (event.target.checked) {
            this.principalChecked = true;
            if (this.allItems) {
                this.allItems.forEach((item, index) => this.itemsChecked.set(this.currentConfig.id + "-" + index, item));
            }
        } else {
            this.principalChecked = false;
            if (this.allItems) {
                this.allItems.forEach((item, index) => this.itemsChecked.set(this.currentConfig.id + "-" + index, null));
            }
        }
        this.selectedRows = this.itemsChecked.values().filter(item => item);
        this.onRowSelect.emit(this.itemsChecked.values().filter(item => item));
    }

    public getItemsChecked(event, value, id): void {
        if (event.target.checked) {
            this.itemsChecked.set(id, value);
        } else {
            this.principalChecked = false;
            this.itemsChecked.set(id, null);
        }
        this.selectedRows = this.itemsChecked.values().filter(item => item);
        this.onRowSelect.emit(this.itemsChecked.values().filter(item => item));
    }

    public getItemChecked(event, value, id): void {
        if (event.target.checked) {
            this.itemsChecked.set(id, value);
            this.itemsChecked.forEach((value, key) => {
                if (id != key) {
                    this.itemsChecked.set(key, null);
                }
            })
        } else {
            this.itemsChecked.set(id, null);
        }
        this.selectedRows = this.itemsChecked.values().filter(item => item);
        this.onRowSelect.emit(this.itemsChecked.values().filter(item => item));
    }

    public setPage(data: any) {
        if (data.totalItems) {
            this.pager = this.pagerService.getPager(this.allItems.length, data.page, data.totalItems);
        } else {
            this.pager = this.pagerService.getPager(this.allItems.length, data.page, this.currentConfig.pagination.rowsPerPage);
        }
        this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
    }

    public getIndex(index): number {
        if (this.currentConfig.pagination) {
            return index + this.pager.pageSize * (this.pager.currentPage - 1);
        } else {
            return index;
        }
    }

    private isString(value) {
        return typeof value === 'string' || value instanceof String;
    }

}