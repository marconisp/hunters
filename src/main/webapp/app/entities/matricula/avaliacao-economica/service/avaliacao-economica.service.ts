import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvaliacaoEconomica, getAvaliacaoEconomicaIdentifier } from '../avaliacao-economica.model';

export type EntityResponseType = HttpResponse<IAvaliacaoEconomica>;
export type EntityArrayResponseType = HttpResponse<IAvaliacaoEconomica[]>;

@Injectable({ providedIn: 'root' })
export class AvaliacaoEconomicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avaliacao-economicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(avaliacaoEconomica: IAvaliacaoEconomica): Observable<EntityResponseType> {
    return this.http.post<IAvaliacaoEconomica>(this.resourceUrl, avaliacaoEconomica, { observe: 'response' });
  }

  update(avaliacaoEconomica: IAvaliacaoEconomica): Observable<EntityResponseType> {
    return this.http.put<IAvaliacaoEconomica>(
      `${this.resourceUrl}/${getAvaliacaoEconomicaIdentifier(avaliacaoEconomica) as number}`,
      avaliacaoEconomica,
      { observe: 'response' }
    );
  }

  partialUpdate(avaliacaoEconomica: IAvaliacaoEconomica): Observable<EntityResponseType> {
    return this.http.patch<IAvaliacaoEconomica>(
      `${this.resourceUrl}/${getAvaliacaoEconomicaIdentifier(avaliacaoEconomica) as number}`,
      avaliacaoEconomica,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvaliacaoEconomica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvaliacaoEconomica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAvaliacaoEconomicaToCollectionIfMissing(
    avaliacaoEconomicaCollection: IAvaliacaoEconomica[],
    ...avaliacaoEconomicasToCheck: (IAvaliacaoEconomica | null | undefined)[]
  ): IAvaliacaoEconomica[] {
    const avaliacaoEconomicas: IAvaliacaoEconomica[] = avaliacaoEconomicasToCheck.filter(isPresent);
    if (avaliacaoEconomicas.length > 0) {
      const avaliacaoEconomicaCollectionIdentifiers = avaliacaoEconomicaCollection.map(
        avaliacaoEconomicaItem => getAvaliacaoEconomicaIdentifier(avaliacaoEconomicaItem)!
      );
      const avaliacaoEconomicasToAdd = avaliacaoEconomicas.filter(avaliacaoEconomicaItem => {
        const avaliacaoEconomicaIdentifier = getAvaliacaoEconomicaIdentifier(avaliacaoEconomicaItem);
        if (avaliacaoEconomicaIdentifier == null || avaliacaoEconomicaCollectionIdentifiers.includes(avaliacaoEconomicaIdentifier)) {
          return false;
        }
        avaliacaoEconomicaCollectionIdentifiers.push(avaliacaoEconomicaIdentifier);
        return true;
      });
      return [...avaliacaoEconomicasToAdd, ...avaliacaoEconomicaCollection];
    }
    return avaliacaoEconomicaCollection;
  }
}
