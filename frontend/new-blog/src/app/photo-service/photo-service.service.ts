import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as CONFIG from '../CONFIG';

@Injectable({
  providedIn: 'root'
})
export class PhotoServiceService {

  constructor(
    private http:HttpClient,
  ) { }

  getAlbums(userName:string) {
    return this.http.get<any>(CONFIG.GET_ALBUMS_URL);
  }

  getPhotos(albumCode:number) {
    return this.http.get<any>(`${CONFIG.GET_ALBUMS_URL}${albumCode}/photos`);
  }
}
