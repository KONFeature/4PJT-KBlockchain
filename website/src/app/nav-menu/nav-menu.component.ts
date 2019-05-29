
import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { from } from 'rxjs';
import { WalletService } from '../Services/WalletService';
import { NodeService } from '../Services/NodeService';
import { TransactionService } from '../Services/TransactionService';
import { BlocService } from '../Services/BlocService';
import {
  AuthService,
  FacebookLoginProvider,
  GoogleLoginProvider
} from 'angular5-social-login';
import { Console } from '@angular/core/src/console';
@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {
  isExpanded = false;
  showSearchBar: boolean = true;
  searchText = '';
  blocks = [
    { id: 11, name: 'XYBDD', country: 'India' },
    { id: 12, name: 'BNbnb', country: 'USA' },
    { id: 13, name: 'Bombasto', country: 'UK' },
    { id: 14, name: 'CelJjkaaeritas', country: 'Canada' },
    { id: 15, name: 'neezhfkukf', country: 'Russia' },
    { id: 16, name: 'Terncehfzeuf', country: 'China' },
    { id: 17, name: 'hzfjfbhebfbef', country: 'Germany' },
    { id: 18, name: 'hjgjyvhsdku&z', country: 'Hong Kong' },
    { id: 19, name: 'hbzh,dayjg', country: 'South Africa' },
    { id: 20, name: 'ppfkeofjeiafai', country: 'Sri Lanka' }
  ];

  constructor(private router: Router, private socialAuthService: AuthService, private http: HttpClient,
    private walletService: WalletService, private nodeService: NodeService, private transactionService: TransactionService, private blocService: BlocService) { }

    subscribe() {
    this.showSearchBar = false;
    this.router.navigate(['/subscribe']);

  }
    connect() {
    this.showSearchBar = false;
    this.router.navigate(['/connect']);
  }
  home() {

    this.nodeService.getNode().subscribe(res => console.log("test" + res));
    this.walletService.walletLoad()
    this.nodeService.getNode()
    this.transactionService.getTransactionPool()
    this.walletService.walletStatus();
    this.transactionService.getTransactions();
    //this.walletService.createWallet("t", "test1@test", "125672fegeg55454dege84");
    this.showSearchBar = true;
    this.router.navigate(['/'])
  }
  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }


}
