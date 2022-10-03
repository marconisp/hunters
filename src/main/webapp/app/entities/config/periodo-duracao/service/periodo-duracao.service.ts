import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriodoDuracao, getPeriodoDuracaoIdentifier } from '../periodo-duracao.model';

export type EntityResponseType = HttpResponse<IPeriodoDuracao>;
export type EntityArrayResponseType = HttpResponse<IPeriodoDuracao[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoDuracaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodo-duracaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(periodoDuracao: IPeriodoDuracao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoDuracao);
    return this.http
      .post<IPeriodoDuracao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(periodoDuracao: IPeriodoDuracao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoDuracao);
    return this.http
      .put<IPeriodoDuracao>(`${this.resourceUrl}/${getPeriodoDuracaoIdentifier(periodoDuracao) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(periodoDuracao: IPeriodoDuracao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoDuracao);
    return this.http
      .patch<IPeriodoDuracao>(`${this.resourceUrl}/${getPeriodoDuracaoIdentifier(periodoDuracao) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPeriodoDuracao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPeriodoDuracao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPeriodoDuracaoToCollectionIfMissing(
    periodoDuracaoCollection: IPeriodoDuracao[],
    ...periodoDuracaosToCheck: (IPeriodoDuracao | null | undefined)[]
  ): IPeriodoDuracao[] {
    const periodoDuracaos: IPeriodoDuracao[] = periodoDuracaosToCheck.filter(isPresent);
    if (periodoDuracaos.length > 0) {
      const periodoDuracaoCollectionIdentifiers = periodoDuracaoCollection.map(
        periodoDuracaoItem => getPeriodoDuracaoIdentifier(periodoDuracaoItem)!
      );
      const periodoDuracaosToAdd = periodoDuracaos.filter(periodoDuracaoItem => {
        const periodoDuracaoIdentifier = getPeriodoDuracaoIdentifier(periodoDuracaoItem);
        if (periodoDuracaoIdentifier == null || periodoDuracaoCollectionIdentifiers.includes(periodoDuracaoIdentifier)) {
          return false;
        }
        periodoDuracaoCollectionIdentifiers.push(periodoDuracaoIdentifier);
        return true;
      });
      return [...periodoDuracaosToAdd, ...periodoDuracaoCollection];
    }
    return periodoDuracaoCollection;
  }

  protected convertDateFromClient(periodoDuracao: IPeriodoDuracao): IPeriodoDuracao {
    return Object.assign({}, periodoDuracao, {
      dataInicio: periodoDuracao.dataInicio?.isValid() ? periodoDuracao.dataInicio.format(DATE_FORMAT) : undefined,
      dataFim: periodoDuracao.dataFim?.isValid() ? periodoDuracao.dataFim.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicio = res.body.dataInicio ? dayjs(res.body.dataInicio) : undefined;
      res.body.dataFim = res.body.dataFim ? dayjs(res.body.dataFim) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((periodoDuracao: IPeriodoDuracao) => {
        periodoDuracao.dataInicio = periodoDuracao.dataInicio ? dayjs(periodoDuracao.dataInicio) : undefined;
        periodoDuracao.dataFim = periodoDuracao.dataFim ? dayjs(periodoDuracao.dataFim) : undefined;
      });
    }
    return res;
  }
}
