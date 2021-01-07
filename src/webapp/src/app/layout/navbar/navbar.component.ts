import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { LayoutService, RefService, TokenService } from 'src/app/core/services';
import { Site } from 'src/app/core/models/site.modal';
import { Observable } from 'rxjs';
import { StorageMap } from '@ngx-pwa/local-storage';

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
  public displayL: Boolean = false;

  public state: string = 'hidden';
  public site: string = 'Ghazala';

  public notifs: any[] = [];
  public numberAlerts: any = 0;

  public sites$: Observable<Site[]>;

  constructor(private tokenService: TokenService,
    private refService: RefService,
    public ls: LayoutService) { }

  ngOnInit() {
    this.sites$ = this.refService.getSites();
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

  public changeLang() {
    this.displayL = !this.displayL;
  }

  public switchSite(site: Site) {
    this.site = site.label;
  }

  public animateLogo() {
    this.state = (this.state === 'visible' ? 'hidden' : 'visible');
  }

}