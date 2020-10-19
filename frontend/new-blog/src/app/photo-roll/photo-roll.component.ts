import { Component, OnInit } from '@angular/core';
import { PhotoServiceService } from '../photo-service/photo-service.service';
import { TokenService } from '../auth/token.service';
import { PostServiceService } from '../post-service/post-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import {Photo} from '../interfaces/intefaces';

@Component({
  selector: 'app-photo-roll',
  templateUrl: './photo-roll.component.html',
  styleUrls: ['./photo-roll.component.css']
})
export class PhotoRollComponent implements OnInit {

  albumCode:number;
  photos:Array<Photo> = [];

  constructor(
    private photoService:PhotoServiceService,
    private tokenService:TokenService,
    private postService: PostServiceService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.route
      .queryParams
      .subscribe(params => {
        this.albumCode = +params['albumCode'] || 0;
        this.photoService.getPhotos(this.albumCode).subscribe(data => {
          this.photoFactory(data);
        });
      });
  }

  photoFactory(data) {
    for(let content of data) {
      let photo:Photo = {
        owner: content.owner,
        date: new Date(content.date),
        code: content.code,
      }
      this.getPhotoImage(photo, content.code);
      this.photos.push(photo);
    }
  }

  getPhotoImage(photo:Photo, code:number) {
    this.postService.getPhoto(code).subscribe((data) => {
      photo.photoBase64 = data.photo;
    });
  }
}
