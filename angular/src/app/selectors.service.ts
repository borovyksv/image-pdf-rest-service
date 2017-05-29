import {Injectable} from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {Selector, RawSelector} from "./app.component";

@Injectable()
export class SelectorsService{

  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http){
    console.log('Document service initialized...')
  }

  getSelectors(): Promise<Selector[]>{
    return this.http.get('http://localhost:8080/selectors')
      .toPromise()
      .then(response=>response.json() as Selector[])
      .catch(this.handleError);
  }


  add(rawSelector:RawSelector): void {
    this.http
      .post("http://localhost:8080/selectors", JSON.stringify(rawSelector), {headers: this.headers})
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}


