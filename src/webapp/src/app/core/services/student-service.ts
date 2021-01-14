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

    addStudent(data: CreateStudentRequest): Observable<HttpResponse<HttpResponse<Student>>> {
        return this.httpClient.post<HttpResponse<Student>>(this.domain, data, { observe: 'response' });
    }

}