import { Component, OnInit } from '@angular/core';
import { PostServiceService } from '../post-service/post-service.service';
import {Post, Comment, SendPost} from '../interfaces/intefaces';
import {MatSnackBar} from '@angular/material/snack-bar';
import { ViewChild } from '@angular/core';
import { TokenService } from '../auth/token.service';

@Component({
  selector: 'app-main-timeline',
  templateUrl: './main-timeline.component.html',
  styleUrls: ['./main-timeline.component.css']
})
export class MainTimelineComponent implements OnInit {
  @ViewChild('fileInput')
  fileInput:any;

  posts:Array<Post> = [];
  newPostImageBase64:string | ArrayBuffer;
  newPost:SendPost = {
    text: '',
  };
  userName:string;

  constructor(
    private postService: PostServiceService,
    private snackBar:MatSnackBar,
    private tokenService:TokenService,
  ) { }

  ngOnInit(): void {
    this.postService.getPosts().subscribe((data) => {
      this.postArrayFactory(data);
      console.log(this.posts);
    })
    this.tokenService.getUser().subscribe(data => {
      this.userName = data.user_name;
    })
  }

  postArrayFactory(data:any) {
    for(let content of data.content) {
      let post:Post = {
        code: content.code,
        date: new Date(content.date),
        owner: content.owner,
        text: content.text,
      }
      if(content.photo) {
        post.image = {
          code: content.photo.code,
        }
        this.getPostImage(post, content.photo.code);
      }
      this.posts.push(post);
    }
  }

  getPostImage(post:Post, code:number) {
    this.postService.getPhoto(code).subscribe((data) => {
      post.imageBase64 = data.photo;
    });
  }

  createPost(input:any) {
    let newPost:SendPost = {
      text: input.value
    }
    if(this.newPostImageBase64) {
      newPost.photoBase64 = this.newPostImageBase64.toString();
      newPost.photoBase64 = newPost.photoBase64.split('data:image/png;base64,')[1].replace(' ', '');
    }
    input.value = "";
    this.newPostImageBase64 = null;
    this.postService.sendPost(newPost).subscribe(data => {
      if(data.code) {
        this.refreshPosts();
      } else {
        let snackBarRef = this.snackBar.open('Ocorreu um erro!', 'OK', {
          duration: 3000
        });
      }
    });
  }

  refreshPosts() {
    this.posts = [];
    this.postService.getPosts().subscribe((data) => {
      this.postArrayFactory(data);
    })
  }

  onFileChanged(event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.newPostImageBase64 = reader.result;
    };
    this.fileInput.nativeElement.value = "";
  }

  removeNewPostImage() {
    this.newPostImageBase64 = null;
  }

  deletePost(code:number) {
    this.postService.delete(code).subscribe(data => {
        this.refreshPosts();
    });
  }
}
