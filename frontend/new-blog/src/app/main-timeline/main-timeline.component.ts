import { Component, OnInit } from '@angular/core';
import { PostServiceService } from '../post-service/post-service.service';
import {Post, Comment, SendPost, SendComment} from '../interfaces/intefaces';
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
      console.log(data)
      this.postArrayFactory(data);
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
      post.comments = this.commentArrayFactory(content);
      this.posts.push(post);
    }
  }

  commentArrayFactory(post:any) {
    let comments = [];
    for(let content of post.comments) {
      let comment:Comment = {
        code: content.code,
        date: new Date(content.date),
        owner: content.owner,
        text: content.text,
        post: post.code,
      }
      comments.push(comment);
    }
    return comments;
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

  createComment(input:any, code:number) {
    let newComment:SendComment = {
      postCode: code,
      text: input.value,
    }
    input.value = "";
    this.postService.sendComment(newComment).subscribe(data => {
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

  deleteComment(code:number) {
    this.postService.deleteComment(code).subscribe(data => {
      this.refreshPosts();
    });
  }

  searchPosts(input:any) {
    if(!input.value) {
      this.refreshPosts();
      return;
    }
    let query = {
      text: input.value,
    }
    this.postService.searchPosts(0, query.text).subscribe(data => {
      this.posts = [];
      this.postArrayFactory(data);
    });
    input.value = "";
  }
}
