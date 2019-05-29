import { Component, OnInit } from '@angular/core';
import { f0 } from '@angular/core/src/render3';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {
  
  constructor() { }
  showSearchBar: boolean;
  ngOnInit() {
    this.showSearchBar = false;
  }

}
