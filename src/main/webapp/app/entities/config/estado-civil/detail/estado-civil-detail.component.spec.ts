import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstadoCivilDetailComponent } from './estado-civil-detail.component';

describe('EstadoCivil Management Detail Component', () => {
  let comp: EstadoCivilDetailComponent;
  let fixture: ComponentFixture<EstadoCivilDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstadoCivilDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ estadoCivil: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EstadoCivilDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstadoCivilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estadoCivil on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.estadoCivil).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
