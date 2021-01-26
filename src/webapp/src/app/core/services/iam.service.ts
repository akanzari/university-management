import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { SpecificUserResponse, UserResponse } from '../models';

@Injectable()
export class IAMService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "iam/";
    }

    addUser(data): Observable<any> {
        let httpOptions = { headers: new HttpHeaders({}), responseType: 'text' as 'json' };
        return this.httpClient.post<any>(this.domain + 'user', data, httpOptions);
    }

    updateUser(data): Observable<void> {
        return this.httpClient.put<void>(this.domain + 'user', data);
    }

    disabledUser(id): Observable<void> {
        return this.httpClient.patch<void>(this.domain + 'user/disabled/' + id, null);
    }

    enabledUser(id): Observable<void> {
        return this.httpClient.patch<void>(this.domain + 'user/enable/' + id, null);
    }

    deleteUser(id) {
        return this.httpClient.delete<void>(this.domain + 'user/' + id);
    }

    getUsers(): Observable<UserResponse[]> {
        return this.httpClient.get<UserResponse[]>(this.domain + 'users');
    }

    getCurrentUser(): Observable<UserResponse[]> {
        return this.httpClient.get<UserResponse[]>(this.domain + 'current-user');
    }

    getSingleUser(username: string): Observable<UserResponse> {
        return this.httpClient.get<UserResponse>(this.domain + 'user/' + username);
    }

    getUserByRole(role: string): Observable<SpecificUserResponse[]> {
        return this.httpClient.get<SpecificUserResponse[]>(this.domain + 'users/' + role);
    }

    getRoles(): Observable<string[]> {
        return this.httpClient.get<string[]>(this.domain + 'roles');
    }

}