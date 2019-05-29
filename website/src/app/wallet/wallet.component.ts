import { Component, OnInit } from '@angular/core';
import {WalletService} from "../Services/WalletService";
import {Wallet} from "../Model/Wallet";
import {Transaction} from "../Model/Transaction";
import {TransactionService} from "../Services/TransactionService";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  private dataLoaded: Promise<Boolean>;
  private wallet: Wallet;
  private sendedTransactions: Array<Transaction> = [];
  private receivedTransactions: Array<Transaction> = [];

  private trReceiverId: number;
  private trAmount: number;
  private trMessage: string;

  constructor(private walletService: WalletService,
              private transactionService: TransactionService) { }

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
          // Sort the transaction (received or sended)
          if(transaction.receiverId == this.wallet.id) {
            this.receivedTransactions.push(transaction)
          } else {
            this.sendedTransactions.push(transaction)
          }
        });
      }, (error) => {
        // When receive an error
        console.log("Error");
        this.dataLoaded = Promise.resolve(false)
      }, () => {
        // At the end of the load
        this.dataLoaded = Promise.resolve(true)
      })
  }

  private createTransaction() {
    this.transactionService.sendTransaction(this.trReceiverId, this.trAmount, this.trMessage)
      .subscribe((result: Transaction) => {
        console.log(result)
      }, (error) => {
        console.log(error)
      }, () => {
        window.location.reload(true)
      })
  }

}
