import { AfterViewInit, Component, Input, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/internal/Subscription';
import * as _ from 'lodash';
import { SideBarMenu, User } from 'src/app/core/models';
import { LayoutService, TokenService } from 'src/app/core/services';
import { subMenuItems } from 'src/app/settings';

@Component({
  selector: 'be-sidebar',
  templateUrl: './siderbar.component.html',
  styles: [`[hidden]:not([broken]) { display: none !important;}`],
  animations: [
    trigger('subMenuContent', [
      state('hidden', style({
        height: '0',
        opacity: '0'
      })),
      state('visible', style({
        height: '*', opacity: '1'
      })),
      transition('visible <=> hidden', animate('300ms linear'))
    ]),
    trigger('subsubMenuContent', [
      state('hidden', style({
        height: '0', opacity: '0'
      })),
      state('visible', style({
        height: '*', opacity: '1'
      })),
      transition('visible <=> hidden', animate('300ms linear'))
    ])
  ]
})
export class SiderbarComponent implements OnInit, AfterViewInit, OnDestroy {

  @Input()
  public items: SideBarMenu[] = [];
  public currentMenu: string = '0';
  public currentSubMenu: string = null;
  public menuClickAction: boolean = false;
  public routeObservable: Subscription;
  public hidden: boolean = false;
  public currentAccount: User;

  public subMenus;

  public reduce = false;
  public template;
  public showSubs = false;
  public displaySub = true;

  constructor(public ls: LayoutService,
    private renderer: Renderer2) { }

  public async ngOnInit() {
    this.items = subMenuItems;
    this.currentAccount = null;
  }

  public selectMenu(menu: string): void {
    this.menuClickAction = true;
    menu !== this.currentMenu ? (this.displaySub = true) : (this.displaySub = !this.displaySub);
    menu !== this.currentMenu ? (this.currentMenu = menu) : (this.currentMenu = this.currentMenu);
    this.currentSubMenu = null;
  }

  public selectSubMenu(subMenu: any, menu: any): void {
    this.menuClickAction = true;
    if (!this.ls.isSideBarDisplay && !subMenu) {
      this.displaySub = false;
    }
    subMenu.code !== this.currentSubMenu ? (this.currentSubMenu = subMenu.code) : (this.currentSubMenu = null);
  }

  public ngAfterViewInit(): void {
    this.template = <HTMLElement>document.getElementsByClassName('sidebar')[0];
    this.renderer.listen('body', 'click', () => {
      if (!this.menuClickAction && this.currentMenu && !this.ls.isSideBarDisplay) {
        this.currentMenu = null;
        this.items = subMenuItems;
      }
      this.menuClickAction = false;
    });
    document.addEventListener('reduce', function () { }); 
  }

  public getNavLinkRouteStyle(action): Boolean {
    /*if (this.currentAccount && this.currentAccount.userId === "psce_user") {
      if (!this.authzGuardService.isAuthorized(this.currentAccount, action)) {
        this.hidden = true;
      } else {
        this.hidden = false;
      }
    }*/
    return false;
  }

  search(searchValue: string) {
    this.items = [];
    var el = _.filter(subMenuItems, function (item) {
      return item.defaultLabel.toUpperCase().includes(searchValue.toUpperCase());
    });
    if (el.length != 0) {
      el.forEach((i) => {
        this.items.push(i);
      });
    }
    var el1 = _.filter(subMenuItems[0].subMenus, function (item) {
      return item.defaultLabel.toUpperCase().includes(searchValue.toUpperCase());
    });
    if (el1.length != 0) {
      el1.forEach((i) => {
        let item = new SideBarMenu();
        item.code = i.code;
        item.defaultLabel = i.defaultLabel;
        item.icon = '';
        item.routerLink = [i.routerLink[0]];
        //item.fragment = '';
        this.items.push(item);
      });
    }
    if (searchValue === '') {
      this.items = subMenuItems;
    }
  }

  public initSearch() {
    if (this.items.length === 0) {
      (<HTMLInputElement>document.getElementById('search')).value = '';
      this.items = subMenuItems;
    }
  }

  public ngOnDestroy(): void {
    if (this.routeObservable) {
      this.routeObservable.unsubscribe();
    }
  }

}