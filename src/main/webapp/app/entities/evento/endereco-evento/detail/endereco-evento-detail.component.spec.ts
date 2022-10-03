import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnderecoEventoDetailComponent } from './endereco-evento-detail.component';

describe('EnderecoEvento Management Detail Component', () => {
  let comp: EnderecoEventoDetailComponent;
  let fixture: ComponentFixture<EnderecoEventoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnderecoEventoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ enderecoEvento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EnderecoEventoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnderecoEventoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load enderecoEvento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.enderecoEvento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
