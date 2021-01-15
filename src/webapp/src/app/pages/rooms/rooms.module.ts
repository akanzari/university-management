import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { DisponibilityModalComponent } from './disponibility/disponibility.component';
import { RoomModalComponent } from './room-modal/room-modal.component';
import { RoomsComponent } from './rooms.component';

const routes: Routes = [
    { path: '', component: RoomsComponent }
];

const MODULE_COMPONENT = [
    RoomsComponent,
    RoomModalComponent,
    DisponibilityModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        RoomModalComponent,
        DisponibilityModalComponent
    ]
})
export class RoomsModule { }