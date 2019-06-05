import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class BlockService {

  constructor(private http: HttpClient) { }
  baseUrl: string = localStorage.getItem('baseUrl');
  public getBlock() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl + "/blockchain/blocks", header);
  }

  public getBlockDetail(id: number) {
    let params = new HttpParams().set("id", id.toString())
    return this.http.post(this.baseUrl + "/blockchain/block?id="+id,null);
  }


}
