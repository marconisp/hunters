import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRaca, getRacaIdentifier } from '../raca.model';

export type EntityResponseType = HttpResponse<IRaca>;
export type EntityArrayResponseType = HttpResponse<IRaca[]>;

@Injectable({ providedIn: 'root' })
export class RacaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/racas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(raca: IRaca): Observable<EntityResponseType> {
    return this.http.post<IRaca>(this.resourceUrl, raca, { observe: 'response' });
  }

  update(raca: IRaca): Observable<EntityResponseType> {
    return this.http.put<IRaca>(`${this.resourceUrl}/${getRacaIdentifier(raca) as number}`, raca, { observe: 'response' });
  }

  partialUpdate(raca: IRaca): Observable<EntityResponseType> {
    return this.http.patch<IRaca>(`${this.resourceUrl}/${getRacaIdentifier(raca) as number}`, raca, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRaca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRaca[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRacaToCollectionIfMissing(racaCollection: IRaca[], ...racasToCheck: (IRaca | null | undefined)[]): IRaca[] {
    const racas: IRaca[] = racasToCheck.filter(isPresent);
    if (racas.length > 0) {
      const racaCollectionIdentifiers = racaCollection.map(racaItem => getRacaIdentifier(racaItem)!);
      const racasToAdd = racas.filter(racaItem => {
        const racaIdentifier = getRacaIdentifier(racaItem);
        if (racaIdentifier == null || racaCollectionIdentifiers.includes(racaIdentifier)) {
          return false;
        }
        racaCollectionIdentifiers.push(racaIdentifier);
        return true;
      });
      return [...racasToAdd, ...racaCollection];
    }
    return racaCollection;
  }
}
