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
import { NodeService } from './services/NodeService';
import { TransactionService } from './services/TransactionService';
import { BlockService } from './services/BlockService';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { NgxPaginationModule } from 'ngx-pagination';
import { WalletComponent } from './wallet/wallet.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TrasactionModalComponent } from './trasaction-modal/trasaction-modal.component';
import { BlockModalComponent } from './block-modal/block-modal.component';

@NgModule({
  declarations: [

    AppComponent,
    NavMenuComponent,
    HomeComponent,
    FetchDataComponent,
    SubscribeComponent,
    WalletComponent,
    TrasactionModalComponent,
    BlockModalComponent
  ],
  entryComponents: [TrasactionModalComponent, BlockModalComponent],
  imports: [
    NgbModule.forRoot(),   
    SocialLoginModule, 
    BrowserModule.withServerTransition({ appId: 'ng-cli-universal' }),
    Ng2SearchPipeModule,
    HttpClientModule,
    ReactiveFormsModule,
     NgxPaginationModule, Ng2SearchPipeModule,  
    FormsModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent, pathMatch: 'full' },
      { path: 'fetch-data', component: FetchDataComponent },
      { path: 'subscribe', component: SubscribeComponent },
      { path: 'wallet', component: WalletComponent }
    ])
  ],
  exports: [TrasactionModalComponent, BlockModalComponent],
  providers: [
    NgbActiveModal,
    WalletService,
    NodeService,
    TransactionService,
    BlockService,
    {
      provide: AuthServiceConfig, useFactory: getAuthServiceConfigs,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {

    localStorage.setItem('baseUrl', "http://10.17.16.208:8070/");
  }
}
