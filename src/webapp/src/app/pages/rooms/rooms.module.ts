import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { DisponibilityModalComponent } from './disponibility-modal/disponibility.component';
import { RoomModalComponent } from './add-room-modal/add-room.component';
import { RoomsComponent } from './rooms.component';
import { UpdateRoomModalComponent } from './update-room-modal/update-room.component';

const routes: Routes = [
    { path: '', component: RoomsComponent }
];

const MODULE_COMPONENT = [
    RoomsComponent,
    RoomModalComponent,
    DisponibilityModalComponent,
    UpdateRoomModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        RoomModalComponent,
        DisponibilityModalComponent,
        UpdateRoomModalComponent
    ]
})
export class RoomsModule { }