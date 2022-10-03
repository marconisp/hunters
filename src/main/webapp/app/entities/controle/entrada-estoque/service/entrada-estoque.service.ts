import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntradaEstoque, getEntradaEstoqueIdentifier } from '../entrada-estoque.model';

export type EntityResponseType = HttpResponse<IEntradaEstoque>;
export type EntityArrayResponseType = HttpResponse<IEntradaEstoque[]>;

@Injectable({ providedIn: 'root' })
export class EntradaEstoqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entrada-estoques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(entradaEstoque: IEntradaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entradaEstoque);
    return this.http
      .post<IEntradaEstoque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(entradaEstoque: IEntradaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entradaEstoque);
    return this.http
      .put<IEntradaEstoque>(`${this.resourceUrl}/${getEntradaEstoqueIdentifier(entradaEstoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(entradaEstoque: IEntradaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entradaEstoque);
    return this.http
      .patch<IEntradaEstoque>(`${this.resourceUrl}/${getEntradaEstoqueIdentifier(entradaEstoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEntradaEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEntradaEstoque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEntradaEstoqueToCollectionIfMissing(
    entradaEstoqueCollection: IEntradaEstoque[],
    ...entradaEstoquesToCheck: (IEntradaEstoque | null | undefined)[]
  ): IEntradaEstoque[] {
    const entradaEstoques: IEntradaEstoque[] = entradaEstoquesToCheck.filter(isPresent);
    if (entradaEstoques.length > 0) {
      const entradaEstoqueCollectionIdentifiers = entradaEstoqueCollection.map(
        entradaEstoqueItem => getEntradaEstoqueIdentifier(entradaEstoqueItem)!
      );
      const entradaEstoquesToAdd = entradaEstoques.filter(entradaEstoqueItem => {
        const entradaEstoqueIdentifier = getEntradaEstoqueIdentifier(entradaEstoqueItem);
        if (entradaEstoqueIdentifier == null || entradaEstoqueCollectionIdentifiers.includes(entradaEstoqueIdentifier)) {
          return false;
        }
        entradaEstoqueCollectionIdentifiers.push(entradaEstoqueIdentifier);
        return true;
      });
      return [...entradaEstoquesToAdd, ...entradaEstoqueCollection];
    }
    return entradaEstoqueCollection;
  }

  protected convertDateFromClient(entradaEstoque: IEntradaEstoque): IEntradaEstoque {
    return Object.assign({}, entradaEstoque, {
      data: entradaEstoque.data?.isValid() ? entradaEstoque.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((entradaEstoque: IEntradaEstoque) => {
        entradaEstoque.data = entradaEstoque.data ? dayjs(entradaEstoque.data) : undefined;
      });
    }
    return res;
  }
}
