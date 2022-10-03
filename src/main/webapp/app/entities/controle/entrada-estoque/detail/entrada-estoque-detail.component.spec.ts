import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EntradaEstoqueDetailComponent } from './entrada-estoque-detail.component';

describe('EntradaEstoque Management Detail Component', () => {
  let comp: EntradaEstoqueDetailComponent;
  let fixture: ComponentFixture<EntradaEstoqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EntradaEstoqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ entradaEstoque: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EntradaEstoqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EntradaEstoqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load entradaEstoque on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.entradaEstoque).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
