import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GrupoProdutoDetailComponent } from './grupo-produto-detail.component';

describe('GrupoProduto Management Detail Component', () => {
  let comp: GrupoProdutoDetailComponent;
  let fixture: ComponentFixture<GrupoProdutoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GrupoProdutoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ grupoProduto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GrupoProdutoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GrupoProdutoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load grupoProduto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.grupoProduto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
