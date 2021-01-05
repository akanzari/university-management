import { Component, Input, EventEmitter, Output } from '@angular/core';

@Component({
    selector: 'cm-pagination',
    templateUrl: './pagination.component.html',
    styleUrls: ['./pagination.component.scss']
})
export class CmPaginationComponent {

    @Input()
    public pager: any = {};

    @Input()
    public rowsPerPageOptions?: number[];

    @Output()
    public updatePager: EventEmitter<any> = new EventEmitter();

    public setPage(page: number, totalItems?: number) {
        if (totalItems) {
            this.updatePager.emit({ page: page, totalItems: totalItems });
        } else {
            this.updatePager.emit({ page: page, totalItems: this.pager.pageSize });
        }
    }

}