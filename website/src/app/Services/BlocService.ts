import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class BlocService {

  constructor(private http: HttpClient) { }
  baseUrl: string = "http://10.17.16.208:8071/"
  public getBlock() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.post(this.baseUrl+"blockchain/blocks", header).subscribe({
      next(response) { console.log(response); },
      error(err) { console.error('Error: ' + err); },
      complete() { console.log('Completed'); }
    });
  }

  public getBlockDetail(id: number) {
    let params = new HttpParams().set("id", id.toString())
    return this.http.get(this.baseUrl+"blockchain/blocks", { params: params });
  }

}
