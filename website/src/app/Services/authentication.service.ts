
//import { Injectable } from '@angular/core';
//import { HttpClient } from '@angular/common/http';
//import { BehaviorSubject, Observable } from 'rxjs';
//import { map } from 'rxjs/operators';
//import {
//  AuthService,
//  FacebookLoginProvider,
//  GoogleLoginProvider
//} from 'angular5-social-login';
//import { User } from '../model/user';

//@Injectable({ providedIn: 'root' })
//export class AuthenticationService {
//  private currentUserSubject: BehaviorSubject<User>;
//  public currentUser: Observable<User>;

//  constructor(private http: HttpClient, private socialAuthService: AuthService) {
//    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
//    this.currentUser = this.currentUserSubject.asObservable();
//  }

//  public get currentUserValue(): User {
//    return this.currentUserSubject.value;
//  }

//  public facebookLogin() {
//    let socialPlatformProvider = FacebookLoginProvider.PROVIDER_ID;
//    this.socialAuthService.signIn(socialPlatformProvider).then(
//      (userData) => {
//        //this will return user data from facebook. What you need is a user token which you will send it to the server
//        console.log(userData.name)
//      }
//    );
//  }
//  login(username: string, password: string) {
//    return this.http.post<any>(`http://localhost:8070/users/authenticate`, { username, password })
//      .pipe(map(user => {
//        // login successful if there's a jwt token in the response
//        if (user && user.token) {
//          // store user details and jwt token in local storage to keep user logged in between page refreshes
//          localStorage.setItem('currentUser', JSON.stringify(user));
//          this.currentUserSubject.next(user);
//        }

//        return user;
//      }));
//  }

//  logout() {
//    // remove user from local storage to log user out
//    localStorage.removeItem('currentUser');
//    this.currentUserSubject.next(null);
//  }
//}