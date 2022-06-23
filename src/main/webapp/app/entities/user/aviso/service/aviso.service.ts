import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAviso, getAvisoIdentifier } from '../aviso.model';

export type EntityResponseType = HttpResponse<IAviso>;
export type EntityArrayResponseType = HttpResponse<IAviso[]>;

@Injectable({ providedIn: 'root' })
export class AvisoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avisos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(aviso: IAviso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aviso);
    return this.http
      .post<IAviso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aviso: IAviso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aviso);
    return this.http
      .put<IAviso>(`${this.resourceUrl}/${getAvisoIdentifier(aviso) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(aviso: IAviso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aviso);
    return this.http
      .patch<IAviso>(`${this.resourceUrl}/${getAvisoIdentifier(aviso) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAviso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAviso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAvisoToCollectionIfMissing(avisoCollection: IAviso[], ...avisosToCheck: (IAviso | null | undefined)[]): IAviso[] {
    const avisos: IAviso[] = avisosToCheck.filter(isPresent);
    if (avisos.length > 0) {
      const avisoCollectionIdentifiers = avisoCollection.map(avisoItem => getAvisoIdentifier(avisoItem)!);
      const avisosToAdd = avisos.filter(avisoItem => {
        const avisoIdentifier = getAvisoIdentifier(avisoItem);
        if (avisoIdentifier == null || avisoCollectionIdentifiers.includes(avisoIdentifier)) {
          return false;
        }
        avisoCollectionIdentifiers.push(avisoIdentifier);
        return true;
      });
      return [...avisosToAdd, ...avisoCollection];
    }
    return avisoCollection;
  }

  protected convertDateFromClient(aviso: IAviso): IAviso {
    return Object.assign({}, aviso, {
      data: aviso.data?.isValid() ? aviso.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((aviso: IAviso) => {
        aviso.data = aviso.data ? dayjs(aviso.data) : undefined;
      });
    }
    return res;
  }
}
