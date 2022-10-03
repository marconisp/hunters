import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeriodoDuracaoDetailComponent } from './periodo-duracao-detail.component';

describe('PeriodoDuracao Management Detail Component', () => {
  let comp: PeriodoDuracaoDetailComponent;
  let fixture: ComponentFixture<PeriodoDuracaoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PeriodoDuracaoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ periodoDuracao: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PeriodoDuracaoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PeriodoDuracaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load periodoDuracao on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.periodoDuracao).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
