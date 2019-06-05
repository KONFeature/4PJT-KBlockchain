
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
import { User } from '../Model/User';
@Injectable()
export class WalletService {
  baseUrl: string = localStorage.getItem('baseUrl');
  constructor(private http: HttpClient) { }

  public createWallet(name: string, mail: string, token: string) {
    let user: User = new User();
    user.name = name;
    user.mail = mail;
    user.token = token;
    //let params = new HttpParams().set("name", name).set("mail", mail).set("token", token)
    return this.http.post<any>(this.baseUrl + "/wallet/create", user);
  }
  public walletLoad(identifier: string) {
    let params = new HttpParams().set("identifier", identifier);
    return this.http.get(this.baseUrl + "/wallet/load", {params: params});
  }
  public walletStatus() {
    return this.http.get(this.baseUrl +"/wallet/status");
  }
  public walletCount() {
    return this.http.post(this.baseUrl + "/blockchain/wallets",null);
  }
  public walletTransaction() {
    return this.http.get(this.baseUrl + "/wallet/transactions")
  }
  public walletDecrypt(id: number) {
    return this.http.post(this.baseUrl + "/wallet/decrypt", id);
  }

}
