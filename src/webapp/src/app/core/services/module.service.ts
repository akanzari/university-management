import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Module } from '../models';
import { CreateModuleRequest } from '../models/request/create-module-request.model';

@Injectable()
export class ModuleService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "module/";
    }

    addModule(data: CreateModuleRequest): Observable<HttpResponse<Module>> {
        return this.httpClient.post<Module>(this.domain, data, { observe: 'response' });
    }

    updateModule(data): Observable<HttpResponse<Module>> {
        return this.httpClient.put<Module>(this.domain, data, { observe: 'response' });
    }

    deleteModule(id: string) {
        return this.httpClient.delete<void>(this.domain + id);
    }

    getModules(): Observable<Module[]> {
        return this.httpClient.get<Module[]>(this.domain + "without-classes");
    }

    getAssignClassesByModule(moduleId: string): Observable<any> {
        return this.httpClient.get<any>(this.domain + "assign-classes/" + moduleId);
    }

    getModulesByClass(classId: string): Observable<Module[]> {
        return this.httpClient.get<Module[]>(this.domain + "classId/" + classId);
    }

    getSingleModule(id: string): Observable<Module> {
        return this.httpClient.get<Module>(this.domain + id);
    }

}