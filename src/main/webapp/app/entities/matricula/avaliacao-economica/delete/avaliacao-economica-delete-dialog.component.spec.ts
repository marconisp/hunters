jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AvaliacaoEconomicaService } from '../service/avaliacao-economica.service';

import { AvaliacaoEconomicaDeleteDialogComponent } from './avaliacao-economica-delete-dialog.component';

describe('AvaliacaoEconomica Management Delete Component', () => {
  let comp: AvaliacaoEconomicaDeleteDialogComponent;
  let fixture: ComponentFixture<AvaliacaoEconomicaDeleteDialogComponent>;
  let service: AvaliacaoEconomicaService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AvaliacaoEconomicaDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AvaliacaoEconomicaDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AvaliacaoEconomicaDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AvaliacaoEconomicaService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
