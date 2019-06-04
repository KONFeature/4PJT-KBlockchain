import { Component, OnInit } from '@angular/core';
import {WalletService} from "../services/WalletService";
import {Wallet} from "../Model/Wallet";
import {Transaction} from "../Model/Transaction";
import {TransactionService} from "../services/TransactionService";
import { Router } from "@angular/router";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TrasactionModalComponent } from '../trasaction-modal/trasaction-modal.component';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css'],
 
})
export class WalletComponent implements OnInit {

  private dataLoaded: Promise<Boolean>;
  private wallet: Wallet;
  private sendedTransactions: Array<Transaction> = [];
  private receivedTransactions: Array<Transaction> = [];

  private trReceiverId: number;
  private trAmount: number;
  private trMessage: string;
  private message: string = "yo";

  showAlert: boolean;

  constructor(private walletService: WalletService,
    private transactionService: TransactionService, private router: Router, public activeModal: NgbActiveModal, private modalService: NgbModal) { }

  ngOnInit() {
    // Fetch the loaded wallet on the blockchain
    this.wallet = JSON.parse(localStorage.getItem('currentUser'));
    if (this.wallet == null || this.wallet == undefined) {
      this.router.navigate(['/'])
    }
    else {
      this.loadTransactions();
    }
  }

   //Fetch the transaction associated to the wallet
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

  public createTransaction() {
    console.log(this.trReceiverId, this.trAmount, this.trMessage);
    this.transactionService.sendTransaction(this.trReceiverId, this.trAmount, this.trMessage) 
      .subscribe((result: Transaction) => {
       
        if (result == null) {
          this.showAlert = true;
        }
        else {
          window.location.reload(true)
        }
      });
     
  }

  private decryptTransaction(id: number, amount: number, sId: number, mined: boolean,timestamp:string) {

    localStorage.setItem('id', id.toString());
    localStorage.setItem('amount', amount.toString());    
    localStorage.setItem('sender', sId.toString());
    if (mined == true) {
      localStorage.setItem('mined', 'true');
    } else {
      localStorage.setItem('mined','false');
    }
    localStorage.setItem('timestamp', timestamp);
    this.transactionService.decryptTransaction(id).subscribe((result: string) => {
      localStorage.setItem('message', result);
      const modalRef = this.modalService.open(TrasactionModalComponent, { size: 'lg', backdrop: 'static' });
      modalRef.componentInstance.name = 'transaction';
    });

  }

}
