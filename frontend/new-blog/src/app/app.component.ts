import { Component } from '@angular/core';
import { TokenService } from './auth/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'new-blog';

  constructor(
    private tokenService:TokenService,
  ) { }

  redirectToBlog() {
    window.location.href = "/"
  }

  redirectToPhotoAlbum() {
    window.location.href = "/album"
  }

  logout() {
    this.tokenService.logout();
  }
}
