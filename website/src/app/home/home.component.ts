

import { Component, OnInit, ViewChild, HostListener, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { from } from 'rxjs';
import { TransactionService } from '../services/TransactionService';
import { Transaction } from '../Model/Transaction';
import { Block } from '../Model/Block';
import { BlockService } from '../services/BlockService';
import { NodeService } from '../services/NodeService';
import { WalletService } from '../services/WalletService';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BlockModalComponent } from '../block-modal/block-modal.component';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})


export class HomeComponent {
  private tableInfo: Transaction[] = [];
  public col: string[];
  public showTransaction: boolean = true;
  public showBlock: boolean = false;
  public showNode: boolean = false;
  public showWallet: boolean = false;
  public totalTransaction: number;
  public totalTransactionMined: number;
  public totalUsers: number;
  public totalBlocks: number;
  public totalTransactionUnMined: number;
  constructor(private transactionService: TransactionService, private blockService: BlockService,
    private nodeService: NodeService, private walletService: WalletService,
    public activeModal: NgbActiveModal, private modalService: NgbModal) {

  }
  ngOnInit() {
    this.totalBlocks = 0;
    this.totalTransactionMined = 0;
    this.totalTransaction = 0;
    this.totalTransactionUnMined = 0;
    this.totalUsers = 0;
    this.transactionService.getTransactions().subscribe((res: Transaction[]) => {
      res.forEach(t => {
        this.totalTransaction += 1
        if (t.mined == true) {
          this.totalTransactionMined += 1;
        } else {
          this.totalTransactionUnMined += 1;
        }
        this.tableInfo = res;
      });

    });
    this.blockService.getBlock().subscribe((res: any[]) => {
      res.forEach(b => { this.totalBlocks += 1 });
    });
    this.walletService.walletCount().subscribe((res: any[]) => {
      res.forEach(w => { this.totalUsers += 1 });
    });

  }

  public getTransactions() {
    
    this.transactionService.getTransactions().subscribe((res: Transaction[]) => {
      console.log(res);
      this.tableInfo = res;
      this.showTransaction = true;
      this.showBlock = false;
      this.showNode = false;
      this.showWallet = false;
    });
  }
  public getBlocks() {
    this.showTransaction = false;
    this.showBlock = true;
    this.showNode = false;
    this.showWallet = false;
    this.blockService.getBlock().subscribe((res: any[]) => {
       console.log(res);
      this.tableInfo = res;     
     });
  }
    
  public getNodes() {
    this.nodeService.getNode().subscribe((res: any[]) => {
      console.log(res);
      this.tableInfo = res;
      this.showTransaction = false;
      this.showBlock = false;
      this.showNode = true;
      this.showWallet = false;
    });
  }

  public getWallets() {
    this.walletService.walletCount().subscribe((res: any[]) => {
      console.log(res);
      this.tableInfo = res;
      this.showTransaction = false;
      this.showBlock = false;
      this.showNode = false;
      this.showWallet = true;
    });
  }

  public blockDetail(id: number) {
    this.blockService.getBlockDetail(id).subscribe((block: Block) => {
      localStorage.setItem('id', block.id.toString());
      localStorage.setItem('hash', block.hash);
      localStorage.setItem('prevHash', block.prevHash);
      localStorage.setItem('nonce', block.nonce.toString());
      localStorage.setItem('timestamp', block.timestamp);
      localStorage.setItem('transactions', JSON.stringify(block.transactions));      
      const modalRef = this.modalService.open(BlockModalComponent, { size: 'lg' });
      modalRef.componentInstance.name = 'transaction';
    });

  }

}
