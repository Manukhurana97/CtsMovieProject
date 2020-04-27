import {Component, OnInit} from '@angular/core';
import {UserService} from '../../Service/user.service';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../Model/user.model';

@Component({
  selector: 'app-admin-register',
  templateUrl: './admin-register.component.html',
  styleUrls: ['./admin-register.component.css']
})
export class AdminRegisterComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private userservice: UserService, private route: Router, private formBuilder: FormBuilder) {
  }


  ngOnInit() {
    if (this.userservice.isAuthenticated()) {
      console.log(this.userservice.isAuthenticated());
      this.route.navigate(['/']);
    }
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      userPermission: ['admin', Validators.required]
    });

  }

  Register() {
    if (this.userservice.isAuthenticated()) {
      this.route.navigate(['/']);
    }
    this.userservice.Registeradmin(this.registerForm.value).subscribe(
      data => {
        console.log(data);
        this.route.navigate(['/Login']);
      },
      error => console.log(error)
    );
  }


}
