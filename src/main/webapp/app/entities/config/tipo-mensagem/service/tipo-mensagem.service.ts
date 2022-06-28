import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoMensagem, getTipoMensagemIdentifier } from '../tipo-mensagem.model';

export type EntityResponseType = HttpResponse<ITipoMensagem>;
export type EntityArrayResponseType = HttpResponse<ITipoMensagem[]>;

@Injectable({ providedIn: 'root' })
export class TipoMensagemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-mensagems');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoMensagem: ITipoMensagem): Observable<EntityResponseType> {
    return this.http.post<ITipoMensagem>(this.resourceUrl, tipoMensagem, { observe: 'response' });
  }

  update(tipoMensagem: ITipoMensagem): Observable<EntityResponseType> {
    return this.http.put<ITipoMensagem>(`${this.resourceUrl}/${getTipoMensagemIdentifier(tipoMensagem) as number}`, tipoMensagem, {
      observe: 'response',
    });
  }

  partialUpdate(tipoMensagem: ITipoMensagem): Observable<EntityResponseType> {
    return this.http.patch<ITipoMensagem>(`${this.resourceUrl}/${getTipoMensagemIdentifier(tipoMensagem) as number}`, tipoMensagem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoMensagem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoMensagem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoMensagemToCollectionIfMissing(
    tipoMensagemCollection: ITipoMensagem[],
    ...tipoMensagemsToCheck: (ITipoMensagem | null | undefined)[]
  ): ITipoMensagem[] {
    const tipoMensagems: ITipoMensagem[] = tipoMensagemsToCheck.filter(isPresent);
    if (tipoMensagems.length > 0) {
      const tipoMensagemCollectionIdentifiers = tipoMensagemCollection.map(
        tipoMensagemItem => getTipoMensagemIdentifier(tipoMensagemItem)!
      );
      const tipoMensagemsToAdd = tipoMensagems.filter(tipoMensagemItem => {
        const tipoMensagemIdentifier = getTipoMensagemIdentifier(tipoMensagemItem);
        if (tipoMensagemIdentifier == null || tipoMensagemCollectionIdentifiers.includes(tipoMensagemIdentifier)) {
          return false;
        }
        tipoMensagemCollectionIdentifiers.push(tipoMensagemIdentifier);
        return true;
      });
      return [...tipoMensagemsToAdd, ...tipoMensagemCollection];
    }
    return tipoMensagemCollection;
  }
}
