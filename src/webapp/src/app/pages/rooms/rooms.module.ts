import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from 'src/app/shared';
import { RoomModalComponent } from './room-modal/room-modal.component';
import { RoomsComponent } from './rooms.component';

const routes: Routes = [
    { path: '', component: RoomsComponent }
];

const MODULE_COMPONENT = [
    RoomsComponent,
    RoomModalComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [RouterModule.forChild(routes), SharedModule],
    entryComponents: [
        RoomModalComponent
    ]
})
export class RoomsModule { }