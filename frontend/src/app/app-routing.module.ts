import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {PagenotfoundComponent} from './pagenotfound/pagenotfound.component';
import {AutenticacaoGuard} from './guards/autenticacao.guard';
import {CriarUsuarioComponent} from './criar-usuario/criar-usuario.component';
import {NovaPessoaComponent} from './cadastro/nova-pessoa/nova-pessoa.component';

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, canActivate: [AutenticacaoGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'criarusuario', component: CriarUsuarioComponent},
  {path: 'novapessoa', component: NovaPessoaComponent, canActivate: [AutenticacaoGuard]},
  {path:  '**', component: PagenotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
