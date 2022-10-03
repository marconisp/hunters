import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReceberDeDetailComponent } from './receber-de-detail.component';

describe('ReceberDe Management Detail Component', () => {
  let comp: ReceberDeDetailComponent;
  let fixture: ComponentFixture<ReceberDeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReceberDeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ receberDe: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReceberDeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReceberDeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load receberDe on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.receberDe).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
