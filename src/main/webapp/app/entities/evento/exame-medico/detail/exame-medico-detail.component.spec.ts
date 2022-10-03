import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExameMedicoDetailComponent } from './exame-medico-detail.component';

describe('ExameMedico Management Detail Component', () => {
  let comp: ExameMedicoDetailComponent;
  let fixture: ComponentFixture<ExameMedicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExameMedicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ exameMedico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExameMedicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExameMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load exameMedico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.exameMedico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
