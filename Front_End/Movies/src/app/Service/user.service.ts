import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHandler, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../Model/user.model';
import {SESSION_STORAGE, StorageService} from 'ngx-webstorage-service';
import {Address} from '../Model/address.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://192.168.31.241:8762/Movies/';
  private UserbaseUrl = 'http://192.168.31.241:8762/Account/User/';
  private AddressbaseUrl = 'http://192.168.31.241:8762/Address/';

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService, private http: HttpClient) {
  }

  RegisterUser(user: User): Observable<any> {
    return this.http.post(this.UserbaseUrl + `UserRegister`, user);
  }

  Registeradmin(user: User): Observable<any> {
    return this.http.post(this.UserbaseUrl + `AdminRegister`, user);
  }

  userLogin(user: User): Observable<any> {
    return this.http.post(this.UserbaseUrl + `UserLogin`, user);
  }

  adminLogin(user: User): Observable<any> {
    return this.http.post(this.UserbaseUrl + `AdminLogin`, user);
  }

  getUserList(): Observable<any> {
    return this.http.get(this.UserbaseUrl + `All_User`);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(this.UserbaseUrl + 'Delete/' + `${id}`);
  }


  public isAuthenticated(): boolean {
    // tslint:disable-next-line:triple-equals
    if (this.getToken() != 'undefined' && this.getToken() != null) {
      return true;
    }
    return false;
  }

  // tslint:disable-next-line:variable-name
  storeToken(token: string, auth_type: string) {
    this.storage.set('auth_token', token);
    this.storage.set('auth_type', auth_type);
  }

  getToken() {
    return this.storage.get('auth_token');
  }

  getAuthType(): string {
    if (this.storage.get('auth_type') !== null) {
      return this.storage.get('auth_type');
    }
    return null;
  }

  removeToken() {
    this.storage.remove('auth_type');
    return this.storage.remove('auth_token');
  }

  addAddress(auth: string, add: Address) {
    const header = new HttpHeaders().set('Authentication', auth);
    return this.http.post(this.AddressbaseUrl + 'AddAddress', add, {headers: header});
  }

  getAddress(auth: string) {
    const header = new HttpHeaders().set('Authentication', auth);
    return this.http.post(this.AddressbaseUrl, '', {headers: header});
  }
}
