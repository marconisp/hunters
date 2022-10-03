import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExameMedico } from '../exame-medico.model';

@Component({
  selector: 'jhi-exame-medico-detail',
  templateUrl: './exame-medico-detail.component.html',
})
export class ExameMedicoDetailComponent implements OnInit {
  exameMedico: IExameMedico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exameMedico }) => {
      this.exameMedico = exameMedico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
