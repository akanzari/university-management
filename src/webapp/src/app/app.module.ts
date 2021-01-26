import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { APP_INITIALIZER, LOCALE_ID, NgModule } from '@angular/core';
import localeFr from '@angular/common/locales/fr';
import { AppComponent } from './app.component';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { ConfigLoader, ConfigModule } from '@ngx-config/core';
import { ConfigHttpLoader } from '@ngx-config/http-loader';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { initializer } from './app-init';
import { CoreModule } from './core';
import { LayoutModule } from './layout';
import { TranslateModule } from '@ngx-translate/core';
import { AppRoutingModule } from './app.routes';
import { registerLocaleData } from '@angular/common';
import { HoùeModule } from './pages/home/home.module';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

export function configFactory(http: HttpClient): ConfigLoader {
  let infoUrl = window.location.protocol + '//' + window.location.hostname + ':8997/actuator/info';
  if ((window.location.port == null) || (window.location.port === '')) {
    infoUrl = window.location.protocol + '//' + window.location.hostname + '/actuator/info';
  }
  return new ConfigHttpLoader(http, infoUrl);
}

registerLocaleData(localeFr);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    LayoutModule,
    HoùeModule,
    CoreModule.forRoot(),
    TranslateModule.forRoot(),
    KeycloakAngularModule,
    ConfigModule.forRoot({
      provide: ConfigLoader,
      useFactory: configFactory,
      deps: [HttpClient]
    })
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    {
      provide: APP_INITIALIZER, useFactory: initializer, multi: true,
      deps: [KeycloakService, HttpClient]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
