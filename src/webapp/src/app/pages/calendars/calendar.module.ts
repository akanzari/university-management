import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { SharedModule } from 'src/app/shared';
import { CalendarTeacherComponent } from './calendar-teacher.component';

const routes: Routes = [
    { path: '', component: CalendarTeacherComponent }
];

const MODULE_COMPONENT = [
    CalendarTeacherComponent,
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