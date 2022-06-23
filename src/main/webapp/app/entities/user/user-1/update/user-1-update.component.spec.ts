import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { User1Service } from '../service/user-1.service';
import { IUser1, User1 } from '../user-1.model';

import { User1UpdateComponent } from './user-1-update.component';

describe('User1 Management Update Component', () => {
  let comp: User1UpdateComponent;
  let fixture: ComponentFixture<User1UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let user1Service: User1Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [User1UpdateComponent],
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
      .overrideTemplate(User1UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(User1UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    user1Service = TestBed.inject(User1Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const user1: IUser1 = { id: 456 };

      activatedRoute.data = of({ user1 });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(user1));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<User1>>();
      const user1 = { id: 123 };
      jest.spyOn(user1Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user1 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user1 }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(user1Service.update).toHaveBeenCalledWith(user1);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<User1>>();
      const user1 = new User1();
      jest.spyOn(user1Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user1 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: user1 }));
      saveSubject.complete();

      // THEN
      expect(user1Service.create).toHaveBeenCalledWith(user1);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<User1>>();
      const user1 = { id: 123 };
      jest.spyOn(user1Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ user1 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(user1Service.update).toHaveBeenCalledWith(user1);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
