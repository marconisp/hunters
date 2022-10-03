import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubGrupoProdutoDetailComponent } from './sub-grupo-produto-detail.component';

describe('SubGrupoProduto Management Detail Component', () => {
  let comp: SubGrupoProdutoDetailComponent;
  let fixture: ComponentFixture<SubGrupoProdutoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubGrupoProdutoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subGrupoProduto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubGrupoProdutoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubGrupoProdutoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subGrupoProduto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subGrupoProduto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
