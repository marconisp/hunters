import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDadosMedico } from '../dados-medico.model';

@Component({
  selector: 'jhi-dados-medico-detail',
  templateUrl: './dados-medico-detail.component.html',
})
export class DadosMedicoDetailComponent implements OnInit {
  dadosMedico: IDadosMedico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dadosMedico }) => {
      this.dadosMedico = dadosMedico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
