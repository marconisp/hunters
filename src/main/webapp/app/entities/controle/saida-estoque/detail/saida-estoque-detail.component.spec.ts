import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SaidaEstoqueDetailComponent } from './saida-estoque-detail.component';

describe('SaidaEstoque Management Detail Component', () => {
  let comp: SaidaEstoqueDetailComponent;
  let fixture: ComponentFixture<SaidaEstoqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaidaEstoqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ saidaEstoque: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SaidaEstoqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SaidaEstoqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saidaEstoque on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.saidaEstoque).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
