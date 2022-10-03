import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlergiaDetailComponent } from './alergia-detail.component';

describe('Alergia Management Detail Component', () => {
  let comp: AlergiaDetailComponent;
  let fixture: ComponentFixture<AlergiaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlergiaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ alergia: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AlergiaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AlergiaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load alergia on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.alergia).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
