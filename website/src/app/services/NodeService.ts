import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Component } from '@angular/core';
@Injectable()
export class NodeService {
  constructor(private http: HttpClient) { }
  baseUrl: string = localStorage.getItem('baseUrl');
  public getNode() {
    let header = new HttpHeaders().set("Access-Control-Allow-Origin", "*")
    return this.http.get(this.baseUrl + "/network/nodes", { headers: header });
  }
}
