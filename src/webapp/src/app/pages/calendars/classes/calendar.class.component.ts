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
import { ClassService, TeacherService } from 'src/app/core/services';
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
    templateUrl: "calendar.class.component.html"
})
export class CalendarClassComponent implements OnInit {

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    events$: Observable<CalendarEvent<{ teacher: any }>[]>;

    activeDayIsOpen: boolean = false;

    constructor(private classService: ClassService) { }

    ngOnInit(): void {
        this.fetchEvents();
    }

    fetchEvents(): void {
        this.events$ = this.classService.getClasses()
            .pipe(
                map((result: any) => {
                    return result.map((classe: any) => {
                        return {
                            title: classe.label + " - " + classe.typeExam,
                            start: startOfDay(new Date(classe.examDate)),
                            end: endOfDay(new Date(classe.examDate)),
                            color: colors.blue,
                            allDay: true,
                            meta: {
                                classe,
                            },
                        }
                    });
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