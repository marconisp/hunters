import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaidaEstoque, getSaidaEstoqueIdentifier } from '../saida-estoque.model';

export type EntityResponseType = HttpResponse<ISaidaEstoque>;
export type EntityArrayResponseType = HttpResponse<ISaidaEstoque[]>;

@Injectable({ providedIn: 'root' })
export class SaidaEstoqueService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saida-estoques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(saidaEstoque: ISaidaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saidaEstoque);
    return this.http
      .post<ISaidaEstoque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(saidaEstoque: ISaidaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saidaEstoque);
    return this.http
      .put<ISaidaEstoque>(`${this.resourceUrl}/${getSaidaEstoqueIdentifier(saidaEstoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(saidaEstoque: ISaidaEstoque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saidaEstoque);
    return this.http
      .patch<ISaidaEstoque>(`${this.resourceUrl}/${getSaidaEstoqueIdentifier(saidaEstoque) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISaidaEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISaidaEstoque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSaidaEstoqueToCollectionIfMissing(
    saidaEstoqueCollection: ISaidaEstoque[],
    ...saidaEstoquesToCheck: (ISaidaEstoque | null | undefined)[]
  ): ISaidaEstoque[] {
    const saidaEstoques: ISaidaEstoque[] = saidaEstoquesToCheck.filter(isPresent);
    if (saidaEstoques.length > 0) {
      const saidaEstoqueCollectionIdentifiers = saidaEstoqueCollection.map(
        saidaEstoqueItem => getSaidaEstoqueIdentifier(saidaEstoqueItem)!
      );
      const saidaEstoquesToAdd = saidaEstoques.filter(saidaEstoqueItem => {
        const saidaEstoqueIdentifier = getSaidaEstoqueIdentifier(saidaEstoqueItem);
        if (saidaEstoqueIdentifier == null || saidaEstoqueCollectionIdentifiers.includes(saidaEstoqueIdentifier)) {
          return false;
        }
        saidaEstoqueCollectionIdentifiers.push(saidaEstoqueIdentifier);
        return true;
      });
      return [...saidaEstoquesToAdd, ...saidaEstoqueCollection];
    }
    return saidaEstoqueCollection;
  }

  protected convertDateFromClient(saidaEstoque: ISaidaEstoque): ISaidaEstoque {
    return Object.assign({}, saidaEstoque, {
      data: saidaEstoque.data?.isValid() ? saidaEstoque.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((saidaEstoque: ISaidaEstoque) => {
        saidaEstoque.data = saidaEstoque.data ? dayjs(saidaEstoque.data) : undefined;
      });
    }
    return res;
  }
}
