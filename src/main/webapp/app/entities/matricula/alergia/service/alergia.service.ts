import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlergia, getAlergiaIdentifier } from '../alergia.model';

export type EntityResponseType = HttpResponse<IAlergia>;
export type EntityArrayResponseType = HttpResponse<IAlergia[]>;

@Injectable({ providedIn: 'root' })
export class AlergiaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/alergias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(alergia: IAlergia): Observable<EntityResponseType> {
    return this.http.post<IAlergia>(this.resourceUrl, alergia, { observe: 'response' });
  }

  update(alergia: IAlergia): Observable<EntityResponseType> {
    return this.http.put<IAlergia>(`${this.resourceUrl}/${getAlergiaIdentifier(alergia) as number}`, alergia, { observe: 'response' });
  }

  partialUpdate(alergia: IAlergia): Observable<EntityResponseType> {
    return this.http.patch<IAlergia>(`${this.resourceUrl}/${getAlergiaIdentifier(alergia) as number}`, alergia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlergia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlergia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAlergiaToCollectionIfMissing(alergiaCollection: IAlergia[], ...alergiasToCheck: (IAlergia | null | undefined)[]): IAlergia[] {
    const alergias: IAlergia[] = alergiasToCheck.filter(isPresent);
    if (alergias.length > 0) {
      const alergiaCollectionIdentifiers = alergiaCollection.map(alergiaItem => getAlergiaIdentifier(alergiaItem)!);
      const alergiasToAdd = alergias.filter(alergiaItem => {
        const alergiaIdentifier = getAlergiaIdentifier(alergiaItem);
        if (alergiaIdentifier == null || alergiaCollectionIdentifiers.includes(alergiaIdentifier)) {
          return false;
        }
        alergiaCollectionIdentifiers.push(alergiaIdentifier);
        return true;
      });
      return [...alergiasToAdd, ...alergiaCollection];
    }
    return alergiaCollection;
  }
}
