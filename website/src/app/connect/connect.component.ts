import { Component, OnInit } from '@angular/core';
import {
  AuthService as SocialAuthService,
  FacebookLoginProvider,
  GoogleLoginProvider
} from 'angular5-social-login';

import { WalletService } from './Services/WalletService';

@Component({
  selector: 'app-connect',
  templateUrl: './connect.component.html',
  styleUrls: ['./connect.component.css']
})
export class ConnectComponent implements OnInit {

  constructor( private socialAuthService: SocialAuthService ) {}

facebookLogin() {
    const socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        WalletService.createWallet(userData.name, userData.email, userData.idToken);
      }
    );
  }

  public signinWithGoogle () {
    const socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;

    this.socialAuthService.signIn(socialPlatformProvider)
      .then((userData) => {

        WalletService.createWallet(userData.name, userData.email, userData.idToken);
      });
  }




  ngOnInit() {
  }

}
