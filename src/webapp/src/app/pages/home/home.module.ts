import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';
import { SharedModule } from 'src/app/shared';
import { HomeComponent } from './home.component';

const MODULE_COMPONENT = [
    HomeComponent
];

@NgModule({
    declarations: MODULE_COMPONENT,
    imports: [SharedModule, ChartsModule]
})
export class HoùeModule { }