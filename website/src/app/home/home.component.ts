import { Component, OnInit, ViewChild, HostListener, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { from } from 'rxjs';
import { TransactionService } from '../Services/TransactionService';
import { Transaction } from '../Model/Transaction';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})

export class HomeComponent {
  private tableInfo: Transaction[] = [] ;
  constructor(private transactionService: TransactionService) {
  }
  ngOnInit() {
    this.transactionService.getTransactions().subscribe((res: Transaction[]) => {
      console.log(res);
      this.tableInfo = res;
    });
  }

  public getTransactions() {
    this.transactionService.getTransactions().subscribe((res: Transaction[]) => {
      console.log(res);
      this.tableInfo = res;
    });

  }





}
