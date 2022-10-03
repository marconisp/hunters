import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DadosMedicoDetailComponent } from './dados-medico-detail.component';

describe('DadosMedico Management Detail Component', () => {
  let comp: DadosMedicoDetailComponent;
  let fixture: ComponentFixture<DadosMedicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DadosMedicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dadosMedico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DadosMedicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DadosMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dadosMedico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dadosMedico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
