import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../Service/movie.service';
import {MoviesComponent} from '../movies/movies.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MovieModel} from '../../Model/movie.model';
import {HttpClient} from '@angular/common/http';
import {UserService} from '../../Service/user.service';


@Component({
  selector: 'app-admin-movie',
  templateUrl: './admin-movie.component.html',
  styleUrls: ['./admin-movie.component.css']
})
export class AdminMovieComponent implements OnInit {

  movies: any = [];
  movie: any = [];
  showModel: boolean;
  showModel1: boolean;
  add = false;
  img = null;
  fileToUpload: File = null;
  addmovie: MovieModel = new MovieModel();
  private imageUrl: any;


  // tslint:disable-next-line:max-line-length
  constructor(private http: HttpClient, private userservice: UserService, private movieService: MovieService, private router: Router, private formBuilder: FormBuilder) {
  }


  ngOnInit(): void {
    if (this.userservice.isAuthenticated() && this.userservice.getAuthType() === 'admin') {
      this.reloadData();
    } else {
      console.log(this.userservice.getAuthType());
      this.router.navigate(['']);
    }
  }

  onSelectFile(file: FileList) {
    this.fileToUpload = file.item(0);
    const reader = new FileReader();
    reader.onload = (event: any) => {
      this.imageUrl = event.target.result;
    };
    console.log(this.fileToUpload);
    reader.readAsDataURL(this.fileToUpload);
  }

  add_form() {
    this.showModel = true;
  }

  reloadData() {
    const response = this.movieService.getMovieList();
    response.subscribe((data) => this.movies = data);
  }

  onRegister() {
    this.movieService.createMovie(this.addmovie).subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error)
    );
    this.showModel = false;
  }


  onUpdategetdata(name, id) {
    this.showModel1 = true;
    this.movieService.getMovieByNameandid(name, id).subscribe(
      data => {
        console.log(data);
        this.movie = data;
      },
      error => console.log(error)
    );
  }

  updatedata() {
    this.movieService.update(this.movie).subscribe(
      data => {
        this.movie = new MovieModel();
      },
      error => console.log(error));
    this.showModel1 = false;
    this.reloadData();
  }

  onDelete(id) {
    this.movieService.deleteMovie(id).subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error)
    );
  }

}
