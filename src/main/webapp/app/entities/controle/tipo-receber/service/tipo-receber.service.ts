import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoReceber, getTipoReceberIdentifier } from '../tipo-receber.model';

export type EntityResponseType = HttpResponse<ITipoReceber>;
export type EntityArrayResponseType = HttpResponse<ITipoReceber[]>;

@Injectable({ providedIn: 'root' })
export class TipoReceberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-recebers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoReceber: ITipoReceber): Observable<EntityResponseType> {
    return this.http.post<ITipoReceber>(this.resourceUrl, tipoReceber, { observe: 'response' });
  }

  update(tipoReceber: ITipoReceber): Observable<EntityResponseType> {
    return this.http.put<ITipoReceber>(`${this.resourceUrl}/${getTipoReceberIdentifier(tipoReceber) as number}`, tipoReceber, {
      observe: 'response',
    });
  }

  partialUpdate(tipoReceber: ITipoReceber): Observable<EntityResponseType> {
    return this.http.patch<ITipoReceber>(`${this.resourceUrl}/${getTipoReceberIdentifier(tipoReceber) as number}`, tipoReceber, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoReceber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoReceber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoReceberToCollectionIfMissing(
    tipoReceberCollection: ITipoReceber[],
    ...tipoRecebersToCheck: (ITipoReceber | null | undefined)[]
  ): ITipoReceber[] {
    const tipoRecebers: ITipoReceber[] = tipoRecebersToCheck.filter(isPresent);
    if (tipoRecebers.length > 0) {
      const tipoReceberCollectionIdentifiers = tipoReceberCollection.map(tipoReceberItem => getTipoReceberIdentifier(tipoReceberItem)!);
      const tipoRecebersToAdd = tipoRecebers.filter(tipoReceberItem => {
        const tipoReceberIdentifier = getTipoReceberIdentifier(tipoReceberItem);
        if (tipoReceberIdentifier == null || tipoReceberCollectionIdentifiers.includes(tipoReceberIdentifier)) {
          return false;
        }
        tipoReceberCollectionIdentifiers.push(tipoReceberIdentifier);
        return true;
      });
      return [...tipoRecebersToAdd, ...tipoReceberCollection];
    }
    return tipoReceberCollection;
  }
}
