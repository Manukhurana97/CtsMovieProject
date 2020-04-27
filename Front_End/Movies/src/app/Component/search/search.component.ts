import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  movies: any = [];

  constructor(private movieService: MovieService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.onload();
  }

  onload() {
    const name = this.route.snapshot.params.data;
    this.movieService.getMovieByName(name).subscribe(
      data => {
        console.log(data);
        this.movies = data;
      },
      error => console.log(error)
    );
  }

  getName(data, id) {
    this.router.navigate(['Moviedetails', data, id]);

  }

}
