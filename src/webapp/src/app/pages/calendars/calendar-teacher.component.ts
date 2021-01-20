import {
    Component,
    ChangeDetectionStrategy
} from "@angular/core";
import {
    startOfDay,
    subDays,
    addDays,
    endOfMonth,
    isSameDay,
    isSameMonth,
    addHours
} from "date-fns";
import { Subject } from "rxjs";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
    CalendarEvent,
    CalendarView
} from "angular-calendar";

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
export class CalendarTeacherComponent {
    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    modalData: {
        action: string;
        event: CalendarEvent;
    };

    refresh: Subject<any> = new Subject();

    activeDayIsOpen: boolean = true;

    constructor(private modal: NgbModal) { }

    events: CalendarEvent[] = [
        {
            start: subDays(startOfDay(new Date()), 1),
            end: addDays(new Date(), 1),
            title: "A 3 day event",
            color: colors.red,
            allDay: true
        },
        {
            start: startOfDay(new Date()),
            title: "An event with no end date",
            color: colors.yellow
        },
        {
            start: subDays(endOfMonth(new Date()), 3),
            end: addDays(endOfMonth(new Date()), 3),
            title: "A long event that spans 2 months",
            color: colors.blue
        },
        {
            start: addHours(startOfDay(new Date()), 2),
            end: addHours(new Date(), 2),
            title: "A draggable and resizable event",
            color: colors.yellow
        }
    ];

    dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
            }
            this.viewDate = date;
        }
    }

    setView(view: CalendarView) {
        this.view = view;
    }

    closeOpenMonthViewDay() {
        this.activeDayIsOpen = false;
    }
}
