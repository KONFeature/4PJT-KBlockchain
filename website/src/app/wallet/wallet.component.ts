import { Component, OnInit } from '@angular/core';
import {WalletService} from "../Services/WalletService";
import {Wallet} from "../Model/Wallet";
import {Transaction} from "../Model/Transaction";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  private dataLoaded: Promise<Boolean>;
  private wallet: Wallet;
  private transactions: Array<Transaction> = [];

  constructor(private walletService: WalletService) { }

  ngOnInit() {
    // Fetch the loaded wallet on the blockchain
    this.walletService.walletStatus()
      .subscribe(( wallet: Wallet) => {
        this.wallet = wallet;
        this.loadTransactions()
      })
  }

  // Fetch the transaction associated to the wallet
  private loadTransactions() {
    this.walletService.walletTransaction()
      .subscribe((transactions: Transaction[]) => {
        // Add all the transactions retreived
        transactions.forEach((transaction: Transaction) => {
          this.transactions.push(transaction);
        });
      })
  }

}
