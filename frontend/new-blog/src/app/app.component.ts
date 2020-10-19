import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'new-blog';
  redirectToBlog() {
    window.location.href = "/"
  }

  redirectToPhotoAlbum() {
    window.location.href = "/album"
  }
}
