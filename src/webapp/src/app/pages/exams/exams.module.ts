import { NgModule } from '@angular/core';
import { ExamsComponent } from './exams.component';
import { ExamModalComponent } from './exam-modal/exam-modal.component';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';

const routes: Routes = [
    { path: '', component: ExamsComponent }
];

const MODULE_COMPONENT = [
    ExamsComponent,
    ExamModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        ExamModalComponent
    ]
})
export class ExamsModule { }