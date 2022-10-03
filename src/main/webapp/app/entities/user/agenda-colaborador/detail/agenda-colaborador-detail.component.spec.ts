import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgendaColaboradorDetailComponent } from './agenda-colaborador-detail.component';

describe('AgendaColaborador Management Detail Component', () => {
  let comp: AgendaColaboradorDetailComponent;
  let fixture: ComponentFixture<AgendaColaboradorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgendaColaboradorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ agendaColaborador: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AgendaColaboradorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AgendaColaboradorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agendaColaborador on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.agendaColaborador).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
