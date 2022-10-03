import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubGrupoProduto, getSubGrupoProdutoIdentifier } from '../sub-grupo-produto.model';

export type EntityResponseType = HttpResponse<ISubGrupoProduto>;
export type EntityArrayResponseType = HttpResponse<ISubGrupoProduto[]>;

@Injectable({ providedIn: 'root' })
export class SubGrupoProdutoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sub-grupo-produtos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subGrupoProduto: ISubGrupoProduto): Observable<EntityResponseType> {
    return this.http.post<ISubGrupoProduto>(this.resourceUrl, subGrupoProduto, { observe: 'response' });
  }

  update(subGrupoProduto: ISubGrupoProduto): Observable<EntityResponseType> {
    return this.http.put<ISubGrupoProduto>(
      `${this.resourceUrl}/${getSubGrupoProdutoIdentifier(subGrupoProduto) as number}`,
      subGrupoProduto,
      { observe: 'response' }
    );
  }

  partialUpdate(subGrupoProduto: ISubGrupoProduto): Observable<EntityResponseType> {
    return this.http.patch<ISubGrupoProduto>(
      `${this.resourceUrl}/${getSubGrupoProdutoIdentifier(subGrupoProduto) as number}`,
      subGrupoProduto,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubGrupoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubGrupoProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubGrupoProdutoToCollectionIfMissing(
    subGrupoProdutoCollection: ISubGrupoProduto[],
    ...subGrupoProdutosToCheck: (ISubGrupoProduto | null | undefined)[]
  ): ISubGrupoProduto[] {
    const subGrupoProdutos: ISubGrupoProduto[] = subGrupoProdutosToCheck.filter(isPresent);
    if (subGrupoProdutos.length > 0) {
      const subGrupoProdutoCollectionIdentifiers = subGrupoProdutoCollection.map(
        subGrupoProdutoItem => getSubGrupoProdutoIdentifier(subGrupoProdutoItem)!
      );
      const subGrupoProdutosToAdd = subGrupoProdutos.filter(subGrupoProdutoItem => {
        const subGrupoProdutoIdentifier = getSubGrupoProdutoIdentifier(subGrupoProdutoItem);
        if (subGrupoProdutoIdentifier == null || subGrupoProdutoCollectionIdentifiers.includes(subGrupoProdutoIdentifier)) {
          return false;
        }
        subGrupoProdutoCollectionIdentifiers.push(subGrupoProdutoIdentifier);
        return true;
      });
      return [...subGrupoProdutosToAdd, ...subGrupoProdutoCollection];
    }
    return subGrupoProdutoCollection;
  }
}
