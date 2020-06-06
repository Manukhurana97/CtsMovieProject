import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../../Service/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  search = '';
  logintype: string;

  constructor(private router: Router, private userService: UserService) {
    this.logintype = 'home';
    if (this.userService.getAuthType() === null || this.userService.getAuthType() === 'undefined') {
      this.logintype = 'home';
    } else {
      if (this.userService.isAuthenticated() && (this.userService.getAuthType() === 'customer')) {
        this.logintype = 'customer';
      } else if (this.userService.isAuthenticated() && (this.userService.getAuthType() === 'admin')) {
        this.logintype = 'admin';
      }
    }
  }

  ngOnInit(): void {

  }

  onSearchClick(data) {
    this.router.navigate(['Search', data]);
  }

  onlogout() {
    this.logintype = 'home';
    this.userService.removeToken();
    this.router.navigate(['/']);
  }

}
