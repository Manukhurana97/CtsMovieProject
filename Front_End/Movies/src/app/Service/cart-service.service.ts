import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserService} from './user.service';
import {SESSION_STORAGE, StorageService} from 'ngx-webstorage-service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CartServiceService {

  private CartbaseUrl = 'http://192.168.31.241:8762/Account/Cart/';
  private token: any;

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService, private http: HttpClient, private userservice: UserService,
              private router: Router) {
  }

  getToken() {
    return this.storage.get('auth_token');
  }

  add_to_Cart(id: number, quantity: number) {
    this.token = this.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.post(this.CartbaseUrl + `Add_to_cart/${id}/${quantity}`, '', {headers: header});
  }

  viewcart() {
    this.token = this.getToken();
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.post(this.CartbaseUrl + `ViewCart`, '', {headers: header});
  }
}
