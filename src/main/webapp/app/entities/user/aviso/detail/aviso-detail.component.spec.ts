import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AvisoDetailComponent } from './aviso-detail.component';

describe('Aviso Management Detail Component', () => {
  let comp: AvisoDetailComponent;
  let fixture: ComponentFixture<AvisoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvisoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ aviso: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AvisoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AvisoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load aviso on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.aviso).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
