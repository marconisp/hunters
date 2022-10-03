import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemMateria } from '../item-materia.model';

@Component({
  selector: 'jhi-item-materia-detail',
  templateUrl: './item-materia-detail.component.html',
})
export class ItemMateriaDetailComponent implements OnInit {
  itemMateria: IItemMateria | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemMateria }) => {
      this.itemMateria = itemMateria;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
