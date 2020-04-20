import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AutenticacaoService} from '../login/autenticacao.service';

@Injectable({
  providedIn: 'root'
})
export class AutenticacaoGuard implements CanActivate {

  constructor(private autenticacaoService: AutenticacaoService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    console.log('verificando se usuario autenticado');

    if (this.autenticacaoService.usuarioEstaAutenticado()){
      return true;
    }

    this.router.navigate(['/login']).then(r => console.log('usuario nao logado', r));
    return false;
  }

}
