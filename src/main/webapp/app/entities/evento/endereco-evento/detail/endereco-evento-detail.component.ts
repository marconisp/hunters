import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnderecoEvento } from '../endereco-evento.model';

@Component({
  selector: 'jhi-endereco-evento-detail',
  templateUrl: './endereco-evento-detail.component.html',
})
export class EnderecoEventoDetailComponent implements OnInit {
  enderecoEvento: IEnderecoEvento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoEvento }) => {
      this.enderecoEvento = enderecoEvento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
