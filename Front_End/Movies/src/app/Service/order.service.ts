import {Inject, Injectable} from '@angular/core';
import {SESSION_STORAGE, StorageService} from 'ngx-webstorage-service';
import {HttpClient} from '@angular/common/http';
import {UserService} from './user.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private CartbaseUrl = 'http://192.168.31.241:8762/Order/Orders/Placeorder';
  private token: any;

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService, private http: HttpClient, private userservice: UserService,
              private router: Router) {
  }
}
