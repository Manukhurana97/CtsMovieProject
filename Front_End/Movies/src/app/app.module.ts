import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './Component/header/header.component';
import { MoviesComponent } from './Component/movies/movies.component';
import { HttpClientModule } from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import { AdminMovieComponent } from './Component/admin-movie/admin-movie.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MoviedetailsComponent } from './Component/moviedetails/moviedetails.component';
import { SearchComponent } from './Component/search/search.component';
import { RegisterComponent } from './Component/register/register.component';
import { LoginComponent } from './Component/login/login.component';
import { AdminUserComponent } from './Component/admin-user/admin-user.component';
import { AddressComponent } from './Component/address/address.component';
import { AdminRegisterComponent } from './Component/admin-register/admin-register.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MoviesComponent,
    AdminMovieComponent,
    MoviedetailsComponent,
    SearchComponent,
    RegisterComponent,
    LoginComponent,
    AdminUserComponent,
    AddressComponent,
    AdminRegisterComponent,

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
