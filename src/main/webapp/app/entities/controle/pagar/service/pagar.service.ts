import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPagar, getPagarIdentifier } from '../pagar.model';

export type EntityResponseType = HttpResponse<IPagar>;
export type EntityArrayResponseType = HttpResponse<IPagar[]>;

@Injectable({ providedIn: 'root' })
export class PagarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pagars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pagar: IPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pagar);
    return this.http
      .post<IPagar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pagar: IPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pagar);
    return this.http
      .put<IPagar>(`${this.resourceUrl}/${getPagarIdentifier(pagar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pagar: IPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pagar);
    return this.http
      .patch<IPagar>(`${this.resourceUrl}/${getPagarIdentifier(pagar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPagar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPagar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPagarToCollectionIfMissing(pagarCollection: IPagar[], ...pagarsToCheck: (IPagar | null | undefined)[]): IPagar[] {
    const pagars: IPagar[] = pagarsToCheck.filter(isPresent);
    if (pagars.length > 0) {
      const pagarCollectionIdentifiers = pagarCollection.map(pagarItem => getPagarIdentifier(pagarItem)!);
      const pagarsToAdd = pagars.filter(pagarItem => {
        const pagarIdentifier = getPagarIdentifier(pagarItem);
        if (pagarIdentifier == null || pagarCollectionIdentifiers.includes(pagarIdentifier)) {
          return false;
        }
        pagarCollectionIdentifiers.push(pagarIdentifier);
        return true;
      });
      return [...pagarsToAdd, ...pagarCollection];
    }
    return pagarCollection;
  }

  protected convertDateFromClient(pagar: IPagar): IPagar {
    return Object.assign({}, pagar, {
      data: pagar.data?.isValid() ? pagar.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((pagar: IPagar) => {
        pagar.data = pagar.data ? dayjs(pagar.data) : undefined;
      });
    }
    return res;
  }
}
