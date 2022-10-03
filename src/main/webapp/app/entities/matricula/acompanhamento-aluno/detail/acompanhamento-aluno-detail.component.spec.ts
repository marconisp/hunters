import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AcompanhamentoAlunoDetailComponent } from './acompanhamento-aluno-detail.component';

describe('AcompanhamentoAluno Management Detail Component', () => {
  let comp: AcompanhamentoAlunoDetailComponent;
  let fixture: ComponentFixture<AcompanhamentoAlunoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AcompanhamentoAlunoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ acompanhamentoAluno: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AcompanhamentoAlunoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AcompanhamentoAlunoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load acompanhamentoAluno on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.acompanhamentoAluno).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
