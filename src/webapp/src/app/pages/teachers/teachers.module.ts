import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { TeachersComponent } from './teachers.component';

const routes: Routes = [
    { path: '', component: TeachersComponent }
];

const MODULE_COMPONENT = [
    TeachersComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule]
})
export class TeachersModule { }