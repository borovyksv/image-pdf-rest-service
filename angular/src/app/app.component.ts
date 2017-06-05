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
export class AppComponent implements OnInit {

  constructor(private http:Http, private selectorsService:SelectorsService) {}

  // upload a file
  fileList:FileList;

  fileChange(event) {
    this.fileList = event.target.files;
  }
  uploadPdf(){
    if(this.fileList.length>0){
      let file: File = this.fileList[0];
      let formData:FormData = new FormData();

      formData.append('file', file, file.name);
      formData.append('info', new Blob([JSON.stringify(this.activeSelectors)],
        {
          type: "application/json"
        }));

      let headers = new Headers();
      headers.append('Accept', 'application/json');
      let options = new RequestOptions({ headers: headers });
      this.http.post("http://localhost:8080//documents/store",formData, options)
        .toPromise()
        .then(res => console.log(res.json()))
        .catch(error => console.log(error));
    }
    this.activeSelectors=[new RawSelector("", "")];
  }






  // Selectors form
  activeSelectors:RawSelector[]=[new RawSelector("", "")];

  selectors:Selector[];
  selector:Selector;
  rawSelector = new RawSelector('', '');

  onAddSubmit() {
    console.log(this.rawSelector);
    this.rawSelector.title = this.rawSelector.title.trim();
    this.rawSelector.options = this.rawSelector.options.trim();
    if (!this.rawSelector.title || !this.rawSelector.options) {
      console.log("RawSelector has emtry fields");
      return;
    }
    this.selectorsService.add(this.rawSelector);

  }

  // todo: remove this
  get diagnostic() { return JSON.stringify(this.activeSelectors); }

  changeActiveSelectorValue(title:string, value:string) {

    if (this.activeSelectors.some(x => x.title === title)) {
      for (let i in this.activeSelectors) {
        if (this.activeSelectors[i].title == title) {
          this.activeSelectors[i].options = value;
          break;
        }
      }
    } else this.activeSelectors.push(new RawSelector(title, value))

  }

  ngOnInit():void {
    this.getSelectors();
  }


  getSelectors():void {
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
  title:string;
  options:string;

  constructor(title:string, options:string) {
    this.title = title;
    this.options = options;
  }
}

