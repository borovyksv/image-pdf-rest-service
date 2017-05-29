import {Component, OnInit} from '@angular/core';
import {SelectorsService} from "./selectors.service";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SelectorsService]
})
export class AppComponent implements OnInit{

  selectors: Selector[];
  selector: Selector;

  // upload a file
  




  // Add selector form
  rawSelector = new RawSelector('', '');

  onAddSubmit(){
    console.log(this.rawSelector);
    this.rawSelector.title = this.rawSelector.title.trim();
    this.rawSelector.options = this.rawSelector.options.trim();
    if (!this.rawSelector.title||!this.rawSelector.options) {
      console.log("RawSelector has emtry fields");
      return; }
    this.selectorsService.add(this.rawSelector);
  }



  constructor(private selectorsService: SelectorsService) {}


  ngOnInit():void {
    this.getSelectors();
  }


  getSelectors(): void{
    this.selectorsService.getSelectors().then(selectors => this.selectors = selectors)
  }
}

export class Selector {
  title:string;
  options:string[];

  constructor(title:string, options:string[]) {
      this.title = title;
      this.options = options;
      }
}

export class RawSelector {
  title: string;
  options: string;

  constructor(title:string, options:string) {
      this.title = title;
      this.options = options;
      }
}

