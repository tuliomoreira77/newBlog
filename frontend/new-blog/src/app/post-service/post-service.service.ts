import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as CONFIG from '../CONFIG';
import {SendPost} from '../interfaces/intefaces';

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

  getPhoto(code:number) {
    return this.http.get<any>(`${CONFIG.GET_IMAGE_URL}${code}`);
  }

  sendPost(post:SendPost) {
    return this.http.post<any>(`${CONFIG.CREATE_POST_URL}`, post);
  }

  delete(code:number) {
    return this.http.delete<any>(`${CONFIG.CREATE_POST_URL}${code}`);
  }
}
