import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PagarDetailComponent } from './pagar-detail.component';

describe('Pagar Management Detail Component', () => {
  let comp: PagarDetailComponent;
  let fixture: ComponentFixture<PagarDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PagarDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pagar: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PagarDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PagarDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pagar on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pagar).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
