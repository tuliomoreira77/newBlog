import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as CONFIG from '../CONFIG';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private http: HttpClient,
  ) { }

  ngOnInit(): void {
    this.redirectToOauthServer();
  }

  redirectToOauthServer() {
    let params = {
      client_id: 'framework_new_blog',
      scope: 'read',
      state: '1234',
      response_type: 'token',
      redirect_uri: encodeURIComponent(CONFIG.OAUTH_REDIRECT_URL),
    }

    let uri = CONFIG.OAUTH_LOGIN_URL + '?';
    for(let key of Object.keys(params)) {
      uri = uri + key + '=' + params[key] + '&';
    }

    window.location.href = uri;
  }
}
