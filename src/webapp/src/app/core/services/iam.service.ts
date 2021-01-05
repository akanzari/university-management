import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { ConfigService } from '@ngx-config/core';
import { SpecificUserResponse, UserResponse } from '../models';

@Injectable()
export class IAMService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "iam/";
    }

    addUser(data): Observable<void> {
        return this.httpClient.post<void>(this.domain + 'create-user', data)
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    updateUser(data): Observable<void> {
        return this.httpClient.put<void>(this.domain + 'update-user', data)
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    deleteUser(id) {
        return this.httpClient.delete<void>(this.domain + 'delete-user/' + id)
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    getUsers(): Observable<UserResponse[]> {
        return this.httpClient.get<UserResponse[]>(this.domain + 'find-users')
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    getSingleUser(username: string): Observable<UserResponse> {
        return this.httpClient.get<UserResponse>(this.domain + 'find-user/' + username)
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    getUserByRole(role: string): Observable<SpecificUserResponse[]> {
        return this.httpClient.get<SpecificUserResponse[]>(this.domain + 'find-users/' + role)
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    getRoles(): Observable<string[]> {
        return this.httpClient.get<string[]>(this.domain + 'find-roles')
            .pipe(
                retry(1),
                catchError(this.processError)
            )
    }

    processError(err) {
        let message = '';
        if (err.error instanceof ErrorEvent) {
            message = err.error.message;
        } else {
            message = `Error Code: ${err.status}\nMessage: ${err.message}`;
        }
        console.log(message);
        return throwError(message);
    }
}