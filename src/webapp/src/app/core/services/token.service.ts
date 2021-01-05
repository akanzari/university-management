import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class TokenService {

    constructor(private keycloakService: KeycloakService) {
    }

    logout = async (): Promise<void> => {
        await this.keycloakService.logout();
    }

}