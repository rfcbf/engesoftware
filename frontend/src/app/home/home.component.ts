import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Subject} from 'rxjs';
import {Usuario} from '../login/usuario';
import {Cadastro} from '../cadastro/cadastro';
import {Router} from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  dtOptions: DataTables.Settings = {};
  persons: Cadastro[] = [];

  // We use this trigger because fetching the list of persons can be quite long,
  // thus we ensure the data is fetched before rendering
  dtTrigger = new Subject();

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit(): void {

    const url = 'http://localhost:8080/v1/cadastro';

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10
    };

    this.http.get(`${url}`)
      .subscribe(data => {

        this.persons = [];

        for (let registro in data) {
          this.persons.push(data[registro]);
        }

        // Calling the DT trigger to manually render the table
        this.dtTrigger.next();
      });

  }

  ngOnDestroy(): void {
    // Do not forget to unsubscribe the event
    this.dtTrigger.unsubscribe();
  }

  editar(pessoa: Cadastro) {
    console.log('editar', pessoa);
    this.router.navigate(['/novapessoa'], {
      state: { person: pessoa }
    });
  }

  apagar(pessoa: Cadastro) {
    if (confirm('Confirma exclusão da pessoa?')) {
      const url = 'http://localhost:8080/v1/cadastro/' + pessoa.id;
      console.log(url);

      this.http.delete(`${url}`)
        .subscribe(data => {
          alert('Pessoa excluída com sucesso');

          const index: number = this.persons.indexOf(pessoa);
          if (index !== -1) {
            this.persons.splice(index, 1);
          }
        });
    }

  }

  novaPessoa() {
    this.router.navigate(['/novapessoa'], {
      state: { person: null }
    });
  }

}
