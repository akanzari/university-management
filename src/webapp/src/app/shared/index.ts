import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChartsModule } from 'ng2-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OrderModule } from 'ngx-order-pipe';
import { NgxSpinnerModule } from "ngx-spinner";
import { NgSelectModule } from '@ng-select/ng-select';
import { DisplayPipe } from './components/cm-table-container/pipes/display.pipe';
import { FilterPipe } from './components/cm-table-container/pipes/filter.pipe';
import { RemovePopupComponent } from './components/comfirmation-popup/remove/remove-popup.component';
import { CmPaginationComponent } from './components/cm-table-container/components/pagination/pagination.component';
import { CmTbaleContainerComponent } from './components/cm-table-container/cm-table-container.component';
import { WidthDirective } from './components/cm-table-container/directives/width.directive';

@NgModule({
    declarations: [
        CmTbaleContainerComponent,
        CmPaginationComponent,
        RemovePopupComponent,
        FilterPipe,
        DisplayPipe,
        WidthDirective
    ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        TranslateModule,
        ReactiveFormsModule,
        OrderModule,
        NgSelectModule
    ],
    exports: [
        CommonModule,
        RouterModule,
        NgbModule,
        TranslateModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        NgSelectModule,
        NgxSpinnerModule,
        ChartsModule,
        CmTbaleContainerComponent
    ]
})
export class SharedModule { }