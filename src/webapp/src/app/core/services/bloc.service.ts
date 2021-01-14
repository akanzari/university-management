import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Bloc } from '../models';

@Injectable()
export class BlocService {

    readonly domain: string;

    constructor(private config: ConfigService,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl') + "bloc/";
    }

    getBlocs(siteId: string): Observable<Bloc[]> {
        return this.httpClient.get<Bloc[]>(this.domain + siteId);
    }

}