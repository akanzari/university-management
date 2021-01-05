import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { LayoutService, TokenService } from 'src/app/core/services';

@Component({
  selector: 'be-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  animations: [
    trigger('avatarContent', [
      state('hidden', style({
        height: '0',
        opacity: '0'
      })),
      state('visible', style({
        height: '*', opacity: '1'
      })),
      transition('visible <=> hidden', animate('300ms linear'))
    ])
  ]
})

export class NavbarComponent implements OnInit {

  public isSearchSmDisplay: boolean = false;
  public isNavBarSmDisplay: boolean = false;

  public notifs: any[] = [];
  public numberAlerts: any = 0;

  constructor(private tokenService: TokenService,
    public ls: LayoutService) { }

  ngOnInit() {
    //this.tokenService.getProfile().then(res => console.log(res));
  }

  public toggleSearchModeForSmallScreen(): void {
    this.isSearchSmDisplay = !this.isSearchSmDisplay;
  }

  public toggleNavBarForSmallScreen(): void {
    this.isNavBarSmDisplay = !this.isNavBarSmDisplay;
  }

  public logout(): void {
    this.tokenService.logout();
  }

  public change() {
    this.isNavBarSmDisplay = !this.isNavBarSmDisplay;
  }

}