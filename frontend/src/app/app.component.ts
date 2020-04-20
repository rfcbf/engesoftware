import {Component, OnInit} from '@angular/core';
import {AutenticacaoService} from './login/autenticacao.service';
import {Usuario} from './login/usuario';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'Cadastro';

  mostrarMenu = false;
  botaoAtivado = 'Login';
  usuarioLogado = new Usuario();

  constructor(private autenticacaoService: AutenticacaoService, private cookieService: CookieService, private router: Router) {
    this.usuarioLogado.nome = cookieService.get("nomeUsuario");

    if (this.usuarioLogado.nome !== ''){
      this.mostrarMenu = true;
      this.router.navigate(['/home'])
    }
  }

  ngOnInit(): void{
    this.autenticacaoService.usuarioLogadoEmitter.subscribe( usuario => this.usuarioLogado = usuario);
    this.autenticacaoService.mostrarMenuEmitter.subscribe( mostrar => this.mostrarMenu = mostrar);
  }

  mudaFocusLogin(){
    this.botaoAtivado = 'Login';
  }

  mudaFocusCriarUsuario(){
    this.botaoAtivado = 'CriarUsuario';
  }

  desconectar(){
    this.botaoAtivado = 'Login';
    this.cookieService.set("nomeUsuario", '');
    this.mostrarMenu = false;
    this.router.navigate(['/login'])
  }

}
