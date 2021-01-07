import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';
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
        return this.httpClient.post<HttpResponse<Room>>(this.domain, data, { observe: 'response' }).pipe(retry(1));
    }

    updateRoom(data): Observable<HttpResponse<HttpResponse<Room>>> {
        return this.httpClient.put<HttpResponse<Room>>(this.domain, data, { observe: 'response' }).pipe(retry(1));
    }

    deleteRoom(id) {
        return this.httpClient.delete<void>(this.domain + id).pipe(retry(1));
    }

    getRooms(): Observable<Room[]> {
        return this.httpClient.get<Room[]>(this.domain).pipe(retry(1));
    }

    getSingleRoom(username: string): Observable<Room> {
        return this.httpClient.get<Room>(this.domain + '/' + username).pipe(retry(1));
    }

}