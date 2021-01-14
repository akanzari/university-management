import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Classs } from '../models';

@Injectable()
export class ClassService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "class/";
    }

    addClass(data): Observable<HttpResponse<Classs>> {
        return this.httpClient.post<Classs>(this.domain, data, { observe: 'response' });
    }

    updateClass(data): Observable<HttpResponse<Classs>> {
        return this.httpClient.put<Classs>(this.domain, data, { observe: 'response' });
    }

    deleteClass(id) {
        return this.httpClient.delete<void>(this.domain + id);
    }

    getClasses(): Observable<Classs[]> {
        return this.httpClient.get<Classs[]>(this.domain);
    }

    getClassesByModule(moduleId: string): Observable<Classs[]> {
        return this.httpClient.get<Classs[]>(this.domain + "moduleId/" + moduleId);
    }

    getSingleClass(id: string): Observable<Classs> {
        return this.httpClient.get<Classs>(this.domain + id);
    }

}