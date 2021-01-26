import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { of } from 'rxjs/internal/observable/of';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { TokenService } from '../services';
import { User } from '../models';
import { RolesMatrix } from '../models/roles.module';

@Injectable()
export class AuthzGuardService implements CanActivate {

    private currentUser: User;

    constructor(private tokenService: TokenService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
        this.tokenService.isLoggedIn().then((status: Boolean) => {
            if (!status) {
                return of(false);
            }
        });

        if (route.data === undefined) {
            return of(true);
        }

        let action = route.data.action as string;
        return this.getCurrentUser().pipe(
            map(user => {
                return this.isAuthorized(user, action)
            })
        );
    }


    isAuthorized(user: User, action: string): boolean {
        if (!user || !action) {
            return false;
        }
        return RolesMatrix[action].some(r => user.roles.indexOf(r) >= 0);
    }

    getCurrentUser(): Observable<User> {
        if (!this.currentUser) {
            this.tokenService.getProfile().then((user: User) => {
                this.currentUser = user;
            });
        }
        return of(this.currentUser);
    }

}