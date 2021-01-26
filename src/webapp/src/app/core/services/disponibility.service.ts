import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';

@Injectable()
export class DisponibilityService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "disponibility/";
    }

    addDisponibilityToRoom(roomId, data): Observable<any> {
        return this.httpClient.post<any>(this.domain + "room/" + roomId, data, { observe: 'response' });
    }

    addDisponibilityToTeacher(teacherId, data): Observable<any> {
        return this.httpClient.post<any>(this.domain + "teacher/" + teacherId, data, { observe: 'response' });
    }

    updateDisponibility(data): Observable<any> {
        return this.httpClient.put<any>(this.domain, data, { observe: 'response' });
    }

    deleteDisponibilityFromTeacher(teacherId, disponibilityId) {
        return this.httpClient.delete<void>(this.domain + "teacher/" + teacherId + "/" + disponibilityId);
    }

    deleteDisponibilityFromRoom(roomId, disponibilityId) {
        return this.httpClient.delete<void>(this.domain + "room/" + roomId + "/" + disponibilityId);
    }

    getDisponibilitiesByTeacher(teacherId): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain + "teacher/" + teacherId);
    }

    getDisponibilitiesByRoom(roomId): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain + "room/" + roomId);
    }

}