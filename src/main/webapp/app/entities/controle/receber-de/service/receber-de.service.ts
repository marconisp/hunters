import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceberDe, getReceberDeIdentifier } from '../receber-de.model';

export type EntityResponseType = HttpResponse<IReceberDe>;
export type EntityArrayResponseType = HttpResponse<IReceberDe[]>;

@Injectable({ providedIn: 'root' })
export class ReceberDeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/receber-des');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(receberDe: IReceberDe): Observable<EntityResponseType> {
    return this.http.post<IReceberDe>(this.resourceUrl, receberDe, { observe: 'response' });
  }

  update(receberDe: IReceberDe): Observable<EntityResponseType> {
    return this.http.put<IReceberDe>(`${this.resourceUrl}/${getReceberDeIdentifier(receberDe) as number}`, receberDe, {
      observe: 'response',
    });
  }

  partialUpdate(receberDe: IReceberDe): Observable<EntityResponseType> {
    return this.http.patch<IReceberDe>(`${this.resourceUrl}/${getReceberDeIdentifier(receberDe) as number}`, receberDe, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReceberDe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReceberDe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReceberDeToCollectionIfMissing(
    receberDeCollection: IReceberDe[],
    ...receberDesToCheck: (IReceberDe | null | undefined)[]
  ): IReceberDe[] {
    const receberDes: IReceberDe[] = receberDesToCheck.filter(isPresent);
    if (receberDes.length > 0) {
      const receberDeCollectionIdentifiers = receberDeCollection.map(receberDeItem => getReceberDeIdentifier(receberDeItem)!);
      const receberDesToAdd = receberDes.filter(receberDeItem => {
        const receberDeIdentifier = getReceberDeIdentifier(receberDeItem);
        if (receberDeIdentifier == null || receberDeCollectionIdentifiers.includes(receberDeIdentifier)) {
          return false;
        }
        receberDeCollectionIdentifiers.push(receberDeIdentifier);
        return true;
      });
      return [...receberDesToAdd, ...receberDeCollection];
    }
    return receberDeCollection;
  }
}
