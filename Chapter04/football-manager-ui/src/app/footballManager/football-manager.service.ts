import { Injectable } from '@angular/core';
import { FootballManager } from './../footballManager/FootballManager';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class FootballManagerService {

  private apiUrl = 'http://localhost:8180/footballmanager';

  constructor(private http: Http) {
  }

  findAll(): Observable<FootballManager[]>  {
    return this.http.get(this.apiUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  findById(id: number): Observable<FootballManager> {
    return this.http.get(this.apiUrl + '/' + id)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Error'));
  }

  saveFootballManager(footballManager: FootballManager): Observable<FootballManager> {
    return this.http.post(this.apiUrl, footballManager).map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteFootballManagerById(id: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + id)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  updateFootballManager(footballManager: FootballManager): Observable<FootballManager> {
    return this.http.put(this.apiUrl + '/' + footballManager.id, footballManager)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
