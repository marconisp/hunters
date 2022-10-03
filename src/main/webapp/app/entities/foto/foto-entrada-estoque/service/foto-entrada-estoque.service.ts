import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoEntradaEstoque, getFotoEntradaEstoqueIdentifier } from '../foto-entrada-estoque.model';

export type EntityResponseType = HttpResponse<IFotoEntradaEstoque>;
export type EntityArrayResponseType = HttpResponse<IFotoEntradaEstoque[]>;

@Injectable({ providedIn: 'root' })
export class FotoEntradaEstoqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-entrada-estoques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoEntradaEstoque: IFotoEntradaEstoque): Observable<EntityResponseType> {
    return this.http.post<IFotoEntradaEstoque>(this.resourceUrl, fotoEntradaEstoque, { observe: 'response' });
  }

  update(fotoEntradaEstoque: IFotoEntradaEstoque): Observable<EntityResponseType> {
    return this.http.put<IFotoEntradaEstoque>(
      `${this.resourceUrl}/${getFotoEntradaEstoqueIdentifier(fotoEntradaEstoque) as number}`,
      fotoEntradaEstoque,
      { observe: 'response' }
    );
  }

  partialUpdate(fotoEntradaEstoque: IFotoEntradaEstoque): Observable<EntityResponseType> {
    return this.http.patch<IFotoEntradaEstoque>(
      `${this.resourceUrl}/${getFotoEntradaEstoqueIdentifier(fotoEntradaEstoque) as number}`,
      fotoEntradaEstoque,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoEntradaEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoEntradaEstoque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoEntradaEstoqueToCollectionIfMissing(
    fotoEntradaEstoqueCollection: IFotoEntradaEstoque[],
    ...fotoEntradaEstoquesToCheck: (IFotoEntradaEstoque | null | undefined)[]
  ): IFotoEntradaEstoque[] {
    const fotoEntradaEstoques: IFotoEntradaEstoque[] = fotoEntradaEstoquesToCheck.filter(isPresent);
    if (fotoEntradaEstoques.length > 0) {
      const fotoEntradaEstoqueCollectionIdentifiers = fotoEntradaEstoqueCollection.map(
        fotoEntradaEstoqueItem => getFotoEntradaEstoqueIdentifier(fotoEntradaEstoqueItem)!
      );
      const fotoEntradaEstoquesToAdd = fotoEntradaEstoques.filter(fotoEntradaEstoqueItem => {
        const fotoEntradaEstoqueIdentifier = getFotoEntradaEstoqueIdentifier(fotoEntradaEstoqueItem);
        if (fotoEntradaEstoqueIdentifier == null || fotoEntradaEstoqueCollectionIdentifiers.includes(fotoEntradaEstoqueIdentifier)) {
          return false;
        }
        fotoEntradaEstoqueCollectionIdentifiers.push(fotoEntradaEstoqueIdentifier);
        return true;
      });
      return [...fotoEntradaEstoquesToAdd, ...fotoEntradaEstoqueCollection];
    }
    return fotoEntradaEstoqueCollection;
  }
}
