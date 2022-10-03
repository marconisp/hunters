import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoTransacao, getTipoTransacaoIdentifier } from '../tipo-transacao.model';

export type EntityResponseType = HttpResponse<ITipoTransacao>;
export type EntityArrayResponseType = HttpResponse<ITipoTransacao[]>;

@Injectable({ providedIn: 'root' })
export class TipoTransacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-transacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoTransacao: ITipoTransacao): Observable<EntityResponseType> {
    return this.http.post<ITipoTransacao>(this.resourceUrl, tipoTransacao, { observe: 'response' });
  }

  update(tipoTransacao: ITipoTransacao): Observable<EntityResponseType> {
    return this.http.put<ITipoTransacao>(`${this.resourceUrl}/${getTipoTransacaoIdentifier(tipoTransacao) as number}`, tipoTransacao, {
      observe: 'response',
    });
  }

  partialUpdate(tipoTransacao: ITipoTransacao): Observable<EntityResponseType> {
    return this.http.patch<ITipoTransacao>(`${this.resourceUrl}/${getTipoTransacaoIdentifier(tipoTransacao) as number}`, tipoTransacao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoTransacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoTransacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoTransacaoToCollectionIfMissing(
    tipoTransacaoCollection: ITipoTransacao[],
    ...tipoTransacaosToCheck: (ITipoTransacao | null | undefined)[]
  ): ITipoTransacao[] {
    const tipoTransacaos: ITipoTransacao[] = tipoTransacaosToCheck.filter(isPresent);
    if (tipoTransacaos.length > 0) {
      const tipoTransacaoCollectionIdentifiers = tipoTransacaoCollection.map(
        tipoTransacaoItem => getTipoTransacaoIdentifier(tipoTransacaoItem)!
      );
      const tipoTransacaosToAdd = tipoTransacaos.filter(tipoTransacaoItem => {
        const tipoTransacaoIdentifier = getTipoTransacaoIdentifier(tipoTransacaoItem);
        if (tipoTransacaoIdentifier == null || tipoTransacaoCollectionIdentifiers.includes(tipoTransacaoIdentifier)) {
          return false;
        }
        tipoTransacaoCollectionIdentifiers.push(tipoTransacaoIdentifier);
        return true;
      });
      return [...tipoTransacaosToAdd, ...tipoTransacaoCollection];
    }
    return tipoTransacaoCollection;
  }
}
