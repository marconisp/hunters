import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AvaliacaoEconomicaDetailComponent } from './avaliacao-economica-detail.component';

describe('AvaliacaoEconomica Management Detail Component', () => {
  let comp: AvaliacaoEconomicaDetailComponent;
  let fixture: ComponentFixture<AvaliacaoEconomicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvaliacaoEconomicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ avaliacaoEconomica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AvaliacaoEconomicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AvaliacaoEconomicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load avaliacaoEconomica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.avaliacaoEconomica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
