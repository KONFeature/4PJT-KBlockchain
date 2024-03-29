//import { Component, OnInit } from '@angular/core';
//import { Injectable } from '@angular/core';
//import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
//import { AuthenticationService } from '@/_services';
//@Component({
//  selector: 'app-auth-guard'
//})

//@Injectable({ providedIn: 'root' })
//export class AuthGuardComponent implements OnInit {

//  constructor(
//    private router: Router,
//    private authenticationService: AuthenticationService
//  ) { }

//  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
//    const currentUser = this.authenticationService.currentUserValue;
//    if (currentUser) {
//      // logged in so return true
//      return true;
//    }

//    // not logged in so redirect to login page with the return url
//    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
//    return false;
//  }

//}
