import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { AssignClassesModalComponent } from './assign-classes/assign-classes.component';
import { ModuleModalComponent } from './module-modal/module-modal.component';
import { ModulesComponent } from './modules.component';

const routes: Routes = [
    { path: '', component: ModulesComponent }
];

const MODULE_COMPONENT = [
    ModulesComponent,
    ModuleModalComponent,
    AssignClassesModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        ModuleModalComponent,
        AssignClassesModalComponent
    ]
})
export class ModulesModule { }