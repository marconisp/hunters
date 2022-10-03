import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ColaboradorDetailComponent } from './colaborador-detail.component';

describe('Colaborador Management Detail Component', () => {
  let comp: ColaboradorDetailComponent;
  let fixture: ComponentFixture<ColaboradorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ColaboradorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ colaborador: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ColaboradorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ColaboradorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load colaborador on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.colaborador).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
