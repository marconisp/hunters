import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaracteristicasPsiquicasDetailComponent } from './caracteristicas-psiquicas-detail.component';

describe('CaracteristicasPsiquicas Management Detail Component', () => {
  let comp: CaracteristicasPsiquicasDetailComponent;
  let fixture: ComponentFixture<CaracteristicasPsiquicasDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CaracteristicasPsiquicasDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ caracteristicasPsiquicas: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CaracteristicasPsiquicasDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CaracteristicasPsiquicasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load caracteristicasPsiquicas on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.caracteristicasPsiquicas).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
