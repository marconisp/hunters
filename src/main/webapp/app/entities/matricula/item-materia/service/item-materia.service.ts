import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemMateria, getItemMateriaIdentifier } from '../item-materia.model';

export type EntityResponseType = HttpResponse<IItemMateria>;
export type EntityArrayResponseType = HttpResponse<IItemMateria[]>;

@Injectable({ providedIn: 'root' })
export class ItemMateriaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-materias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(itemMateria: IItemMateria): Observable<EntityResponseType> {
    return this.http.post<IItemMateria>(this.resourceUrl, itemMateria, { observe: 'response' });
  }

  update(itemMateria: IItemMateria): Observable<EntityResponseType> {
    return this.http.put<IItemMateria>(`${this.resourceUrl}/${getItemMateriaIdentifier(itemMateria) as number}`, itemMateria, {
      observe: 'response',
    });
  }

  partialUpdate(itemMateria: IItemMateria): Observable<EntityResponseType> {
    return this.http.patch<IItemMateria>(`${this.resourceUrl}/${getItemMateriaIdentifier(itemMateria) as number}`, itemMateria, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemMateria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemMateria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addItemMateriaToCollectionIfMissing(
    itemMateriaCollection: IItemMateria[],
    ...itemMateriasToCheck: (IItemMateria | null | undefined)[]
  ): IItemMateria[] {
    const itemMaterias: IItemMateria[] = itemMateriasToCheck.filter(isPresent);
    if (itemMaterias.length > 0) {
      const itemMateriaCollectionIdentifiers = itemMateriaCollection.map(itemMateriaItem => getItemMateriaIdentifier(itemMateriaItem)!);
      const itemMateriasToAdd = itemMaterias.filter(itemMateriaItem => {
        const itemMateriaIdentifier = getItemMateriaIdentifier(itemMateriaItem);
        if (itemMateriaIdentifier == null || itemMateriaCollectionIdentifiers.includes(itemMateriaIdentifier)) {
          return false;
        }
        itemMateriaCollectionIdentifiers.push(itemMateriaIdentifier);
        return true;
      });
      return [...itemMateriasToAdd, ...itemMateriaCollection];
    }
    return itemMateriaCollection;
  }
}
