import { Component, OnInit } from '@angular/core';
import { Transaction } from '../Model/Transaction'
@Component({
  selector: 'app-block-modal',
  templateUrl: './block-modal.component.html',
  styleUrls: ['./block-modal.component.css'],
  template: `
    <div class="modal-header">
      <h2 class="modal-title">Détail du bloc.</h2>
    
    </div>
    <div class="modal-body">
      <div>
        <h5>Identifiant: {{id}}</h5>
      <h5>Hash: {{hash}}</h5>
      <h5>Hash précédent: {{prevHash}}</h5>
      <h5>Nonce: {{nonce}}</h5>
      <h5>Timestamp: {{timestamp}}</h5>
      <h5>Transactions:</h5>

      <table id="table" class="table table-striped table-responsive table-sm col-lg-12" style="margin-top:0px; border-radius: 5px 5px;">
        <thead style="background-color:#00285c; border-top-left-radius: 40px 40px;">
          <tr>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px; max-width:3em;">Id</th>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px">Minée</th>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px">Message</th>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px">Id receveur</th>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px">Id lanceur</th>
            <th class="text-center" scope="col" style="border-top: 0px;color:white; padding-bottom:0px">Timestamp</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let t of transactions | paginate: { itemsPerPage: 10, currentPage: p }">
            <th class="text-center" scope="row">{{t.id}}</th>
            <td class="text-center">{{t.mined}}</td>
            <td class="text-center">{{t.message.substring(0,40)}}...</td>
            <td class="text-center">{{t.receiverId}}</td>
            <td class="text-center">{{t.senderId}}</td>
            <td class="text-center">{{t.timestamp}}</td>
          </tr>
        </tbody>
        <tfoot>
          <ul class="pagination col-lg-12">
            <pagination-controls (pageChange)="p = $event"></pagination-controls>
          </ul>
        </tfoot>
      </table>

      </div>      
    </div>
  `
})
export class BlockModalComponent implements OnInit { 
  id: string = localStorage.getItem('id');
  hash: string = localStorage.getItem('hash');
  prevHash: string = localStorage.getItem('prevHash');
  nonce: string = localStorage.getItem('nonce');
  transactions: Transaction[] = JSON.parse(localStorage.getItem('transactions'));
  timestamp: string = localStorage.getItem('timestamp');
  constructor(/*id: number, message: string, amount: number, sender: number, mined: boolean, timestamp: string*/) {
    //this.id = id;
    //this.message = message;
    //this.amount = amount;
    //this.sender = sender;
    //this.mined = mined;
    //this.timestamp = timestamp;
  }

  ngOnInit() {
  }

}
