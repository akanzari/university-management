import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Room } from '../models';

@Injectable()
export class RoomService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "room/";
    }

    addRoom(data): Observable<HttpResponse<HttpResponse<Room>>> {
        return this.httpClient.post<HttpResponse<Room>>(this.domain, data, { observe: 'response' });
    }

    addDisponibility(data): Observable<any> {
        return this.httpClient.post<any>(this.domain + "disponibility", data, { observe: 'response' });
    }

    updateRoom(data): Observable<any> {
        return this.httpClient.put<any>(this.domain, data, { observe: 'response' });
    }

    updateDisponibility(classRoomId: string, data): Observable<any> {
        return this.httpClient.post<any>(this.domain + "disponibility/" + classRoomId, data, { observe: 'response' });
    }

    deleteRoom(id) {
        return this.httpClient.delete<void>(this.domain + id);
    }

    deleteDisponibility(id) {
        return this.httpClient.delete<void>(this.domain + "disponibility/" + id);
    }

    getDisponibilitiesByRoom(id): Observable<any> {
        return this.httpClient.get<any>(this.domain + "disponibility/" + id);
    }

    getRooms(): Observable<Room[]> {
        return this.httpClient.get<Room[]>(this.domain + "without-disponibilities");
    }

    getRoomsByBlocs(data): Observable<Room[]> {
        return this.httpClient.post<Room[]>(this.domain + "blocs", data);
    }

    getSingleRoom(username: string): Observable<Room> {
        return this.httpClient.get<Room>(this.domain + '/' + username);
    }

}