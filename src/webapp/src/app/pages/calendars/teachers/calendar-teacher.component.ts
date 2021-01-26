import { Component, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CalendarEvent, CalendarView } from 'angular-calendar';
import {
    isSameMonth,
    isSameDay,
    startOfMonth,
    endOfMonth,
    startOfWeek,
    endOfWeek,
    startOfDay,
    endOfDay,
    format,
} from 'date-fns';
import { Observable } from 'rxjs';
import { TeacherService } from 'src/app/core/services';
const colors: any = {
    red: {
        primary: "#ad2121",
        secondary: "#FAE3E3"
    },
    blue: {
        primary: "#1e90ff",
        secondary: "#D1E8FF"
    },
    yellow: {
        primary: "#e3bc08",
        secondary: "#FDF1BA"
    }
};


@Component({
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: "calendar-teacher.component.html"
})
export class CalendarTeacherComponent implements OnInit {

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    events$: Observable<CalendarEvent<{ teacher: any }>[]>;

    activeDayIsOpen: boolean = false;

    constructor(private teacherService: TeacherService) { }

    ngOnInit(): void {
        this.fetchEvents();
    }

    fetchEvents(): void {
        this.events$ = this.teacherService.getTeachers()
            .pipe(
                map((result: any) => {
                    let teachers = result.map((teacher: any) => {
                        let results: any[] = [];
                        teacher.disponibilities.forEach(disp => {
                            results.push({
                                title: teacher.fullName + " - " + disp.motif,
                                start: startOfDay(new Date(disp.startDate)),
                                end: endOfDay(new Date(disp.endDate)),
                                color: colors.yellow,
                                allDay: true,
                                meta: {
                                    teacher,
                                },
                            })
                        });
                        return results;
                    });
                    let resultss = [];
                    teachers.forEach((element) => {
                        resultss = resultss.concat(element)
                    })
                    return resultss;
                })
            );
    }


    setView(view: CalendarView) {
        this.view = view;
    }

    dayClicked({
        date,
        events,
    }: {
        date: Date;
        events: CalendarEvent<{ teacher: any }>[];
    }): void {
        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
                this.viewDate = date;
            }
        }
    }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

}