import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
    name: 'filter'
})
export class FilterPipe implements PipeTransform {
    transform(items: any[], valueForFilter: any, fieldForFilter: string): any[] {
        if (!items) return [];
        if (!valueForFilter) return items;
        return items.filter(item => {
            let date = new Date(item[fieldForFilter]);
            let nbr = +item[fieldForFilter];
            if (Object.prototype.toString.call(item[fieldForFilter]) == "[object Object]") {
                return Object.keys(item[fieldForFilter]).some(key => {
                    return String(item[fieldForFilter][key]).toLowerCase().includes(valueForFilter.toLowerCase());
                });
            } else if (fieldForFilter.includes(".")) {
                let array: string[] = fieldForFilter.split('.');
                let str: string = item[array[0]];
                for (let i = 1; i < array.length; i++) {
                    str = str[array[i]];
                }
                return String(str).toLowerCase().includes(valueForFilter.toLowerCase());
            } else if ((typeof item[fieldForFilter] === 'string' || item[fieldForFilter] instanceof String) && !this.isValidDate(date)) {
                return String(item[fieldForFilter]).toLowerCase().includes(valueForFilter.toLowerCase());
            } else if (this.isValidDate(date) && Number.isNaN(nbr)) {
                let datePipe = new DatePipe("fr-FR");
                let result = datePipe.transform(item[fieldForFilter], 'dd/MM/yyyy');
                return String(result).toLowerCase().includes(valueForFilter.toLowerCase());
            }
        });
    }

    private isValidDate(date: any) {
        return date && Object.prototype.toString.call(date) === "[object Date]" && !isNaN(date);
    }
}
