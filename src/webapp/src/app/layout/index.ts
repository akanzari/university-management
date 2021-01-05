import { NgModule } from '@angular/core';
import { NavbarComponent } from './navbar/navbar.component';
import { SiderbarComponent } from './sidebar/sidebar.component';
import { FooterComponent } from './footer/footer.component';
import { SharedModule } from '../shared';

export const COMPONENTS = [NavbarComponent, SiderbarComponent, FooterComponent];

@NgModule({
  imports: [SharedModule],
  declarations: COMPONENTS,
  exports: COMPONENTS,
})
export class LayoutModule {}
