import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MoviesComponent} from './Component/movies/movies.component';
import { AdminMovieComponent } from './Component/admin-movie/admin-movie.component';
import { MoviedetailsComponent } from './Component/moviedetails/moviedetails.component';
import { SearchComponent } from './Component/search/search.component';
import { RegisterComponent } from './Component/register/register.component';
import { LoginComponent } from './Component/login/login.component';
import { AdminUserComponent } from './Component/admin-user/admin-user.component';
import { AddressComponent } from './Component/address/address.component';
import { AdminRegisterComponent } from './Component/admin-register/admin-register.component';
import { HeaderComponent } from './Component/header/header.component';
import { CartComponent } from './Component/cart/cart.component';


const route: Routes = [
  {path: '', component: MoviesComponent},
  {path: 'Admin_movies', component: AdminMovieComponent},
  {path: 'Moviedetails/:data/:id' , component: MoviedetailsComponent},
  {path: 'Search/:data' , component: SearchComponent},
  {path: 'Register' , component: RegisterComponent},
  {path: 'Login' , component: LoginComponent},
  {path: 'Admin_user' , component: AdminUserComponent},
  {path: 'AddressForm' , component: AddressComponent},
  {path: 'AdminRegister' , component: AdminRegisterComponent},
  {path: 'Header', component: HeaderComponent},
  {path: 'Cart', component: CartComponent}



];

@NgModule({
  imports: [RouterModule.forRoot(route)],
  exports: [RouterModule]
})

export class AppRoutingModule {

}
