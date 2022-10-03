import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ItemMateriaDetailComponent } from './item-materia-detail.component';

describe('ItemMateria Management Detail Component', () => {
  let comp: ItemMateriaDetailComponent;
  let fixture: ComponentFixture<ItemMateriaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ItemMateriaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ itemMateria: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ItemMateriaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ItemMateriaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load itemMateria on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.itemMateria).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
