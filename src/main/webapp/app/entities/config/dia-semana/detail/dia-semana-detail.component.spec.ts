import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiaSemanaDetailComponent } from './dia-semana-detail.component';

describe('DiaSemana Management Detail Component', () => {
  let comp: DiaSemanaDetailComponent;
  let fixture: ComponentFixture<DiaSemanaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DiaSemanaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ diaSemana: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DiaSemanaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DiaSemanaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load diaSemana on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.diaSemana).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
