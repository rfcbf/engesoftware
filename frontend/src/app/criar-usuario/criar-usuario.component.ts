import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UsuarioService} from './usuario.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-criar-usuario',
  templateUrl: './criar-usuario.component.html',
  styleUrls: ['./criar-usuario.component.scss']
})
export class CriarUsuarioComponent implements OnInit {

  formulario: FormGroup;
  alertaEmail = false;

  constructor(private formBuilder: FormBuilder, private router: Router, private usuarioService: UsuarioService, private http: HttpClient) { }

  ngOnInit(): void {
    this.alertaEmail = false;
    this.formulario = this.formBuilder.group({
      nome:  [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email] ],
      senha: [null, [Validators.required]],
      id: [null]
    });
  }

  async criarUsuario() {
    this.alertaEmail = false;
    const autenticacaoUrl = 'http://localhost:8080/v1/usuario/';
    const url = autenticacaoUrl + 'checkemail/' + this.formulario.get('email').value;

    if (this.formulario.valid) {

      await this.http.get(`${url}`)
        .subscribe(resultado => {
            if (resultado !== null) {
              this.alertaEmail=true;
            } else {
              this.alertaEmail=false;

              this.http.post<any>(`${autenticacaoUrl}`, this.formulario.value).subscribe(data => {
                alert('Usuário criado com sucesso');
                this.router.navigate(['/login']);
              });

            }
          },
          (error: any) => alert('Erro ao criar o usuário')
        );

    } else {
      Object.keys(this.formulario.controls).forEach(campo => {
        const controle = this.formulario.get(campo);
        controle.markAsDirty();
      })
    }
  }

  removeAlert(){
    this.alertaEmail = false;
  }

  verificaValidTouch(campo){
    return !this.formulario.get(campo).valid && ( this.formulario.get(campo).touched || this.formulario.get(campo).dirty );
  }

  verificaEmailValido(){
    const campoEmail = this.formulario.get('email');
    if (campoEmail.errors){
      return campoEmail.errors.email && (campoEmail.touched || campoEmail.dirty);
    }
  }

  aplicaCssError(campo){
    return {
      'is-invalid': this.verificaValidTouch(campo)
      // 'has-feedback': this.verificaValidTouch(campo)
    };
  }

}
