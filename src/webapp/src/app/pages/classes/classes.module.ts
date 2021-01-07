import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { ClassModalComponent } from './class-modal/class-modal.component';
import { ClassesComponent } from './classes.component';

const routes: Routes = [
    { path: '', component: ClassesComponent }
];

const MODULE_COMPONENT = [
    ClassesComponent,
    ClassModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        ClassModalComponent
    ]
})
export class ClassesModule { }