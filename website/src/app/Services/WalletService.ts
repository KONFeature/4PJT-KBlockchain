import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class WalletService {
  baseUrl: string = "http://10.17.16.208:8071/";
  constructor(private http: HttpClient) { }

  public createWallet(name: string, mail: string, token: string) {
    let params = new HttpParams().set("name", name).set("mail", mail).set("token", token)
    return this.http.post<any>(this.baseUrl +"wallet/create", { name: name, mail: mail, token: token });
  }

  public walletLoad() {
    return this.http.get(this.baseUrl +"wallet/load");
  }

  public walletStatus() {
    return this.http.get(this.baseUrl +"wallet/status");
  }

  public walletTransaction() {
    return this.http.get(this.baseUrl + "wallet/transactions")
  }
}
