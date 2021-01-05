import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { StudentModalComponent } from './student-modal/student-modal.component';
import { StudentsComponent } from './students.component';

const routes: Routes = [
    { path: '', component: StudentsComponent }
];


const MODULE_COMPONENT = [
    StudentsComponent,
    StudentModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        StudentModalComponent
    ]
})
export class StudentsModule { }