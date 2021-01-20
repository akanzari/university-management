import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';;
import { ConfigService } from '@ngx-config/core';
import { CreateTeacherRequest } from '../models';
import { Teacher } from '../models/teacher.model';

@Injectable()
export class TeacherService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "teacher/";
    }

    addTeacher(data: CreateTeacherRequest): Observable<HttpResponse<HttpResponse<Teacher>>> {
        return this.httpClient.post<HttpResponse<Teacher>>(this.domain, data, { observe: 'response' });
    }

    getSingleTeacher(id: string): Observable<Teacher> {
        return this.httpClient.get<Teacher>(this.domain + id);
    }

    getTeachers(): Observable<Teacher[]> {
        return this.httpClient.get<Teacher[]>(this.domain);
    }

    getTeachersByUps(data): Observable<Teacher[]> {
        return this.httpClient.post<Teacher[]>(this.domain + "ups", data);
    }

    searchTeachers(data): Observable<Teacher[]> {
        return this.httpClient.post<Teacher[]>(this.domain + "search", data);
    }

}