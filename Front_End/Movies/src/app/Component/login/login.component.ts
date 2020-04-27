import {Component, OnInit} from '@angular/core';
import {UserService} from '../../Service/user.service';
import {Router} from '@angular/router';
import {FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm: any;
  error = false;

  constructor(private userService: UserService, private route: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    if (this.userService.isAuthenticated()) {
      console.log(this.userService.isAuthenticated());
      this.route.navigate(['/']);
    }

    this.loginForm = this.formBuilder.group({
      email: ['manu@gmail.com', Validators.required],
      password: ['manu@123', Validators.required]
    });
  }

  login(): void {
    this.userService.userLogin(this.loginForm.value).subscribe(
      data => {
        if (data.status === '200') {
          this.userService.storeToken(data.auth_TOKEN, 'customer');
          this.route.navigate(['/']);
        } else if (data.status === '500') {
          this.userService.adminLogin(this.loginForm.value).subscribe(
            data1 => {
              if (data1.status === '200') {
                this.userService.storeToken(data1.auth_TOKEN, 'admin');
                this.route.navigate(['/Admin_movies']);
              } else {
                this.route.navigate(['/Login']);
                this.error = false;
              }
            },
            error => console.log(error)
          );
        }
      },
      error1 => console.log(error1)
    );
  }
}
