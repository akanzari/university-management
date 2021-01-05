import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { UserModalComponent } from './user-modal/user-modal.component';
import { UsersComponent } from './users.component';

const routes: Routes = [
    { path: '', component: UsersComponent }
];

const MODULE_COMPONENT = [
    UsersComponent,
    UserModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        UserModalComponent
    ]
})
export class UsersModule { }