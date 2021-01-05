import { Injectable } from '@angular/core';

import { Title } from '@angular/platform-browser';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter, map, switchMap } from 'rxjs/operators';

const APP_TITLE = 'Service Provider';
const SEPARATOR = ' > ';

@Injectable()
export class TitleService {
    constructor(private router: Router, private activatedRoute: ActivatedRoute, private titleService: Title) { }

    init() {
        this.router.events
            .pipe(
                filter((event) => event instanceof NavigationEnd),
                map(() => this.activatedRoute),
                map((route) => {
                    while (route.firstChild) route = route.firstChild;
                    return route;
                }),
                switchMap((route) => route.data),
                map((data) => {
                    if (data.title) {
                        // If a route has a title set (e.g. data: {title: "Foo"}) then we use it
                        return data.title;
                    } else {
                        // If not, we do a little magic on the url to create an approximation
                        return this.router.url.split('/').reduce((acc, frag) => {
                            if (acc && frag) {
                                acc += SEPARATOR;
                            }
                            return this.router.url.split('/').reduce((acc, frag) => {
                                if (acc && frag) {
                                    acc += SEPARATOR;
                                }
                                return acc + TitleService.ucFirst(frag);
                            });
                        });
                    }
                }),
            )
            .subscribe((pathString) => {
                if (pathString) {
                    return this.titleService.setTitle(`${pathString}`);
                }
                return this.titleService.setTitle(`${APP_TITLE}`);
            });
    }

    static ucFirst(string: any) {
        if (!string) {
            return string;
        }
        return string.charAt(0).toUpperCase() + string.slice(1);
    }
}