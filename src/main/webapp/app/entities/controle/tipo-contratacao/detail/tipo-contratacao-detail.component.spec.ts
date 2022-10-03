import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoContratacaoDetailComponent } from './tipo-contratacao-detail.component';

describe('TipoContratacao Management Detail Component', () => {
  let comp: TipoContratacaoDetailComponent;
  let fixture: ComponentFixture<TipoContratacaoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoContratacaoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoContratacao: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoContratacaoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoContratacaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoContratacao on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoContratacao).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
