import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {ActivatedRoute, Route, Router} from '@angular/router';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent implements OnInit {

  movie: any = [];

  constructor(private movieService: MovieService, private router: Router) {
  }


  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    const response = this.movieService.getMovieList();
    response.subscribe((data) => this.movie = data);
  }

  getName(data, id) {
    this.router.navigate(['Moviedetails', data, id]);

  }

}
