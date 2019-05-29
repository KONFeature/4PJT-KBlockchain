import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class TransactionService {
  constructor(private http: HttpClient) { }
  baseUrl: string = "http://10.17.16.208:8071/";
  public getTransactions() {
    return this.http.post(this.baseUrl+"blockchain/transactions", null)
  }

  public getTransactionPool() {
    return this.http.post(this.baseUrl+"blockchain/pool", null).subscribe({
      next(response) { console.log(response); },

    });
  }

  public sendTransaction(receiverId: number, amount: number, message: string) {
    let params = new HttpParams()
      .set("receiver", receiverId.toString())
      .set("amount", amount.toString())
      .set("message", message);
    return this.http.get(this.baseUrl + "wallet/publish", { params : params})
  }
}
