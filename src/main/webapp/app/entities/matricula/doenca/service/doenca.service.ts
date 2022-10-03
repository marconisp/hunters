import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDoenca, getDoencaIdentifier } from '../doenca.model';

export type EntityResponseType = HttpResponse<IDoenca>;
export type EntityArrayResponseType = HttpResponse<IDoenca[]>;

@Injectable({ providedIn: 'root' })
export class DoencaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/doencas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(doenca: IDoenca): Observable<EntityResponseType> {
    return this.http.post<IDoenca>(this.resourceUrl, doenca, { observe: 'response' });
  }

  update(doenca: IDoenca): Observable<EntityResponseType> {
    return this.http.put<IDoenca>(`${this.resourceUrl}/${getDoencaIdentifier(doenca) as number}`, doenca, { observe: 'response' });
  }

  partialUpdate(doenca: IDoenca): Observable<EntityResponseType> {
    return this.http.patch<IDoenca>(`${this.resourceUrl}/${getDoencaIdentifier(doenca) as number}`, doenca, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenca[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDoencaToCollectionIfMissing(doencaCollection: IDoenca[], ...doencasToCheck: (IDoenca | null | undefined)[]): IDoenca[] {
    const doencas: IDoenca[] = doencasToCheck.filter(isPresent);
    if (doencas.length > 0) {
      const doencaCollectionIdentifiers = doencaCollection.map(doencaItem => getDoencaIdentifier(doencaItem)!);
      const doencasToAdd = doencas.filter(doencaItem => {
        const doencaIdentifier = getDoencaIdentifier(doencaItem);
        if (doencaIdentifier == null || doencaCollectionIdentifiers.includes(doencaIdentifier)) {
          return false;
        }
        doencaCollectionIdentifiers.push(doencaIdentifier);
        return true;
      });
      return [...doencasToAdd, ...doencaCollection];
    }
    return doencaCollection;
  }
}
