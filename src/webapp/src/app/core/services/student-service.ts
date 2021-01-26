import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';;
import { ConfigService } from '@ngx-config/core';
import { CreateStudentRequest, Student } from '../models';

@Injectable()
export class StudentService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "student/";
    }

    addStudent(data: CreateStudentRequest): Observable<any> {
        return this.httpClient.post<any>(this.domain, data, { observe: 'response' });
    }

    getStudents(): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain);
    }

    getCurrentStudent(): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain + "current-student");
    }

    deleteStudent(id) {
        return this.httpClient.delete<void>(this.domain + id);
    }

}