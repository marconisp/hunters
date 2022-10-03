import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoContratacao, getTipoContratacaoIdentifier } from '../tipo-contratacao.model';

export type EntityResponseType = HttpResponse<ITipoContratacao>;
export type EntityArrayResponseType = HttpResponse<ITipoContratacao[]>;

@Injectable({ providedIn: 'root' })
export class TipoContratacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-contratacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoContratacao: ITipoContratacao): Observable<EntityResponseType> {
    return this.http.post<ITipoContratacao>(this.resourceUrl, tipoContratacao, { observe: 'response' });
  }

  update(tipoContratacao: ITipoContratacao): Observable<EntityResponseType> {
    return this.http.put<ITipoContratacao>(
      `${this.resourceUrl}/${getTipoContratacaoIdentifier(tipoContratacao) as number}`,
      tipoContratacao,
      { observe: 'response' }
    );
  }

  partialUpdate(tipoContratacao: ITipoContratacao): Observable<EntityResponseType> {
    return this.http.patch<ITipoContratacao>(
      `${this.resourceUrl}/${getTipoContratacaoIdentifier(tipoContratacao) as number}`,
      tipoContratacao,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoContratacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoContratacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoContratacaoToCollectionIfMissing(
    tipoContratacaoCollection: ITipoContratacao[],
    ...tipoContratacaosToCheck: (ITipoContratacao | null | undefined)[]
  ): ITipoContratacao[] {
    const tipoContratacaos: ITipoContratacao[] = tipoContratacaosToCheck.filter(isPresent);
    if (tipoContratacaos.length > 0) {
      const tipoContratacaoCollectionIdentifiers = tipoContratacaoCollection.map(
        tipoContratacaoItem => getTipoContratacaoIdentifier(tipoContratacaoItem)!
      );
      const tipoContratacaosToAdd = tipoContratacaos.filter(tipoContratacaoItem => {
        const tipoContratacaoIdentifier = getTipoContratacaoIdentifier(tipoContratacaoItem);
        if (tipoContratacaoIdentifier == null || tipoContratacaoCollectionIdentifiers.includes(tipoContratacaoIdentifier)) {
          return false;
        }
        tipoContratacaoCollectionIdentifiers.push(tipoContratacaoIdentifier);
        return true;
      });
      return [...tipoContratacaosToAdd, ...tipoContratacaoCollection];
    }
    return tipoContratacaoCollection;
  }
}
