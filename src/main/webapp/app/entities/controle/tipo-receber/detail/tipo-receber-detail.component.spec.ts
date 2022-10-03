import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoReceberDetailComponent } from './tipo-receber-detail.component';

describe('TipoReceber Management Detail Component', () => {
  let comp: TipoReceberDetailComponent;
  let fixture: ComponentFixture<TipoReceberDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoReceberDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoReceber: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoReceberDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoReceberDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoReceber on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoReceber).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
