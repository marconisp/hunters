import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoAvatarService } from '../service/foto-avatar.service';
import { IFotoAvatar, FotoAvatar } from '../foto-avatar.model';

import { FotoAvatarUpdateComponent } from './foto-avatar-update.component';

describe('FotoAvatar Management Update Component', () => {
  let comp: FotoAvatarUpdateComponent;
  let fixture: ComponentFixture<FotoAvatarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoAvatarService: FotoAvatarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoAvatarUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FotoAvatarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoAvatarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoAvatarService = TestBed.inject(FotoAvatarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fotoAvatar: IFotoAvatar = { id: 456 };

      activatedRoute.data = of({ fotoAvatar });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(fotoAvatar));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoAvatar>>();
      const fotoAvatar = { id: 123 };
      jest.spyOn(fotoAvatarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoAvatar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoAvatar }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoAvatarService.update).toHaveBeenCalledWith(fotoAvatar);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoAvatar>>();
      const fotoAvatar = new FotoAvatar();
      jest.spyOn(fotoAvatarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoAvatar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoAvatar }));
      saveSubject.complete();

      // THEN
      expect(fotoAvatarService.create).toHaveBeenCalledWith(fotoAvatar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<FotoAvatar>>();
      const fotoAvatar = { id: 123 };
      jest.spyOn(fotoAvatarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoAvatar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoAvatarService.update).toHaveBeenCalledWith(fotoAvatar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
