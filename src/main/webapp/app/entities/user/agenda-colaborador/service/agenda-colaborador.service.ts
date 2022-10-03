import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgendaColaborador, getAgendaColaboradorIdentifier } from '../agenda-colaborador.model';

export type EntityResponseType = HttpResponse<IAgendaColaborador>;
export type EntityArrayResponseType = HttpResponse<IAgendaColaborador[]>;

@Injectable({ providedIn: 'root' })
export class AgendaColaboradorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agenda-colaboradors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(agendaColaborador: IAgendaColaborador): Observable<EntityResponseType> {
    return this.http.post<IAgendaColaborador>(this.resourceUrl, agendaColaborador, { observe: 'response' });
  }

  update(agendaColaborador: IAgendaColaborador): Observable<EntityResponseType> {
    return this.http.put<IAgendaColaborador>(
      `${this.resourceUrl}/${getAgendaColaboradorIdentifier(agendaColaborador) as number}`,
      agendaColaborador,
      { observe: 'response' }
    );
  }

  partialUpdate(agendaColaborador: IAgendaColaborador): Observable<EntityResponseType> {
    return this.http.patch<IAgendaColaborador>(
      `${this.resourceUrl}/${getAgendaColaboradorIdentifier(agendaColaborador) as number}`,
      agendaColaborador,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgendaColaborador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgendaColaborador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAgendaColaboradorToCollectionIfMissing(
    agendaColaboradorCollection: IAgendaColaborador[],
    ...agendaColaboradorsToCheck: (IAgendaColaborador | null | undefined)[]
  ): IAgendaColaborador[] {
    const agendaColaboradors: IAgendaColaborador[] = agendaColaboradorsToCheck.filter(isPresent);
    if (agendaColaboradors.length > 0) {
      const agendaColaboradorCollectionIdentifiers = agendaColaboradorCollection.map(
        agendaColaboradorItem => getAgendaColaboradorIdentifier(agendaColaboradorItem)!
      );
      const agendaColaboradorsToAdd = agendaColaboradors.filter(agendaColaboradorItem => {
        const agendaColaboradorIdentifier = getAgendaColaboradorIdentifier(agendaColaboradorItem);
        if (agendaColaboradorIdentifier == null || agendaColaboradorCollectionIdentifiers.includes(agendaColaboradorIdentifier)) {
          return false;
        }
        agendaColaboradorCollectionIdentifiers.push(agendaColaboradorIdentifier);
        return true;
      });
      return [...agendaColaboradorsToAdd, ...agendaColaboradorCollection];
    }
    return agendaColaboradorCollection;
  }
}
