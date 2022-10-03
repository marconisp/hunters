import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoTransacaoDetailComponent } from './tipo-transacao-detail.component';

describe('TipoTransacao Management Detail Component', () => {
  let comp: TipoTransacaoDetailComponent;
  let fixture: ComponentFixture<TipoTransacaoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoTransacaoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoTransacao: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoTransacaoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoTransacaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoTransacao on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoTransacao).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
