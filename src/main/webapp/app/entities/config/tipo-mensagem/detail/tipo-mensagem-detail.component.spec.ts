import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoMensagemDetailComponent } from './tipo-mensagem-detail.component';

describe('TipoMensagem Management Detail Component', () => {
  let comp: TipoMensagemDetailComponent;
  let fixture: ComponentFixture<TipoMensagemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoMensagemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoMensagem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoMensagemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoMensagemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoMensagem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoMensagem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
