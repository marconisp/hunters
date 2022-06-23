import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDadosPessoais, getDadosPessoaisIdentifier } from '../dados-pessoais.model';

export type EntityResponseType = HttpResponse<IDadosPessoais>;
export type EntityArrayResponseType = HttpResponse<IDadosPessoais[]>;

@Injectable({ providedIn: 'root' })
export class DadosPessoaisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dados-pessoais');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dadosPessoais: IDadosPessoais): Observable<EntityResponseType> {
    return this.http.post<IDadosPessoais>(this.resourceUrl, dadosPessoais, { observe: 'response' });
  }

  update(dadosPessoais: IDadosPessoais): Observable<EntityResponseType> {
    return this.http.put<IDadosPessoais>(`${this.resourceUrl}/${getDadosPessoaisIdentifier(dadosPessoais) as number}`, dadosPessoais, {
      observe: 'response',
    });
  }

  partialUpdate(dadosPessoais: IDadosPessoais): Observable<EntityResponseType> {
    return this.http.patch<IDadosPessoais>(`${this.resourceUrl}/${getDadosPessoaisIdentifier(dadosPessoais) as number}`, dadosPessoais, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDadosPessoais>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDadosPessoais[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDadosPessoaisToCollectionIfMissing(
    dadosPessoaisCollection: IDadosPessoais[],
    ...dadosPessoaisToCheck: (IDadosPessoais | null | undefined)[]
  ): IDadosPessoais[] {
    const dadosPessoais: IDadosPessoais[] = dadosPessoaisToCheck.filter(isPresent);
    if (dadosPessoais.length > 0) {
      const dadosPessoaisCollectionIdentifiers = dadosPessoaisCollection.map(
        dadosPessoaisItem => getDadosPessoaisIdentifier(dadosPessoaisItem)!
      );
      const dadosPessoaisToAdd = dadosPessoais.filter(dadosPessoaisItem => {
        const dadosPessoaisIdentifier = getDadosPessoaisIdentifier(dadosPessoaisItem);
        if (dadosPessoaisIdentifier == null || dadosPessoaisCollectionIdentifiers.includes(dadosPessoaisIdentifier)) {
          return false;
        }
        dadosPessoaisCollectionIdentifiers.push(dadosPessoaisIdentifier);
        return true;
      });
      return [...dadosPessoaisToAdd, ...dadosPessoaisCollection];
    }
    return dadosPessoaisCollection;
  }
}
