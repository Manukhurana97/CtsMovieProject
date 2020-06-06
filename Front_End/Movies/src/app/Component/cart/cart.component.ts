import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {CartServiceService} from '../../Service/cart-service.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cartdata: any = [];
   total = 0;
  count = 0;

  constructor(private movieService: MovieService, private cartServiceService: CartServiceService, private router: Router) {
  }

  ngOnInit(): void {
    this.cartMovies();
  }

  cartMovies() {
    this.cartServiceService.viewcart().subscribe(
      data => {
        this.cartdata = data;
        console.log(this.cartdata);
        this.sum(this.cartdata);
      },
      error => console.error(error)
    );
  }

  sum(cartdata) {
    for (const data of cartdata.cartItems)
    {
      this.total += data.amount;
      this.count += 1;
    }
  }


}
