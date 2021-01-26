import { Injectable } from '@angular/core';
import { ConfigService } from '@ngx-config/core';
import { KeycloakService } from 'keycloak-angular';
import { User } from '../models';
import { IAMService } from './iam.service';

@Injectable()
export class TokenService {

    private user: any;

    readonly domain: string;

    constructor(private keycloakService: KeycloakService,
        private readonly config: ConfigService,
        private iamService: IAMService) {
        this.domain = this.config.getSettings('environment.keycloak.redirectUri');
    }

    logout = async (): Promise<void> => {
        await this.keycloakService.logout(this.domain);
    }

    getProfile(): Promise<User> {
        return new Promise<User>((resolve, reject) => {
            if (this.isLoggedIn()) {
                this.getRoles().then((roles: string[]) => {
                    this.keycloakService.loadUserProfile().then((profile) => {
                        resolve(new User(profile.username, profile.lastName, profile.firstName, profile.email, roles))
                    }).catch(() => {
                        reject();
                    });
                })
            }
        });
    }

    async getRoles() {
        this.user = await this.iamService.getCurrentUser().toPromise();
        return this.user.roles;
    }

    isLoggedIn(): Promise<boolean> {
        return this.keycloakService.isLoggedIn();
    }


}