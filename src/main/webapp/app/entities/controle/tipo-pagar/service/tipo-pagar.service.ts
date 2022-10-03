import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoPagar, getTipoPagarIdentifier } from '../tipo-pagar.model';

export type EntityResponseType = HttpResponse<ITipoPagar>;
export type EntityArrayResponseType = HttpResponse<ITipoPagar[]>;

@Injectable({ providedIn: 'root' })
export class TipoPagarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-pagars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoPagar: ITipoPagar): Observable<EntityResponseType> {
    return this.http.post<ITipoPagar>(this.resourceUrl, tipoPagar, { observe: 'response' });
  }

  update(tipoPagar: ITipoPagar): Observable<EntityResponseType> {
    return this.http.put<ITipoPagar>(`${this.resourceUrl}/${getTipoPagarIdentifier(tipoPagar) as number}`, tipoPagar, {
      observe: 'response',
    });
  }

  partialUpdate(tipoPagar: ITipoPagar): Observable<EntityResponseType> {
    return this.http.patch<ITipoPagar>(`${this.resourceUrl}/${getTipoPagarIdentifier(tipoPagar) as number}`, tipoPagar, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPagar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPagar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoPagarToCollectionIfMissing(
    tipoPagarCollection: ITipoPagar[],
    ...tipoPagarsToCheck: (ITipoPagar | null | undefined)[]
  ): ITipoPagar[] {
    const tipoPagars: ITipoPagar[] = tipoPagarsToCheck.filter(isPresent);
    if (tipoPagars.length > 0) {
      const tipoPagarCollectionIdentifiers = tipoPagarCollection.map(tipoPagarItem => getTipoPagarIdentifier(tipoPagarItem)!);
      const tipoPagarsToAdd = tipoPagars.filter(tipoPagarItem => {
        const tipoPagarIdentifier = getTipoPagarIdentifier(tipoPagarItem);
        if (tipoPagarIdentifier == null || tipoPagarCollectionIdentifiers.includes(tipoPagarIdentifier)) {
          return false;
        }
        tipoPagarCollectionIdentifiers.push(tipoPagarIdentifier);
        return true;
      });
      return [...tipoPagarsToAdd, ...tipoPagarCollection];
    }
    return tipoPagarCollection;
  }
}
