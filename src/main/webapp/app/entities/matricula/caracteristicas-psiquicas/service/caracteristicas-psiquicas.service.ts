import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICaracteristicasPsiquicas, getCaracteristicasPsiquicasIdentifier } from '../caracteristicas-psiquicas.model';

export type EntityResponseType = HttpResponse<ICaracteristicasPsiquicas>;
export type EntityArrayResponseType = HttpResponse<ICaracteristicasPsiquicas[]>;

@Injectable({ providedIn: 'root' })
export class CaracteristicasPsiquicasService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/caracteristicas-psiquicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(caracteristicasPsiquicas: ICaracteristicasPsiquicas): Observable<EntityResponseType> {
    return this.http.post<ICaracteristicasPsiquicas>(this.resourceUrl, caracteristicasPsiquicas, { observe: 'response' });
  }

  update(caracteristicasPsiquicas: ICaracteristicasPsiquicas): Observable<EntityResponseType> {
    return this.http.put<ICaracteristicasPsiquicas>(
      `${this.resourceUrl}/${getCaracteristicasPsiquicasIdentifier(caracteristicasPsiquicas) as number}`,
      caracteristicasPsiquicas,
      { observe: 'response' }
    );
  }

  partialUpdate(caracteristicasPsiquicas: ICaracteristicasPsiquicas): Observable<EntityResponseType> {
    return this.http.patch<ICaracteristicasPsiquicas>(
      `${this.resourceUrl}/${getCaracteristicasPsiquicasIdentifier(caracteristicasPsiquicas) as number}`,
      caracteristicasPsiquicas,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICaracteristicasPsiquicas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICaracteristicasPsiquicas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCaracteristicasPsiquicasToCollectionIfMissing(
    caracteristicasPsiquicasCollection: ICaracteristicasPsiquicas[],
    ...caracteristicasPsiquicasToCheck: (ICaracteristicasPsiquicas | null | undefined)[]
  ): ICaracteristicasPsiquicas[] {
    const caracteristicasPsiquicas: ICaracteristicasPsiquicas[] = caracteristicasPsiquicasToCheck.filter(isPresent);
    if (caracteristicasPsiquicas.length > 0) {
      const caracteristicasPsiquicasCollectionIdentifiers = caracteristicasPsiquicasCollection.map(
        caracteristicasPsiquicasItem => getCaracteristicasPsiquicasIdentifier(caracteristicasPsiquicasItem)!
      );
      const caracteristicasPsiquicasToAdd = caracteristicasPsiquicas.filter(caracteristicasPsiquicasItem => {
        const caracteristicasPsiquicasIdentifier = getCaracteristicasPsiquicasIdentifier(caracteristicasPsiquicasItem);
        if (
          caracteristicasPsiquicasIdentifier == null ||
          caracteristicasPsiquicasCollectionIdentifiers.includes(caracteristicasPsiquicasIdentifier)
        ) {
          return false;
        }
        caracteristicasPsiquicasCollectionIdentifiers.push(caracteristicasPsiquicasIdentifier);
        return true;
      });
      return [...caracteristicasPsiquicasToAdd, ...caracteristicasPsiquicasCollection];
    }
    return caracteristicasPsiquicasCollection;
  }
}
