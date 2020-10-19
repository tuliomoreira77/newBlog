import { Component, OnInit } from '@angular/core';
import { PhotoServiceService } from '../photo-service/photo-service.service';
import { TokenService } from '../auth/token.service';
import {PhotoAlbum} from '../interfaces/intefaces';
import { PostServiceService } from '../post-service/post-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-photo-album',
  templateUrl: './photo-album.component.html',
  styleUrls: ['./photo-album.component.css']
})
export class PhotoAlbumComponent implements OnInit {

  userName:string;
  albums:Array<PhotoAlbum> = [];

  constructor(
    private photoService:PhotoServiceService,
    private tokenService:TokenService,
    private postService: PostServiceService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.tokenService.getUser().subscribe(data => {
      this.userName = data.user_name;
    });
    this.getUserAlbums();
  }

  getUserAlbums() {
    this.photoService.getAlbums(this.userName).subscribe(data => {
      this.photoAlbumFactory(data);
    });
  }

  photoAlbumFactory(data:any) {
    for(let content of data) {
      let album:PhotoAlbum = {
        name: content.name,
        code: content.code,
        owner: content.owner,
        photoNumber: content.photos.length,
      }
      if(content.photos.length > 0) {
        this.getAlbumImage(album, content.photos[0].code);
      }
      this.albums.push(album);
    }
  }

  getAlbumImage(album:PhotoAlbum, code:number) {
    this.postService.getPhoto(code).subscribe((data) => {
      album.mainPhotoBase64 = data.photo;
    });
  }

  goToPhotos(albumCode:number) {
    this.router.navigate(['/photos'], { queryParams: { albumCode: albumCode } });
  }
}
