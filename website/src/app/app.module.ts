import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { HomeComponent } from './home/home.component';
import { FetchDataComponent } from './fetch-data/fetch-data.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { SocialLoginModule, AuthServiceConfig } from 'angular5-social-login';
import { FacebookLoginProvider } from 'angular5-social-login';
import { getAuthServiceConfigs } from './socialloginConfig';
import { WalletService } from "./Services/WalletService";
import { NodeService } from './Services/NodeService';
import { TransactionService } from './Services/TransactionService';
import { BlocService } from './Services/BlocService';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {WalletComponent} from "./wallet/wallet.component";

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    HomeComponent,
    FetchDataComponent,
    SubscribeComponent,
    WalletComponent
  ],
  imports: [
    SocialLoginModule,
    BrowserModule.withServerTransition({ appId: 'ng-cli-universal' }),
    Ng2SearchPipeModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent, pathMatch: 'full' },
      { path: 'fetch-data', component: FetchDataComponent },
      { path: 'subscribe', component: SubscribeComponent },
      { path: 'wallet', component: WalletComponent },

    ])
  ],
  providers: [
    WalletService,
    NodeService,
    TransactionService,
    BlocService,
    {
      provide: AuthServiceConfig, useFactory: getAuthServiceConfigs
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
