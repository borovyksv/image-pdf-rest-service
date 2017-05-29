import {Component, OnInit} from '@angular/core';
import {SelectorsService} from "./selectors.service";
import {Observable} from "rxjs/Observable";
import {Http, RequestOptions, Headers} from "@angular/http";
import 'rxjs/add/operator/toPromise';




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
  fileChange(event) {
    let fileList: FileList = event.target.files;
    if(fileList.length > 0) {
      let file: File = fileList[0];
      let formData = new FormData();
      formData.append('file', file);
      // let headers = new Headers();
      // headers.append('Content-Type', 'multipart/form-data');
      // headers.append('Accept', 'application/json');
      // headers.append('boundary', 'some text');

      // let options = new RequestOptions({ headers: headers });
      this.http.post(`http://localhost:8080/documents/store`, formData)
        .toPromise()
        .then(res => res.json())
        .catch(error => console.log(error));
    }
  }




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



  constructor(private http: Http, private selectorsService: SelectorsService) {}


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

