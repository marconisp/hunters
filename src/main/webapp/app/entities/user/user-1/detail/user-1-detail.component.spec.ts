import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { User1DetailComponent } from './user-1-detail.component';

describe('User1 Management Detail Component', () => {
  let comp: User1DetailComponent;
  let fixture: ComponentFixture<User1DetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [User1DetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ user1: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(User1DetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(User1DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load user1 on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.user1).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
