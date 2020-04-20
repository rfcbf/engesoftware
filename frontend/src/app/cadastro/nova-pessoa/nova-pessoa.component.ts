import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UsuarioService} from '../../criar-usuario/usuario.service';
import {HttpClient} from '@angular/common/http';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import {Cadastro} from '../cadastro';


@Component({
  selector: 'app-nova-pessoa',
  templateUrl: './nova-pessoa.component.html',
  styleUrls: ['./nova-pessoa.component.scss']
})
export class NovaPessoaComponent implements OnInit {
  formulario: FormGroup;
  person : Cadastro;
  titulo = "";

  constructor(private formBuilder: FormBuilder, private router: Router, private http: HttpClient) {
    const nav = this.router.getCurrentNavigation();
    this.person = nav.extras.state.person;

    if (this.person === null ){
      this.titulo= "NOVA PESSOA"
    }else{
      this.titulo= "EDITAR PESSOA"
    }
  }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      nome: [this.person === null ? null : this.person.nome, [Validators.required]],
      email: [this.person === null ? null : this.person.email, [Validators.required, Validators.email]],
      telefone: [this.person === null ? null : this.person.telefone],
      empresa: [this.person === null ? null : this.person.empresa],
      id: [this.person === null ? null : this.person.id]
    });

  }

  criarPessoa() {
    const cadastroUrl = 'http://localhost:8080/v1/cadastro/';

    if (this.formulario.valid) {

      //nova pessoa
      if (this.formulario.get('id').value === null ) {
        this.http.post<any>(`${cadastroUrl}`, this.formulario.value).subscribe(data => {
          alert('Registro adicionado com sucesso');
          this.router.navigate(['/home']);
        });
      }else{
        //editando pessoa
        console.log('salvar edicaxo');
        this.http.put<any>(`${cadastroUrl}`, this.formulario.value).subscribe(data => {
          alert('Registro editado com sucesso');
          this.router.navigate(['/home']);
        });

      }

    } else {
      Object.keys(this.formulario.controls).forEach(campo => {
        const controle = this.formulario.get(campo);
        controle.markAsDirty();
      });
    }
  }

  cancelar() {
    this.router.navigate(['/home']);
  }

  verificaValidTouch(campo) {
    return !this.formulario.get(campo).valid && (this.formulario.get(campo).touched || this.formulario.get(campo).dirty);
  }

  verificaEmailValido() {
    const campoEmail = this.formulario.get('email');
    if (campoEmail.errors) {
      return campoEmail.errors.email && (campoEmail.touched || campoEmail.dirty);
    }
  }

  aplicaCssError(campo) {
    return {
      'is-invalid': this.verificaValidTouch(campo)
    };
  }


}
