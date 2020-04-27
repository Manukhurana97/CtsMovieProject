import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-moviedetails',
  templateUrl: './moviedetails.component.html',
  styleUrls: ['./moviedetails.component.css']
})
export class MoviedetailsComponent implements OnInit {

  movie: any = [];


  constructor(private movieService: MovieService, private router: Router, private route: ActivatedRoute) {
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

}
