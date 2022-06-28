import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DadosPessoaisDetailComponent } from './dados-pessoais-detail.component';

describe('DadosPessoais Management Detail Component', () => {
  let comp: DadosPessoaisDetailComponent;
  let fixture: ComponentFixture<DadosPessoaisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DadosPessoaisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dadosPessoais: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DadosPessoaisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DadosPessoaisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dadosPessoais on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dadosPessoais).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
