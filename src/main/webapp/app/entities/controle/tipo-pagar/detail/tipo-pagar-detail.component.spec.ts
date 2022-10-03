import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoPagarDetailComponent } from './tipo-pagar-detail.component';

describe('TipoPagar Management Detail Component', () => {
  let comp: TipoPagarDetailComponent;
  let fixture: ComponentFixture<TipoPagarDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoPagarDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoPagar: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoPagarDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoPagarDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoPagar on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoPagar).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
