import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstoque, getEstoqueIdentifier } from '../estoque.model';

export type EntityResponseType = HttpResponse<IEstoque>;
export type EntityArrayResponseType = HttpResponse<IEstoque[]>;

@Injectable({ providedIn: 'root' })
export class EstoqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estoques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estoque: IEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estoque);
    return this.http
      .post<IEstoque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(estoque: IEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estoque);
    return this.http
      .put<IEstoque>(`${this.resourceUrl}/${getEstoqueIdentifier(estoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(estoque: IEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(estoque);
    return this.http
      .patch<IEstoque>(`${this.resourceUrl}/${getEstoqueIdentifier(estoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEstoque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEstoqueToCollectionIfMissing(estoqueCollection: IEstoque[], ...estoquesToCheck: (IEstoque | null | undefined)[]): IEstoque[] {
    const estoques: IEstoque[] = estoquesToCheck.filter(isPresent);
    if (estoques.length > 0) {
      const estoqueCollectionIdentifiers = estoqueCollection.map(estoqueItem => getEstoqueIdentifier(estoqueItem)!);
      const estoquesToAdd = estoques.filter(estoqueItem => {
        const estoqueIdentifier = getEstoqueIdentifier(estoqueItem);
        if (estoqueIdentifier == null || estoqueCollectionIdentifiers.includes(estoqueIdentifier)) {
          return false;
        }
        estoqueCollectionIdentifiers.push(estoqueIdentifier);
        return true;
      });
      return [...estoquesToAdd, ...estoqueCollection];
    }
    return estoqueCollection;
  }

  protected convertDateFromClient(estoque: IEstoque): IEstoque {
    return Object.assign({}, estoque, {
      data: estoque.data?.isValid() ? estoque.data.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? dayjs(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((estoque: IEstoque) => {
        estoque.data = estoque.data ? dayjs(estoque.data) : undefined;
      });
    }
    return res;
  }
}
