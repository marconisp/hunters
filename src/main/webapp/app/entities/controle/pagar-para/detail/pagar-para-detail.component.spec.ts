import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PagarParaDetailComponent } from './pagar-para-detail.component';

describe('PagarPara Management Detail Component', () => {
  let comp: PagarParaDetailComponent;
  let fixture: ComponentFixture<PagarParaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PagarParaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pagarPara: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PagarParaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PagarParaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pagarPara on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pagarPara).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
