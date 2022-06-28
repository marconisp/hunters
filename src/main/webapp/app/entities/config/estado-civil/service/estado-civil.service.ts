import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstadoCivil, getEstadoCivilIdentifier } from '../estado-civil.model';

export type EntityResponseType = HttpResponse<IEstadoCivil>;
export type EntityArrayResponseType = HttpResponse<IEstadoCivil[]>;

@Injectable({ providedIn: 'root' })
export class EstadoCivilService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estado-civils');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estadoCivil: IEstadoCivil): Observable<EntityResponseType> {
    return this.http.post<IEstadoCivil>(this.resourceUrl, estadoCivil, { observe: 'response' });
  }

  update(estadoCivil: IEstadoCivil): Observable<EntityResponseType> {
    return this.http.put<IEstadoCivil>(`${this.resourceUrl}/${getEstadoCivilIdentifier(estadoCivil) as number}`, estadoCivil, {
      observe: 'response',
    });
  }

  partialUpdate(estadoCivil: IEstadoCivil): Observable<EntityResponseType> {
    return this.http.patch<IEstadoCivil>(`${this.resourceUrl}/${getEstadoCivilIdentifier(estadoCivil) as number}`, estadoCivil, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoCivil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoCivil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstadoCivilToCollectionIfMissing(
    estadoCivilCollection: IEstadoCivil[],
    ...estadoCivilsToCheck: (IEstadoCivil | null | undefined)[]
  ): IEstadoCivil[] {
    const estadoCivils: IEstadoCivil[] = estadoCivilsToCheck.filter(isPresent);
    if (estadoCivils.length > 0) {
      const estadoCivilCollectionIdentifiers = estadoCivilCollection.map(estadoCivilItem => getEstadoCivilIdentifier(estadoCivilItem)!);
      const estadoCivilsToAdd = estadoCivils.filter(estadoCivilItem => {
        const estadoCivilIdentifier = getEstadoCivilIdentifier(estadoCivilItem);
        if (estadoCivilIdentifier == null || estadoCivilCollectionIdentifiers.includes(estadoCivilIdentifier)) {
          return false;
        }
        estadoCivilCollectionIdentifiers.push(estadoCivilIdentifier);
        return true;
      });
      return [...estadoCivilsToAdd, ...estadoCivilCollection];
    }
    return estadoCivilCollection;
  }
}
