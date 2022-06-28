import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReligiao, getReligiaoIdentifier } from '../religiao.model';

export type EntityResponseType = HttpResponse<IReligiao>;
export type EntityArrayResponseType = HttpResponse<IReligiao[]>;

@Injectable({ providedIn: 'root' })
export class ReligiaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/religiaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(religiao: IReligiao): Observable<EntityResponseType> {
    return this.http.post<IReligiao>(this.resourceUrl, religiao, { observe: 'response' });
  }

  update(religiao: IReligiao): Observable<EntityResponseType> {
    return this.http.put<IReligiao>(`${this.resourceUrl}/${getReligiaoIdentifier(religiao) as number}`, religiao, { observe: 'response' });
  }

  partialUpdate(religiao: IReligiao): Observable<EntityResponseType> {
    return this.http.patch<IReligiao>(`${this.resourceUrl}/${getReligiaoIdentifier(religiao) as number}`, religiao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReligiao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReligiao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addReligiaoToCollectionIfMissing(religiaoCollection: IReligiao[], ...religiaosToCheck: (IReligiao | null | undefined)[]): IReligiao[] {
    const religiaos: IReligiao[] = religiaosToCheck.filter(isPresent);
    if (religiaos.length > 0) {
      const religiaoCollectionIdentifiers = religiaoCollection.map(religiaoItem => getReligiaoIdentifier(religiaoItem)!);
      const religiaosToAdd = religiaos.filter(religiaoItem => {
        const religiaoIdentifier = getReligiaoIdentifier(religiaoItem);
        if (religiaoIdentifier == null || religiaoCollectionIdentifiers.includes(religiaoIdentifier)) {
          return false;
        }
        religiaoCollectionIdentifiers.push(religiaoIdentifier);
        return true;
      });
      return [...religiaosToAdd, ...religiaoCollection];
    }
    return religiaoCollection;
  }
}
