
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from "@angular/router";
import { WalletService } from './/WalletService';
import { map } from 'rxjs/operators';
import {
  AuthService,
  FacebookLoginProvider,
  GoogleLoginProvider
} from 'angular5-social-login';
import { Wallet } from '../Model/Wallet';
import { walletViewModel } from '../Model/walletViewModel';


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<Wallet>;
  public currentUser: Observable<Wallet>;

  constructor(private http: HttpClient, private socialAuthService: AuthService, private walletService: WalletService, private route: Router) {
    this.currentUserSubject = new BehaviorSubject<Wallet>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public facebookLogin() {
    let socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        this.walletService.walletLoad(userData.email).subscribe((wallet: Wallet) => {
          console.log(wallet.token);
          localStorage.setItem('currentUser', JSON.stringify(wallet));
          window.location.reload(true);
        });

      }
    );
  }

  public facebookRegister() {
    let socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        console.log("facebook" + userData.token)
        this.walletService.createWallet(userData.name, userData.email, userData.token).subscribe((wallet: Wallet) => {
          console.log(wallet.token);
          localStorage.setItem('currentUser', JSON.stringify(wallet));
          window.location.reload(true);
        });
      }
    );
  }

  public googleLogin() {
    let socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;
    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        this.walletService.walletLoad(userData.email).subscribe((wallet: Wallet) => {
          console.log(wallet.token);
          localStorage.setItem('currentUser', JSON.stringify(wallet));
          window.location.reload(true);
        });
        //this will return user data from facebook. What you need is a user token which you will send it to the server
        console.log(userData.name)
      }
    );
  }

  public googleRegister() {
    let socialPlatformProvider = GoogleLoginProvider.PROVIDER_ID;
    this.socialAuthService.signIn(socialPlatformProvider).then(
      (userData) => {
        console.log("google" + userData.token)
        this.walletService.createWallet(userData.name, userData.email, userData.token).subscribe((wallet: Wallet) => {
          console.log(wallet.token);
          localStorage.setItem('currentUser', JSON.stringify(wallet));
          window.location.reload(true);
        });
        console.log(userData.name)
      }
    );
  }

  login(mail?: string, name?: string) {
    if (mail != null || mail != undefined) {
      this.walletService.walletLoad(mail).subscribe((wallet: Wallet) => {
        console.log(wallet.token);
        localStorage.setItem('currentUser', JSON.stringify(wallet));
        window.location.reload(true);
      });
    } else {
      this.walletService.walletLoad(name).subscribe((wallet: Wallet) => {
        console.log(wallet.token);
        localStorage.setItem('currentUser', JSON.stringify(wallet));
        window.location.reload(true);
      });
    }

  }
  register(mail: string, name: string) {
    this.walletService.createWallet(name, mail, null).subscribe((wallet: Wallet) => {
      localStorage.setItem('currentUser', JSON.stringify(wallet));
      window.location.reload(true);
    });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.route.navigate(['/']);
  }
}
