import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TipoPessoaDetailComponent } from './tipo-pessoa-detail.component';

describe('TipoPessoa Management Detail Component', () => {
  let comp: TipoPessoaDetailComponent;
  let fixture: ComponentFixture<TipoPessoaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TipoPessoaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tipoPessoa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TipoPessoaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TipoPessoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tipoPessoa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tipoPessoa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
