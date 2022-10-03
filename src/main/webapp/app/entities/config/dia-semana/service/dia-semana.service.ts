import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiaSemana, getDiaSemanaIdentifier } from '../dia-semana.model';

export type EntityResponseType = HttpResponse<IDiaSemana>;
export type EntityArrayResponseType = HttpResponse<IDiaSemana[]>;

@Injectable({ providedIn: 'root' })
export class DiaSemanaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dia-semanas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(diaSemana: IDiaSemana): Observable<EntityResponseType> {
    return this.http.post<IDiaSemana>(this.resourceUrl, diaSemana, { observe: 'response' });
  }

  update(diaSemana: IDiaSemana): Observable<EntityResponseType> {
    return this.http.put<IDiaSemana>(`${this.resourceUrl}/${getDiaSemanaIdentifier(diaSemana) as number}`, diaSemana, {
      observe: 'response',
    });
  }

  partialUpdate(diaSemana: IDiaSemana): Observable<EntityResponseType> {
    return this.http.patch<IDiaSemana>(`${this.resourceUrl}/${getDiaSemanaIdentifier(diaSemana) as number}`, diaSemana, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiaSemana>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiaSemana[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDiaSemanaToCollectionIfMissing(
    diaSemanaCollection: IDiaSemana[],
    ...diaSemanasToCheck: (IDiaSemana | null | undefined)[]
  ): IDiaSemana[] {
    const diaSemanas: IDiaSemana[] = diaSemanasToCheck.filter(isPresent);
    if (diaSemanas.length > 0) {
      const diaSemanaCollectionIdentifiers = diaSemanaCollection.map(diaSemanaItem => getDiaSemanaIdentifier(diaSemanaItem)!);
      const diaSemanasToAdd = diaSemanas.filter(diaSemanaItem => {
        const diaSemanaIdentifier = getDiaSemanaIdentifier(diaSemanaItem);
        if (diaSemanaIdentifier == null || diaSemanaCollectionIdentifiers.includes(diaSemanaIdentifier)) {
          return false;
        }
        diaSemanaCollectionIdentifiers.push(diaSemanaIdentifier);
        return true;
      });
      return [...diaSemanasToAdd, ...diaSemanaCollection];
    }
    return diaSemanaCollection;
  }
}
