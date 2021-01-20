import * as moment from 'moment';
import { AbstractControl, ValidatorFn } from "@angular/forms";

export namespace Dates {
    export const dateFormats = {
        format1: 'DD/MM/YYYY',
        format2: 'DD-MM-YYYY',
        format3: 'YYYY-MM-DD'
    }
    export function toDate(value: string,
        formats = [
            dateFormats.format1,
            dateFormats.format2,
            dateFormats.format3
        ]
    ): { valid: boolean, date: Date | null } {
        if (!value || !value.toString().trim().length) {
            return { valid: true, date: null };
        }
        let trimmed = value.toString().trim();
        if (!formats.some(format => format.length == trimmed.length)) {
            return { valid: false, date: null };
        }
        let date = moment(value.toString(), formats, true);
        if (!date.isValid()) {
            return { valid: false, date: null };
        }
        return { valid: true, date: date.toDate() };
    }
}


export function dateValidator(validatorField: { [key: string]: boolean }): ValidatorFn {
    return (c: AbstractControl): { [key: string]: boolean } | null => {
        if (!Dates.toDate(c.value).valid) {
            return validatorField;
        }
        return null;
    };
}