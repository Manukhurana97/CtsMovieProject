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
  message = String;

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
      userdata => {
        if (userdata.status === 200) {
          this.userService.storeToken(userdata.auth_TOKEN, userdata.usertype);
          this.route.navigate(['/']);
        } else if (userdata.status === 500) {
          this.userService.adminLogin(this.loginForm.value).subscribe(
            admindata => {
              if (admindata.status === 200) {
                this.userService.storeToken(admindata.auth_TOKEN, admindata.usertype);
                this.route.navigate(['/Admin_movies']);
              } else {
                this.route.navigate(['/Login']);
                this.error = true;
                this.message = admindata.message;
              }
            },
            error => console.log(error)
          );
        }
      },
      error1 => console.log(error1)
    );
  }

  Register() {
    this.route.navigate(['/Register']);
  }


}

