import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReligiaoDetailComponent } from './religiao-detail.component';

describe('Religiao Management Detail Component', () => {
  let comp: ReligiaoDetailComponent;
  let fixture: ComponentFixture<ReligiaoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReligiaoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ religiao: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReligiaoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReligiaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load religiao on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.religiao).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
