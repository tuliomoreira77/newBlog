import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as CONFIG from '../CONFIG';
import {SendPost, SendComment} from '../interfaces/intefaces';

@Injectable({
  providedIn: 'root'
})
export class PostServiceService {

  constructor(
    private http: HttpClient,
  ) { }

  getPosts(pageNumber?:number) {
    pageNumber = pageNumber ? pageNumber : 0;
    return this.http.get<any>(`${CONFIG.GET_POSTS_URL}${pageNumber}`);
  }

  searchPosts(pageNumber:number, query:string) {
    return this.http.get<any>(`${CONFIG.GET_POSTS_URL}${pageNumber}/${encodeURIComponent(query)}`);
  }

  getPhoto(code:number) {
    return this.http.get<any>(`${CONFIG.GET_IMAGE_URL}${code}`);
  }

  sendPost(post:SendPost) {
    return this.http.post<any>(`${CONFIG.CREATE_POST_URL}`, post);
  }

  sendComment(comment:SendComment) {
    return this.http.post<any>(`${CONFIG.CREATE_POST_URL}${comment.postCode}/comments`, comment);
  }

  delete(code:number) {
    return this.http.delete<any>(`${CONFIG.CREATE_POST_URL}${code}`);
  }

  deleteComment(code:number) {
    return this.http.delete<any>(`${CONFIG.CREATE_POST_URL}comments/${code}`);
  }
}
