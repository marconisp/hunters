import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReceberDetailComponent } from './receber-detail.component';

describe('Receber Management Detail Component', () => {
  let comp: ReceberDetailComponent;
  let fixture: ComponentFixture<ReceberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReceberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ receber: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReceberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReceberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load receber on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.receber).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
