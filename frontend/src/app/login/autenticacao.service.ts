import {EventEmitter, Injectable} from '@angular/core';
import {Usuario} from './usuario';
import {Router} from '@angular/router';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {throwError} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class AutenticacaoService {

  autenticacaoUrl = 'http://localhost:8080/v1/usuario/';
  // Headers
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  private usuarioAutenticado = false;
  mostrarMenuEmitter = new EventEmitter<boolean>();
  usuarioLogadoEmitter = new EventEmitter<any>();
  private usuarioRecebido = new Usuario();

  constructor(private router: Router,
              private http: HttpClient,
              private cookieService: CookieService) {
  }

  efetuarLogin(usuario: Usuario): Promise<Usuario> {

    const url = this.autenticacaoUrl + 'auth/' + usuario.email + '/' + usuario.senha;

    this.http.get(`${url}`)
      .subscribe(resultado => {

          if (resultado !== null) {
            this.usuarioRecebido.nome = resultado['nome'];
            this.usuarioRecebido.email = resultado['email'];
            this.usuarioRecebido.id = resultado['id'];

            this.usuarioAutenticado = true;
            this.mostrarMenuEmitter.emit(true);
            this.usuarioLogadoEmitter.emit(this.usuarioRecebido);
            this.cookieService.set("nomeUsuario", this.usuarioRecebido.nome);
            this.router.navigate(['/home']).then(r => console.log('redirecionado para home', r));
            return resultado;
          } else {
            console.log('usuario nao autenticado');
            this.usuarioRecebido.nome = null;
            this.usuarioRecebido.email = null;
            this.usuarioRecebido.id = null;
            this.cookieService.set("nomeUsuario", '');
            this.mostrarMenuEmitter.emit(false);
            this.usuarioLogadoEmitter.emit(this.usuarioRecebido);
            this.usuarioAutenticado = false;
            return null;
          }

        }
      );
    return null;
  }

  usuarioEstaAutenticado(): boolean {
    const nome = this.cookieService.get("nomeUsuario");
    return this.usuarioAutenticado || nome !== '';
  }

  // Manipulação de erros
  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Erro ocorreu no lado do client
      errorMessage = error.error.message;
    } else {
      // Erro ocorreu no lado do servidor
      errorMessage = `Código do erro: ${error.status}, ` + `menssagem: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }

}
