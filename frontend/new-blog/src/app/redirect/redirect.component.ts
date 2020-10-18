import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenService } from '../auth/token.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.css']
})
export class RedirectComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginService: TokenService,
  ) { 
    
  }

  ngOnInit(): void {
    this.getAccessToken(this.route);
  }

  getAccessToken(route:ActivatedRoute) {
    route.fragment.subscribe(params => {
      const token = new URLSearchParams(params).get('access_token');
      this.loginService.persistToken(token);
      this.router.navigateByUrl('');
    });
  }

}
