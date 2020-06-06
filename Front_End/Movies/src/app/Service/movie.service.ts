import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseUrl = 'http://192.168.31.241:8762/Movies/';

  constructor(private http: HttpClient) {
  }

  createMovie(Movie: object): Observable<any> {
    return this.http.post(this.baseUrl + `Add`, Movie);
  }

  getMovieList(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  getMovieByid(id: number): Observable<any> {
    return this.http.get(this.baseUrl + `${id}`);
  }

  getMovieByName(name: string): Observable<any> {
    return this.http.get(this.baseUrl + 'Search=' + `${name}`);
  }

  getMovieByNameandid(name: string, id: number): Observable<any> {
    return this.http.get(this.baseUrl + `${name}/` + `${id}`);
  }

  deleteMovie(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + `Delete/${id}`, {responseType: 'text'});
  }

  update(Movie: object): Observable<any> {
    console.log(Movie);
    return this.http.put(this.baseUrl + `Update`, Movie);
  }

}
