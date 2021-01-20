import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { DisponibilityModalComponent } from './disponibility-modal/disponibility.component';
import { TeacherModalComponent } from './teacher-modal/teacher-modal.component';
import { TeachersComponent } from './teachers.component';

const routes: Routes = [
    { path: '', component: TeachersComponent }
];

const MODULE_COMPONENT = [
    TeachersComponent,
    TeacherModalComponent,
    DisponibilityModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        TeacherModalComponent,
        DisponibilityModalComponent
    ]
})
export class TeachersModule { }