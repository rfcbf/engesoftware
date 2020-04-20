import { Component, OnInit } from '@angular/core';
import {AutenticacaoService} from './autenticacao.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Usuario} from './usuario';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  formulario: FormGroup;
  alerta = false;
  usuario = new Usuario();

  constructor(private formBuilder: FormBuilder, private autenticacaoService: AutenticacaoService, private router: Router) { }

  ngOnInit(): void {
    this.alerta = false;
    this.formulario = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email] ],
      senha: [null, [Validators.required]],
    });
  }

  async autenticar(){
    this.alerta = false;

    if (this.formulario.valid) {
      this.usuario = await this.autenticacaoService.efetuarLogin(this.formulario.value);

      if (this.usuario === null) {
        this.alerta = true;
      }
    }
  }

  removeAlert(){
    this.alerta = false;
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
    };
  }


}
