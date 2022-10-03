import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceber, getReceberIdentifier } from '../receber.model';
import { IFiltroReceber } from '../FiltroReceber.model';

export type EntityResponseType = HttpResponse<IReceber>;
export type EntityArrayResponseType = HttpResponse<IReceber[]>;

@Injectable({ providedIn: 'root' })
export class ReceberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recebers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(receber: IReceber): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receber);
    return this.http
      .post<IReceber>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(receber: IReceber): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receber);
    return this.http
      .put<IReceber>(`${this.resourceUrl}/${getReceberIdentifier(receber) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(receber: IReceber): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receber);
    return this.http
      .patch<IReceber>(`${this.resourceUrl}/${getReceberIdentifier(receber) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReceber>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReceber[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  print(filtro: IFiltroReceber): Observable<HttpResponse<any>> {
    const copy = this.convertDateIncioFimFromClient(filtro);
    return this.http.post(`${this.resourceUrl}/report/periodo/jasper`, copy, { observe: 'response', responseType: 'blob' });
  }

  addReceberToCollectionIfMissing(receberCollection: IReceber[], ...recebersToCheck: (IReceber | null | undefined)[]): IReceber[] {
    const recebers: IReceber[] = recebersToCheck.filter(isPresent);
    if (recebers.length > 0) {
      const receberCollectionIdentifiers = receberCollection.map(receberItem => getReceberIdentifier(receberItem)!);
      const recebersToAdd = recebers.filter(receberItem => {
        const receberIdentifier = getReceberIdentifier(receberItem);
        if (receberIdentifier == null || receberCollectionIdentifiers.includes(receberIdentifier)) {
          return false;
        }
        receberCollectionIdentifiers.push(receberIdentifier);
        return true;
      });
      return [...recebersToAdd, ...receberCollection];
    }
    return receberCollection;
  }

  protected convertDateFromClient(receber: IReceber): IReceber {
    return Object.assign({}, receber, {
      data: receber.data?.isValid() ? receber.data.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateIncioFimFromClient(filtro: IFiltroReceber): IFiltroReceber {
    return Object.assign({}, filtro, {
      dataInicio: filtro.dataInicio?.isValid() ? filtro.dataInicio.format(DATE_FORMAT) : undefined,
      dataFim: filtro.dataFim?.isValid() ? filtro.dataFim.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((receber: IReceber) => {
        receber.data = receber.data ? dayjs(receber.data) : undefined;
      });
    }
    return res;
  }
}
