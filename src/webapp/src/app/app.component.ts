import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SideBarMenu } from './core/models';
import { LayoutService, TitleService } from './core/services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public hideSidebar: Observable<boolean>;
  public items: SideBarMenu[] = [];

  constructor(public ls: LayoutService,
    private titleService: TitleService) {
    this.titleService.init();
    this.hideSidebar = this.ls.getSidebarState();
  }

}