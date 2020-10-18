import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainTimelineComponent} from './main-timeline/main-timeline.component';
import { LoginComponent } from './login/login.component';
import { RedirectComponent } from './redirect/redirect.component';

const routes: Routes = [
  { path: '', component: MainTimelineComponent },
  { path: 'login', component: LoginComponent },
  { path: 'oauth/redirect', component: RedirectComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
