import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OrderModule } from 'ngx-order-pipe';
import { NgxSpinnerModule } from "ngx-spinner";
import { NgSelectModule } from '@ng-select/ng-select';
import { FilterPipe } from './components/cm-table-container/pipes/filter.pipe';
import { RemovePopupComponent } from './components/comfirmation-popup/remove/remove-popup.component';
import { CmPaginationComponent } from './components/cm-table-container/components/pagination/pagination.component';
import { CmTbaleContainerComponent } from './components/cm-table-container/cm-table-container.component';

@NgModule({
    declarations: [
        CmTbaleContainerComponent,
        CmPaginationComponent,
        RemovePopupComponent,
        FilterPipe
    ],
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        TranslateModule,
        ReactiveFormsModule,
        OrderModule
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
        CmTbaleContainerComponent
    ]
})
export class SharedModule { }