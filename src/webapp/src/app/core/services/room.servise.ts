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
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "class-room/";
    }

    addRoom(data): Observable<HttpResponse<HttpResponse<Room>>> {
        return this.httpClient.post<HttpResponse<Room>>(this.domain, data, { observe: 'response' });
    }

    updateRoom(data): Observable<HttpResponse<HttpResponse<Room>>> {
        return this.httpClient.put<HttpResponse<Room>>(this.domain, data, { observe: 'response' });
    }

    deleteRoom(id) {
        return this.httpClient.delete<void>(this.domain + id);
    }

    getRooms(): Observable<Room[]> {
        return this.httpClient.get<Room[]>(this.domain);
    }

    getSingleRoom(username: string): Observable<Room> {
        return this.httpClient.get<Room>(this.domain + '/' + username);
    }

}