import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoSaidaEstoque, getFotoSaidaEstoqueIdentifier } from '../foto-saida-estoque.model';

export type EntityResponseType = HttpResponse<IFotoSaidaEstoque>;
export type EntityArrayResponseType = HttpResponse<IFotoSaidaEstoque[]>;

@Injectable({ providedIn: 'root' })
export class FotoSaidaEstoqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-saida-estoques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoSaidaEstoque: IFotoSaidaEstoque): Observable<EntityResponseType> {
    return this.http.post<IFotoSaidaEstoque>(this.resourceUrl, fotoSaidaEstoque, { observe: 'response' });
  }

  update(fotoSaidaEstoque: IFotoSaidaEstoque): Observable<EntityResponseType> {
    return this.http.put<IFotoSaidaEstoque>(
      `${this.resourceUrl}/${getFotoSaidaEstoqueIdentifier(fotoSaidaEstoque) as number}`,
      fotoSaidaEstoque,
      { observe: 'response' }
    );
  }

  partialUpdate(fotoSaidaEstoque: IFotoSaidaEstoque): Observable<EntityResponseType> {
    return this.http.patch<IFotoSaidaEstoque>(
      `${this.resourceUrl}/${getFotoSaidaEstoqueIdentifier(fotoSaidaEstoque) as number}`,
      fotoSaidaEstoque,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoSaidaEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoSaidaEstoque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoSaidaEstoqueToCollectionIfMissing(
    fotoSaidaEstoqueCollection: IFotoSaidaEstoque[],
    ...fotoSaidaEstoquesToCheck: (IFotoSaidaEstoque | null | undefined)[]
  ): IFotoSaidaEstoque[] {
    const fotoSaidaEstoques: IFotoSaidaEstoque[] = fotoSaidaEstoquesToCheck.filter(isPresent);
    if (fotoSaidaEstoques.length > 0) {
      const fotoSaidaEstoqueCollectionIdentifiers = fotoSaidaEstoqueCollection.map(
        fotoSaidaEstoqueItem => getFotoSaidaEstoqueIdentifier(fotoSaidaEstoqueItem)!
      );
      const fotoSaidaEstoquesToAdd = fotoSaidaEstoques.filter(fotoSaidaEstoqueItem => {
        const fotoSaidaEstoqueIdentifier = getFotoSaidaEstoqueIdentifier(fotoSaidaEstoqueItem);
        if (fotoSaidaEstoqueIdentifier == null || fotoSaidaEstoqueCollectionIdentifiers.includes(fotoSaidaEstoqueIdentifier)) {
          return false;
        }
        fotoSaidaEstoqueCollectionIdentifiers.push(fotoSaidaEstoqueIdentifier);
        return true;
      });
      return [...fotoSaidaEstoquesToAdd, ...fotoSaidaEstoqueCollection];
    }
    return fotoSaidaEstoqueCollection;
  }
}
