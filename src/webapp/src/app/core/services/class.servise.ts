import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';
import { ConfigService } from '@ngx-config/core';
import { Class } from '../models';

@Injectable()
export class ClassService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "class/";
    }

    addClass(data): Observable<HttpResponse<HttpResponse<Class>>> {
        return this.httpClient.post<HttpResponse<Class>>(this.domain, data, { observe: 'response' }).pipe(retry(1));
    }

    updateClass(data): Observable<HttpResponse<HttpResponse<Class>>> {
        return this.httpClient.put<HttpResponse<Class>>(this.domain, data, { observe: 'response' }).pipe(retry(1));
    }

    deleteClass(id) {
        return this.httpClient.delete<void>(this.domain + id).pipe(retry(1));
    }

    getClasses(): Observable<Class[]> {
        return this.httpClient.get<Class[]>(this.domain).pipe(retry(1));
    }

    getSingleClass(username: string): Observable<Class> {
        return this.httpClient.get<Class>(this.domain + '/' + username).pipe(retry(1));
    }

}