import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
import { transactionViewModel} from '../Model/transactionViewModel'
@Injectable()
export class TransactionService {
  constructor(private http: HttpClient) { }
  baseUrl: string = localStorage.getItem('baseUrl');
  t: transactionViewModel;
  public getTransactions() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl+"/blockchain/transactions", header)
  }
  public getTransactionPool() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl+"/blockchain/pool", null).subscribe({
      next(response) { console.log(response); },

    });
  }
  public search(searchString: string) {
    let params = new HttpParams().set("criteria", searchString)
    return this.http.get(this.baseUrl + "/blockchain/search", { params: params });
  }

  public sendTransaction(receiverId: number, amount: number, message: string) {
    this.t = new transactionViewModel;
    this.t.amount = amount;
    this.t.receiver = receiverId;
    this.t.message = message;
    return this.http.post(this.baseUrl + "/wallet/publish", this.t);
  }

  public decryptTransaction(id: number) {
    let params = new HttpParams()
      .set("id", id.toString());
    return this.http.get(this.baseUrl + "/wallet/decrypt", { params: params, responseType: 'text' });
  }
}
