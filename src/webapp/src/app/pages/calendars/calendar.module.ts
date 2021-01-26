import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { SharedModule } from 'src/app/shared';
import { CalendarClassComponent } from './classes/calendar.class.component';
import { CalendarRoomComponent } from './rooms/calendar-room.component';
import { CalendarStudentComponent } from './students/calendar-student.component';
import { CalendarTeacherComponent } from './teachers/calendar-teacher.component';

const routes: Routes = [
    { path: 'teachers', component: CalendarTeacherComponent },
    { path: 'classes', component: CalendarClassComponent },
    { path: 'rooms', component: CalendarRoomComponent },
    { path: 'students', component: CalendarStudentComponent }
];

const MODULE_COMPONENT = [
    CalendarTeacherComponent,
    CalendarClassComponent,
    CalendarRoomComponent,
    CalendarStudentComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [
        RouterModule.forChild(routes),
        SharedModule,
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory,
        })
    ]
})
export class CalendarsModule { }