import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { ConfigService } from '@ngx-config/core';
import { Speciality } from '../models/speciality.model';

@Injectable()
export class RefService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl');
    }

    getSpecialities(): Observable<Speciality[]> {
        return this.httpClient.get<Speciality[]>(this.domain + "speciality")
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    processError(err) {
        let message = '';
        if (err.error instanceof ErrorEvent) {
            message = err.error.message;
        } else {
            message = `Error Code: ${err.status}\nMessage: ${err.message}`;
        }
        console.log(message);
        return throwError(message);
    }

}