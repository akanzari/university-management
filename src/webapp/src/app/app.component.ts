import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SideBarMenu, User } from './core/models';
import { LayoutService, TitleService, TokenService } from './core/services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public hideSidebar: Observable<boolean>;
  public items: SideBarMenu[] = [];

  constructor(public ls: LayoutService,
    private tokenService: TokenService,
    private router: Router,
    private titleService: TitleService) {
    this.titleService.init();
    this.hideSidebar = this.ls.getSidebarState();
    this.tokenService.getProfile().then((user: User) => {
      if (user.roles.includes('STUDENT') || user.roles.includes('TEACHER')) {
        this.router.navigate(['/exams']);
      }
    });
  }

}