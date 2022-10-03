import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoReceber, getFotoReceberIdentifier } from '../foto-receber.model';

export type EntityResponseType = HttpResponse<IFotoReceber>;
export type EntityArrayResponseType = HttpResponse<IFotoReceber[]>;

@Injectable({ providedIn: 'root' })
export class FotoReceberService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-recebers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoReceber: IFotoReceber): Observable<EntityResponseType> {
    return this.http.post<IFotoReceber>(this.resourceUrl, fotoReceber, { observe: 'response' });
  }

  update(fotoReceber: IFotoReceber): Observable<EntityResponseType> {
    return this.http.put<IFotoReceber>(`${this.resourceUrl}/${getFotoReceberIdentifier(fotoReceber) as number}`, fotoReceber, {
      observe: 'response',
    });
  }

  partialUpdate(fotoReceber: IFotoReceber): Observable<EntityResponseType> {
    return this.http.patch<IFotoReceber>(`${this.resourceUrl}/${getFotoReceberIdentifier(fotoReceber) as number}`, fotoReceber, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoReceber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoReceber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAllByReceberId(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoReceber[]>(`${this.resourceUrl}/receber/${id}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoReceberToCollectionIfMissing(
    fotoReceberCollection: IFotoReceber[],
    ...fotoRecebersToCheck: (IFotoReceber | null | undefined)[]
  ): IFotoReceber[] {
    const fotoRecebers: IFotoReceber[] = fotoRecebersToCheck.filter(isPresent);
    if (fotoRecebers.length > 0) {
      const fotoReceberCollectionIdentifiers = fotoReceberCollection.map(fotoReceberItem => getFotoReceberIdentifier(fotoReceberItem)!);
      const fotoRecebersToAdd = fotoRecebers.filter(fotoReceberItem => {
        const fotoReceberIdentifier = getFotoReceberIdentifier(fotoReceberItem);
        if (fotoReceberIdentifier == null || fotoReceberCollectionIdentifiers.includes(fotoReceberIdentifier)) {
          return false;
        }
        fotoReceberCollectionIdentifiers.push(fotoReceberIdentifier);
        return true;
      });
      return [...fotoRecebersToAdd, ...fotoReceberCollection];
    }
    return fotoReceberCollection;
  }
}
