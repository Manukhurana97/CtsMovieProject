import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CartServiceService} from '../../Service/cart-service.service';


@Component({
  selector: 'app-moviedetails',
  templateUrl: './moviedetails.component.html',
  styleUrls: ['./moviedetails.component.css']
})
export class MoviedetailsComponent implements OnInit {

  movie: any = [];
  private quantity: number;

  constructor(private movieService: MovieService, private router: Router, private route: ActivatedRoute,
              private cartServiceService: CartServiceService) {
  }

  ngOnInit(): void {
    this.onload_data();
  }

  onload_data() {
    const name = this.route.snapshot.params.data;
    const id = this.route.snapshot.params.id;

    this.movieService.getMovieByNameandid(name, id).subscribe(
      data => {

        console.log();
        this.movie = data;
      },
      error => console.log(error)
    );
  }

  addtocart(id) {
    this.quantity = 1;
    this.cartServiceService.add_to_Cart(id, this.quantity).subscribe(
      data => {
        console.log(data);
      },
      error => console.error()
    );
  }

}
