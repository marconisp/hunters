import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MensagemDetailComponent } from './mensagem-detail.component';

describe('Mensagem Management Detail Component', () => {
  let comp: MensagemDetailComponent;
  let fixture: ComponentFixture<MensagemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MensagemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mensagem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MensagemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MensagemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mensagem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mensagem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
