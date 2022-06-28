import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITipoPessoa, getTipoPessoaIdentifier } from '../tipo-pessoa.model';

export type EntityResponseType = HttpResponse<ITipoPessoa>;
export type EntityArrayResponseType = HttpResponse<ITipoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class TipoPessoaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tipo-pessoas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tipoPessoa: ITipoPessoa): Observable<EntityResponseType> {
    return this.http.post<ITipoPessoa>(this.resourceUrl, tipoPessoa, { observe: 'response' });
  }

  update(tipoPessoa: ITipoPessoa): Observable<EntityResponseType> {
    return this.http.put<ITipoPessoa>(`${this.resourceUrl}/${getTipoPessoaIdentifier(tipoPessoa) as number}`, tipoPessoa, {
      observe: 'response',
    });
  }

  partialUpdate(tipoPessoa: ITipoPessoa): Observable<EntityResponseType> {
    return this.http.patch<ITipoPessoa>(`${this.resourceUrl}/${getTipoPessoaIdentifier(tipoPessoa) as number}`, tipoPessoa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTipoPessoaToCollectionIfMissing(
    tipoPessoaCollection: ITipoPessoa[],
    ...tipoPessoasToCheck: (ITipoPessoa | null | undefined)[]
  ): ITipoPessoa[] {
    const tipoPessoas: ITipoPessoa[] = tipoPessoasToCheck.filter(isPresent);
    if (tipoPessoas.length > 0) {
      const tipoPessoaCollectionIdentifiers = tipoPessoaCollection.map(tipoPessoaItem => getTipoPessoaIdentifier(tipoPessoaItem)!);
      const tipoPessoasToAdd = tipoPessoas.filter(tipoPessoaItem => {
        const tipoPessoaIdentifier = getTipoPessoaIdentifier(tipoPessoaItem);
        if (tipoPessoaIdentifier == null || tipoPessoaCollectionIdentifiers.includes(tipoPessoaIdentifier)) {
          return false;
        }
        tipoPessoaCollectionIdentifiers.push(tipoPessoaIdentifier);
        return true;
      });
      return [...tipoPessoasToAdd, ...tipoPessoaCollection];
    }
    return tipoPessoaCollection;
  }
}
