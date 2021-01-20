import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
    name: 'display'
})
export class DisplayPipe implements PipeTransform {

    constructor(public datePipe: DatePipe) { }

    transform(value: any, arg: any): any {
        if (value) {
            let date = new Date(value);
            let nbr = +value;
            if (Object.prototype.toString.call(value) == "[object Object]") {
                return value[arg];
            } else if ((typeof value === 'string' || value instanceof String) ) {
                return value as string;
            // } else if (this.isValidDate(date) && Number.isNaN(nbr)) {
            //     return this.datePipe.transform(new Date(value), 'dd/MM/yyyy');
            //
         } else if (typeof value === "boolean") {
                return value;
            }
        }
    }

    // private isValidDate(date: any) {
    //     return date && Object.prototype.toString.call(date) === "[object Date]" && !isNaN(date);
    // }

}