import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../Service/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  addressForm: FormGroup;
  auth: string;
  showModel: boolean;
  // tslint:disable-next-line:ban-types
  address: Object = [];

  constructor(private fb: FormBuilder, private userService: UserService, private route: Router) {
  }

  ngOnInit(): void {
    this.auth = this.userService.getToken();

    this.addressForm = this.fb.group({
      address: ['Home', Validators.required],
      city: ['fbc', Validators.required],
      state: ['hr', Validators.required],
      country: ['India', Validators.required],
      zipcode: ['110000', Validators.required],
      phonenumber: ['1091', Validators.required]
    });
    this.UserAddressdata();
  }


  add_form() {
    this.showModel = true;
    console.log(this.showModel);
  }

  addAddress() {
    console.log(this.addressForm.value);
    this.userService.addAddress(this.auth, this.addressForm.value).subscribe(
      data => {
        console.log(data);
      },
      error => console.log(error)
    );
  }

  UserAddressdata() {
    this.userService.getAddress(this.auth).subscribe(
      data => {
        console.log(data);
        this.address = data;
      },
      error => console.log(error)
    );
  }

}
