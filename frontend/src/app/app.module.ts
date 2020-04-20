import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';
import { HomeComponent } from './home/home.component';
import { AutenticacaoService } from './login/autenticacao.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutenticacaoGuard } from './guards/autenticacao.guard';
import { HttpClientModule } from '@angular/common/http';
import { CriarUsuarioComponent } from './criar-usuario/criar-usuario.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { CampoControlErroComponent } from './campo-control-erro/campo-control-erro.component';
import { CookieService } from 'ngx-cookie-service';
import { DataTablesModule } from 'angular-datatables';
import { NovaPessoaComponent } from './cadastro/nova-pessoa/nova-pessoa.component';
import { NgxMaskModule } from 'ngx-mask';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PagenotfoundComponent,
    HomeComponent,
    CriarUsuarioComponent,
    CampoControlErroComponent,
    NovaPessoaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    RouterModule,
    DataTablesModule,
    NgxMaskModule.forRoot()
  ],
  providers: [AutenticacaoService, AutenticacaoGuard, CookieService],
  bootstrap: [AppComponent]
})

export class AppModule { }
