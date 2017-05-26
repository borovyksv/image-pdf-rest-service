import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class SelectorsService{

  constructor(private http: Http){
    console.log('Document service initialized...')
  }

  getSelectors(){
    //noinspection TypeScriptValidateTypes
    return this.http.get('http://localhost:8080/selectors')
      .map((res:Response)=>res.json());
  } 
}


