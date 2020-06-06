import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {ActivatedRoute, Route, Router} from '@angular/router';
import {CartServiceService} from '../../Service/cart-service.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  movie: any = [];
  private quantity: number;

  constructor(private movieService: MovieService, private cartServiceService: CartServiceService, private router: Router) {
  }


  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    const response = this.movieService.getMovieList();
    response.subscribe((data) => this.movie = data);
  }

  getNameandid(data, id) {
    this.router.navigate(['Moviedetails', data, id]);
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
