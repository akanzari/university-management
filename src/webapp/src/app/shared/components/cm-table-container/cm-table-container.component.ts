import { Component, Input, EventEmitter, Output, OnChanges, SimpleChanges, Renderer2, ElementRef, QueryList, ViewChildren } from "@angular/core";
import { TSMap } from 'typescript-map';
import { ConfigColumn, Column, Pipe, Action, ActionEnum } from './models/config-column.model';
import { PagerService } from "./services/pager.service";
import { DataValue } from './models/data-value.model';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'cm-table-container',
    templateUrl: './cm-table-container.component.html',
    styleUrls: ['./cm-table-container.component.scss']
})
export class CmTbaleContainerComponent implements OnChanges {

    @ViewChildren('sort')
    public spans: QueryList<ElementRef>;

    @Output()
    public sendData: EventEmitter<any> = new EventEmitter();

    @Input()
    public currentConfig: ConfigColumn;

    public config: ConfigColumn;

    public reverse: boolean = false;
    public principalChecked: boolean;
    public editableWithSelectMode: boolean;

    public fieldForFilter: string;
    public valueForFilter: string;
    public fieldForSort: string;

    public columns: Column[];

    public allItems: any[];
    public pagedItems: any[];
    public tableSelectedRows: any[];
    public pager: any = {};

    public editableLigneMap: TSMap<string, boolean> = new TSMap<string, boolean>();

    constructor(private renderer2: Renderer2,
        private pagerService: PagerService) {
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes["currentConfig"] && changes["currentConfig"].currentValue) {
            this.config = changes["currentConfig"].currentValue;
            this.columns = this.config.columns;
            this.allItems = this.config.value;
            if (this.config.hasOwnProperty("sortableBy")) {
                this.fieldForSort = this.config.sortableBy;
            } else {
                this.fieldForSort = this.config.columns[0].field;
            }
            if (this.config.hasOwnProperty("pagination")) {
                if (!this.config.pagination.hasOwnProperty("rowsPerPage")) {
                    this.config.pagination.rowsPerPage = 5;
                }
                if (!this.config.pagination.hasOwnProperty("rowsPerPageOptions")) {
                    this.config.pagination.rowsPerPageOptions = [5, 10, 15, 20, 25, 30];
                }
                if (this.pagedItems) {
                    this.setPage({ page: 1, totalItems: this.pager.pageSize });
                } else {
                    this.setPage({ page: 1 });
                }
            }
        }
    }

    public arrayOfItems() {
        if (this.config.pagination) {
            return this.pagedItems;
        } else {
            return this.allItems;
        }
    }

    public sendItem(action: ActionEnum, data: any) {
        let dataValue: DataValue = { action: action, value: data };
        this.sendData.emit(dataValue);
    }

    public displayValue(value: string, column: Column): string {
        let result: string;
        if (column && value) {
            if (column.field.includes(".")) {
                let array: string[] = column.field.split('.');
                let str: string = value[array[0]];
                if (str) {
                    for (let i = 1; i < array.length; i++) {
                        str = str[array[i]];
                    }
                    result = this.getDataPipe(column.pipe, str);
                }
            } else {
                result = this.getDataPipe(column.pipe, value[column.field]);
            }
        }
        return result;
    }

    private getDataPipe(pipe: Pipe, value): string {
        if (pipe) {
            let params = pipe.params ? pipe.params : [];
            if (params.length > 0) {
                return pipe.function.transform(value, params);
            } else {
                if (pipe.function instanceof DatePipe) {
                    return pipe.function.transform(value, 'dd/MM/yyyy');
                } else {
                    return pipe.function.transform(value);
                }
            }
        } else {
            return value;
        }
    }

    public geActionsWithIcon(): Action[] {
        this.config.columns.every(column => column.hasOwnProperty("sortable"));
        return this.config.actions ? this.config.actions.filter(action => action.icon) : null;
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

    public isAllColumnSortable(): boolean {
        return this.config.columns.every(column => column.hasOwnProperty("sortable") && this.allItems.length > 1);
    }

    public setPage(data: any) {
        if (data.totalItems) {
            this.pager = this.pagerService.getPager(this.allItems.length, data.page, data.totalItems);
        } else {
            this.pager = this.pagerService.getPager(this.allItems.length, data.page, this.config.pagination.rowsPerPage);
        }
        this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
    }

    public getIndex(index): number {
        if (this.config.pagination) {
            return index + this.pager.pageSize * (this.pager.currentPage - 1);
        } else {
            return index;
        }
    }

    public getFirstKeyFromJson(object): string {
        let resut: string;
        if (object) {
            resut = Object.keys(object)[0];
        }
        return resut;
    }

    public getFirstValueFromJson(object): any {
        let resut: any;
        if (object) {
            resut = Object.values(object)[0];
        }
        return resut;
    }

    public getClass(value, action): string {
        let resut: any;
        if (value[this.getFirstKeyFromJson(action?.condition)] === this.getFirstValueFromJson(action?.condition)) {
            resut = action.icon.calss
        }
        return resut;
    }

}