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
import { WalletService } from './services/WalletService';
import { NodeService } from './services/Nodeservice';
import { TransactionService } from './services/TransactionService';
import { BlocService } from './services/BlocService';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    HomeComponent,
    FetchDataComponent,
    SubscribeComponent
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
