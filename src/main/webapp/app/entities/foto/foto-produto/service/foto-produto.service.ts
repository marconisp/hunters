import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoProduto, getFotoProdutoIdentifier } from '../foto-produto.model';

export type EntityResponseType = HttpResponse<IFotoProduto>;
export type EntityArrayResponseType = HttpResponse<IFotoProduto[]>;

@Injectable({ providedIn: 'root' })
export class FotoProdutoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-produtos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoProduto: IFotoProduto): Observable<EntityResponseType> {
    return this.http.post<IFotoProduto>(this.resourceUrl, fotoProduto, { observe: 'response' });
  }

  update(fotoProduto: IFotoProduto): Observable<EntityResponseType> {
    return this.http.put<IFotoProduto>(`${this.resourceUrl}/${getFotoProdutoIdentifier(fotoProduto) as number}`, fotoProduto, {
      observe: 'response',
    });
  }

  partialUpdate(fotoProduto: IFotoProduto): Observable<EntityResponseType> {
    return this.http.patch<IFotoProduto>(`${this.resourceUrl}/${getFotoProdutoIdentifier(fotoProduto) as number}`, fotoProduto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoProduto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoProdutoToCollectionIfMissing(
    fotoProdutoCollection: IFotoProduto[],
    ...fotoProdutosToCheck: (IFotoProduto | null | undefined)[]
  ): IFotoProduto[] {
    const fotoProdutos: IFotoProduto[] = fotoProdutosToCheck.filter(isPresent);
    if (fotoProdutos.length > 0) {
      const fotoProdutoCollectionIdentifiers = fotoProdutoCollection.map(fotoProdutoItem => getFotoProdutoIdentifier(fotoProdutoItem)!);
      const fotoProdutosToAdd = fotoProdutos.filter(fotoProdutoItem => {
        const fotoProdutoIdentifier = getFotoProdutoIdentifier(fotoProdutoItem);
        if (fotoProdutoIdentifier == null || fotoProdutoCollectionIdentifiers.includes(fotoProdutoIdentifier)) {
          return false;
        }
        fotoProdutoCollectionIdentifiers.push(fotoProdutoIdentifier);
        return true;
      });
      return [...fotoProdutosToAdd, ...fotoProdutoCollection];
    }
    return fotoProdutoCollection;
  }
}
