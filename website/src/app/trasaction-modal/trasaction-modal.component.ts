import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-trasaction-modal',
  templateUrl: './trasaction-modal.component.html',
  styleUrls: ['./trasaction-modal.component.css'],
  template: `
    <div class="modal-header">
      <h2 class="modal-title">déchiffrement  transaction</h2>
    
    </div>
    <div class="modal-body">
      <h5>Identifiant: {{id}}</h5>
      <h5>Montant: {{amount}}</h5>
      <h5>Lanceur: {{sender}}</h5>
      <h5>Minée: {{mined}}</h5>
      <h5>Timestamp: {{timestamp}}</h5>
      <h5>Message: {{message}}</h5>
    </div>
  `
})
export class TrasactionModalComponent implements OnInit {
  id: string = localStorage.getItem('id');
  message: string = localStorage.getItem('message');
  amount: string = localStorage.getItem('amount');
  sender: string = localStorage.getItem('sender');
  mined: string = localStorage.getItem('mined');
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
