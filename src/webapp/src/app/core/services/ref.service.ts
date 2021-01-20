import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from '@ngx-config/core';
import { Speciality } from '../models/speciality.model';
import { Site } from '../models/site.modal';
import { Department } from '../models';
import { map } from 'rxjs/operators';
import { DatePipe } from '@angular/common';

@Injectable()
export class RefService {

    readonly domain: string;

    readonly domainMigration: string;

    constructor(private config: ConfigService,
        private datePipe: DatePipe,
        private httpClient: HttpClient) {
        this.domain = this.config.getSettings('environment.serviceBaseUrl');
        this.domainMigration = this.config.getSettings('environment.serviceBaseUrlMigration');
    }

    getBlocs(): Observable<any[]> {
        return this.httpClient.get<any[]>("./assets/data/bloc.json");
    }

    getTypeExam(): Observable<any[]> {
        return this.httpClient.get<any[]>("./assets/data/type-exam.json");
    }

    getLevels(): Observable<any[]> {
        return this.httpClient.get<any[]>("./assets/data/level.json");
    }

    getSpecialities(): Observable<Speciality[]> {
        return this.httpClient.get<Speciality[]>(this.domain + "speciality");
    }

    getSites(): Observable<Site[]> {
        return this.httpClient.get<Site[]>(this.domain + "site");
    }

    getDepartements(): Observable<Department[]> {
        return this.httpClient.get<Department[]>(this.domain + "department");
    }

    getReasonRooms(): Observable<any[]> {
        return this.httpClient.get<any[]>(this.domain + "reason-room");
    }

    getDays(): Observable<any> {
        return this.httpClient.get<any[]>(this.domainMigration + "days");
    }

    getSeances(): Observable<any> {
        return this.httpClient.get<any[]>(this.domain + "seance").pipe(
            map((seances: any[]) => this.mapSeances(seances)));
    }

    private mapSeances(seances: any[]): any[] {
        let result : any[] = [];
        if (seances) {
            seances.forEach(seance => {
                result.push({
                    seanceId: seance.seanceId,
                    label: seance.seanceId + " - " + seance.startHour + " à " + seance.endHour,
                    startHour: seance.startHour,
                    endHour: seance.endHour
                })
            })
        }
        return result;
    }

    getSemeters(): Observable<any> {
        return this.httpClient.get<any[]>(this.domain + "semester").pipe(
            map((semesters: any[]) => this.mapSemeters(semesters)));
    }

    private mapSemeters(semesters: any[]): any[] {
        let result : any[] = [];
        if (semesters) {
            semesters.forEach(semester => {
                result.push({
                    semestreId: semester.semestreId,
                    label: (semester.code == "1" ? "S1" : "S2") + " - " + this.datePipe.transform(semester.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(semester.endDate, "dd/MM/yyyy"),
                    code: semester.code,
                    startDate: semester.startDate,
                    endDate: semester.endDate,
                    periods: this.mapPeriods(semester.periods)
                })
            })
        }
        return result;
    }

    private mapPeriods(periods: any[]): any[] {
        let result : any[] = [];
        if (periods) {
            periods.forEach(period => {
                result.push({
                    periodId: period.periodId ? period.periodId : null,
                    label: period.code ? (period.code == "1" ? "P1" : "P2") + " - " + this.datePipe.transform(period.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(period.endDate, "dd/MM/yyyy") : null,
                    code: period.code ? period.code : null,
                    startDate: period.startDate ? period.startDate : null,
                    endDate: period.endDate ? period.endDate : null,
                    weeks: this.mapWeeks(period.weeks)
                })
            })
        }
        return result;
    }

    private mapWeeks(weeks: any[]): any[] {
        let result : any[] = [];
        if (weeks) {
            weeks.forEach(week => {
                result.push({
                    weekId: week.weekId ? week.weekId : null,
                    label: this.datePipe.transform(week.startDate, "dd/MM/yyyy") + " à " + this.datePipe.transform(week.endDate, "dd/MM/yyyy"),
                    startDate: week.startDate ? week.startDate : null,
                    endDate: week.endDate ? week.endDate : null
                })
            })
        }
        return result;
    }

}