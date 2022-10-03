import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcompanhamentoAluno } from '../acompanhamento-aluno.model';

@Component({
  selector: 'jhi-acompanhamento-aluno-detail',
  templateUrl: './acompanhamento-aluno-detail.component.html',
})
export class AcompanhamentoAlunoDetailComponent implements OnInit {
  acompanhamentoAluno: IAcompanhamentoAluno | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acompanhamentoAluno }) => {
      this.acompanhamentoAluno = acompanhamentoAluno;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
