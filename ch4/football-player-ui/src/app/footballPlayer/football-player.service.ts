import { Injectable } from '@angular/core';
import { FootballPlayer } from './../footballPlayer/FootballPlayer';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class FootballPlayerService {

  private apiUrl = 'http://localhost:8080/footballplayer';

  constructor(private http: Http) {
  }

  findAll(): Observable<FootballPlayer[]>  {
    return this.http.get(this.apiUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  findById(id: number): Observable<FootballPlayer> {
    return this.http.get(this.apiUrl + '/' + id)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Error'));
  }

  saveFootballPlayer(footballPlayer: FootballPlayer): Observable<FootballPlayer> {
    return this.http.post(this.apiUrl, footballPlayer).map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteFootballPlayerById(id: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + id)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  updateFootballPlayer(footballPlayer: FootballPlayer): Observable<FootballPlayer> {
    return this.http.put(this.apiUrl + '/' + footballPlayer.id, footballPlayer)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

}
