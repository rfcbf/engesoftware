import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  autenticacaoUrl = 'http://localhost:8080/v1/usuario/';
  // Headers
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private router: Router, private http: HttpClient) { }

  async verificaEmailExistente(email: string): Promise<boolean> {
    console.log(email);

    const url = this.autenticacaoUrl + 'checkemail/' + email;
    console.log(url);

    await this.http.get(`${url}`)
      .subscribe(resultado => {
          console.log(resultado);
          if (resultado !== null) {
            return true;
          } else {
            return false;
          }
        },
        (error: any) => alert('erro')
      );

    return false;
  }

  salvarUsuario(){

  }

}
