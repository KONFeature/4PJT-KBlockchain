
import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { Transaction } from '../Model/Transaction';
import { AuthenticationService } from '../services/authentication.service';
import { TransactionService } from '../services/TransactionService';
import { Wallet } from "../Model/Wallet";
import { FormControl, FormGroup } from '@angular/forms'
@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {
  isExpanded = false;
  showSearchBar: boolean = true;
  isLogged: boolean;
  searchText = '';
  showSearchResult: boolean = false;
  tableInfo = [];
  private wallet: Wallet;
  private contactForm: FormGroup;
  private registerForm: FormGroup;
  constructor(private router: Router, private transactionService: TransactionService, private authService: AuthenticationService) {
    this.contactForm = this.createFormGroup()
    this.registerForm = this.createFormGroup()
  }

  public search(searchString: string) {
    this.transactionService.search(searchString).subscribe((res: Transaction[]) => {    
      this.tableInfo = res;
      console.log(res);
      this.showSearchResult = true;
    });
  }
  ngOnInit() {  
    this.wallet = JSON.parse(localStorage.getItem('currentUser'));
    if (this.wallet == null || this.wallet == undefined) {
      this.isLogged = false;
    }
    else {
      this.isLogged = true;
    }
  }

  createFormGroup() {
    return new FormGroup({      
      name: new FormControl(),
      email: new FormControl(),
    })
  }

  home() {
    this.router.navigate(['/'])
  }

  public facebookLogin() {
    this.authService.facebookLogin();
    this.router.navigate(['/wallet']);
    
  }

  public facebookRegister() {
    this.authService.facebookRegister();
    this.router.navigate(['/wallet']);
    
  }

  public googleLogin() {
    this.authService.googleLogin();
    this.router.navigate(['/wallet']);
   
  }

  public googleRegister() {
    this.authService.googleRegister();
    this.router.navigate(['/wallet']);
    
  }

  public logOut() {
    this.authService.logout();
    this.ngOnInit();
  }

  public logIn() {
    console.log(this.contactForm.value["name"]);
    this.authService.login(this.contactForm.value["email"], this.contactForm.value["name"]);
    

    // Do useful stuff with the gathered data

  }

  public register() {
    console.log(this.registerForm.value["name"]);
    this.authService.register(this.registerForm.value["email"], this.registerForm.value["name"]);
   
    // Do useful stuff with the gathered data
  }

  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }


}
