import { KeycloakService } from 'keycloak-angular';
import { HttpClient } from '@angular/common/http';


function getPageInfo(http: HttpClient): Promise<any> {
  return new Promise(async (resolve, reject) => {
    let infoUrl = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/actuator/info';
    if (window.location.hostname === 'localhost') {
      infoUrl = window.location.protocol + '//' + window.location.hostname + ':8989/actuator/info';
    }
    http.get<any>(infoUrl).subscribe(list => { resolve(list) });
  })
}


export function initializer(keycloak: KeycloakService, http: HttpClient): () => Promise<any> {
  let configOptions: any;
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await getPageInfo(http).then(opt => configOptions = opt);
        await keycloak.init({
          config: {
            url: configOptions.environment.keycloak.url,
            realm: configOptions.environment.keycloak.realm,
            clientId: configOptions.environment.keycloak.clientId
          },
          initOptions: {
            onLoad: 'login-required'
          },
          enableBearerInterceptor: true
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };

}