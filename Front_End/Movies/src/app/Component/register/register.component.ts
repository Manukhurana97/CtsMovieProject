import {Component, OnInit} from '@angular/core';
import {UserService} from '../../Service/user.service';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../Model/user.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private userservice: UserService, private route: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    if (this.userservice.isAuthenticated()) {
      console.log(this.userservice.isAuthenticated());
      this.route.navigate(['/']);
    }
    this.registerForm = this.fb.group({
      name: ['manu', Validators.required],
      email: ['manu@gmail.com', Validators.required],
      password: ['manu@123', Validators.required],
      userPermission: ['customer', Validators.required]
    });

  }

  Register() {
    if (this.userservice.isAuthenticated()) {
      this.route.navigate(['/']);
    }
    this.userservice.RegisterUser(this.registerForm.value).subscribe(
      data => {
        console.log(data);
        this.route.navigate(['/Login']);
      },
      error => console.log(error)
    );
  }

  Login() {
    this.route.navigate(['/Login']);
  }
}
