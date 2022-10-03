import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoProduto, getGrupoProdutoIdentifier } from '../grupo-produto.model';

export type EntityResponseType = HttpResponse<IGrupoProduto>;
export type EntityArrayResponseType = HttpResponse<IGrupoProduto[]>;

@Injectable({ providedIn: 'root' })
export class GrupoProdutoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-produtos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(grupoProduto: IGrupoProduto): Observable<EntityResponseType> {
    return this.http.post<IGrupoProduto>(this.resourceUrl, grupoProduto, { observe: 'response' });
  }

  update(grupoProduto: IGrupoProduto): Observable<EntityResponseType> {
    return this.http.put<IGrupoProduto>(`${this.resourceUrl}/${getGrupoProdutoIdentifier(grupoProduto) as number}`, grupoProduto, {
      observe: 'response',
    });
  }

  partialUpdate(grupoProduto: IGrupoProduto): Observable<EntityResponseType> {
    return this.http.patch<IGrupoProduto>(`${this.resourceUrl}/${getGrupoProdutoIdentifier(grupoProduto) as number}`, grupoProduto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGrupoProdutoToCollectionIfMissing(
    grupoProdutoCollection: IGrupoProduto[],
    ...grupoProdutosToCheck: (IGrupoProduto | null | undefined)[]
  ): IGrupoProduto[] {
    const grupoProdutos: IGrupoProduto[] = grupoProdutosToCheck.filter(isPresent);
    if (grupoProdutos.length > 0) {
      const grupoProdutoCollectionIdentifiers = grupoProdutoCollection.map(
        grupoProdutoItem => getGrupoProdutoIdentifier(grupoProdutoItem)!
      );
      const grupoProdutosToAdd = grupoProdutos.filter(grupoProdutoItem => {
        const grupoProdutoIdentifier = getGrupoProdutoIdentifier(grupoProdutoItem);
        if (grupoProdutoIdentifier == null || grupoProdutoCollectionIdentifiers.includes(grupoProdutoIdentifier)) {
          return false;
        }
        grupoProdutoCollectionIdentifiers.push(grupoProdutoIdentifier);
        return true;
      });
      return [...grupoProdutosToAdd, ...grupoProdutoCollection];
    }
    return grupoProdutoCollection;
  }
}
