import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoPagar, getFotoPagarIdentifier } from '../foto-pagar.model';

export type EntityResponseType = HttpResponse<IFotoPagar>;
export type EntityArrayResponseType = HttpResponse<IFotoPagar[]>;

@Injectable({ providedIn: 'root' })
export class FotoPagarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-pagars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoPagar: IFotoPagar): Observable<EntityResponseType> {
    return this.http.post<IFotoPagar>(this.resourceUrl, fotoPagar, { observe: 'response' });
  }

  update(fotoPagar: IFotoPagar): Observable<EntityResponseType> {
    return this.http.put<IFotoPagar>(`${this.resourceUrl}/${getFotoPagarIdentifier(fotoPagar) as number}`, fotoPagar, {
      observe: 'response',
    });
  }

  partialUpdate(fotoPagar: IFotoPagar): Observable<EntityResponseType> {
    return this.http.patch<IFotoPagar>(`${this.resourceUrl}/${getFotoPagarIdentifier(fotoPagar) as number}`, fotoPagar, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoPagar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoPagar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByPagarId(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoPagar[]>(`${this.resourceUrl}/pagar/${id}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoPagarToCollectionIfMissing(
    fotoPagarCollection: IFotoPagar[],
    ...fotoPagarsToCheck: (IFotoPagar | null | undefined)[]
  ): IFotoPagar[] {
    const fotoPagars: IFotoPagar[] = fotoPagarsToCheck.filter(isPresent);
    if (fotoPagars.length > 0) {
      const fotoPagarCollectionIdentifiers = fotoPagarCollection.map(fotoPagarItem => getFotoPagarIdentifier(fotoPagarItem)!);
      const fotoPagarsToAdd = fotoPagars.filter(fotoPagarItem => {
        const fotoPagarIdentifier = getFotoPagarIdentifier(fotoPagarItem);
        if (fotoPagarIdentifier == null || fotoPagarCollectionIdentifiers.includes(fotoPagarIdentifier)) {
          return false;
        }
        fotoPagarCollectionIdentifiers.push(fotoPagarIdentifier);
        return true;
      });
      return [...fotoPagarsToAdd, ...fotoPagarCollection];
    }
    return fotoPagarCollection;
  }
}
