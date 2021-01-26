import { Component } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label, SingleDataSet } from 'ng2-charts';
import * as _ from 'lodash';
import { TeacherService, DisponibilityService, RoomService } from 'src/app/core/services';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {

    public events$: Observable<any>;
    public teachers$: Observable<any>;
    public rooms$: Observable<any>;

    public barChartOptions: ChartOptions = {
        responsive: true,
        // We use these empty structures as placeholders for dynamic theming.
        scales: { xAxes: [{}], yAxes: [{}] },
        plugins: {
            datalabels: {
                anchor: 'end',
                align: 'end',
            }
        }
    };

    public barChartLabelsT: Label[];
    public barChartDataT: ChartDataSets[];

    public barChartLabelsR: Label[];
    public barChartDataR: ChartDataSets[];

    public barChartLegend = true;
    public barChartType: ChartType = 'bar';

    constructor(private teacherService: TeacherService,
        private roomService: RoomService,
        private spinner: NgxSpinnerService,
        private disponibilityService: DisponibilityService) { }

    ngOnInit(): void {
        this.teachers$ = this.teacherService.getTeachers();
        this.rooms$ = this.roomService.getRooms();
    }

    public onChangeTeacher(event) {
        this.events$ = this.disponibilityService.getDisponibilitiesByTeacher(event.teacherId)
            .pipe(
                map((rs: any) => {
                    let result = rs.filter(item => item.motif == "Surveiller");
                    let teachers = result.map((item: any) => {
                        return { startHour: item.startHour, endHour: item.endHour, year: item.startDate.split('-')[0] };
                    });
                    const groupByYear = Object.entries(
                        teachers.reduce((acc, el) => {
                            if (!acc[el.year]) {
                                acc[el.year] = [el];
                            } else {
                                acc[el.year].push(el);
                            }
                            return acc;
                        }, {})
                    );
                    const res = groupByYear.map(([year, data]) => ({
                        nbr: (data as any).length,
                        nbrHour: (data as any).reduce(
                            (acc, { startHour, endHour }) => acc + endHour - startHour,
                            0
                        ),
                        year,
                    }));
                    return res;
                })
            );
        this.events$.subscribe(items => {
            this.barChartLabelsT = items.map(el => el.year);
            let a: any[] = []
            this.barChartLabelsT.forEach(element => a.push(items.find(el => el.year == element).nbr));
            let b: any[] = []
            this.barChartLabelsT.forEach(element => {
                let x = items.find(el => el.year == element).nbrHour;
                let rst;
                if (x.toString().length == 3) {
                    rst = parseFloat(x.toString().substring(0, 1) + "." + x.toString().substring(1, 3))
                } else if (x.toString().length == 3) {
                    rst = parseFloat(x.toString().substring(0, 2) + "." + x.toString().substring(2, 4));
                }
                b.push(rst);
            });
            this.barChartDataT = [
                { data: a, label: "Nombre d'heure surveiller" },
                { data: b, label: 'Nombre de surveillances' }
            ];
        })
    }

    public onChangeRoom(event) {
        this.events$ = this.disponibilityService.getDisponibilitiesByRoom(event.classRoomId)
            .pipe(
                map((rs: any) => {
                    let result = rs.filter(item => item.motif == "DS" || item.motif == "Examen" || item.motif == "Rattrapage");
                    let teachers = result.map((item: any) => {
                        return { startHour: item.startHour, endHour: item.endHour, year: item.startDate.split('-')[0] };
                    });
                    const groupByYear = Object.entries(
                        teachers.reduce((acc, el) => {
                            if (!acc[el.year]) {
                                acc[el.year] = [el];
                            } else {
                                acc[el.year].push(el);
                            }
                            return acc;
                        }, {})
                    );
                    const res = groupByYear.map(([year, data]) => ({
                        nbr: (data as any).length,
                        nbrHour: (data as any).reduce(
                            (acc, { startHour, endHour }) => acc + endHour - startHour,
                            0
                        ),
                        year,
                    }));
                    return res;
                })
            );
        this.events$.subscribe(items => {
            this.barChartLabelsR = items.map(el => el.year);
            let a: any[] = []
            this.barChartLabelsR.forEach(element => a.push(items.find(el => el.year == element).nbr));
            let b: any[] = []
            this.barChartLabelsR.forEach(element => {
                let x = items.find(el => el.year == element).nbrHour;
                let rst;
                if (x.toString().length == 3) {
                    rst = parseFloat(x.toString().substring(0, 1) + "." + x.toString().substring(1, 3))
                } else if (x.toString().length == 3) {
                    rst = parseFloat(x.toString().substring(0, 2) + "." + x.toString().substring(2, 4));
                }
                b.push(rst);
            });
            this.barChartDataR = [
                { data: a, label: "Nombre d'heure surveiller" },
                { data: b, label: 'Nombre de surveillances' }
            ];
        })
    }

}