import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Speciality } from '../models/speciality.model';
import { Site } from '../models/site.modal';
import { Department } from '../models';

@Injectable()
export class RefService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl');
    }

    getSpecialities(): Observable<Speciality[]> {
        return this.httpClient.get<Speciality[]>(this.domain + "speciality");
    }

    getSites(): Observable<Site[]> {
        return this.httpClient.get<Site[]>(this.domain + "site");
    }

    getDepartements(): Observable<Department[]> {
        return this.httpClient.get<Department[]>(this.domain + "departement");
    }

    getReasonRooms(): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain + "reason-room");
    }

}