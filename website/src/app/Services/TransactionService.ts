import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class TransactionService {
  constructor(private http: HttpClient) { }
  baseUrl: string = "http://10.17.16.208:8071/";
  public getTransactions() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl+"blockchain/transactions", header)
  }

  public getTransactionPool() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl+"blockchain/pool", null).subscribe({
      next(response) { console.log(response); },

    });
  }
}
