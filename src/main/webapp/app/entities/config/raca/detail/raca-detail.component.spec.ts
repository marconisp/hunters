import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RacaDetailComponent } from './raca-detail.component';

describe('Raca Management Detail Component', () => {
  let comp: RacaDetailComponent;
  let fixture: ComponentFixture<RacaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RacaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ raca: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RacaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RacaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load raca on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.raca).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
