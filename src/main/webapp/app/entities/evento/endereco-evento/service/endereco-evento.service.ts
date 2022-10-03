import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnderecoEvento, getEnderecoEventoIdentifier } from '../endereco-evento.model';

export type EntityResponseType = HttpResponse<IEnderecoEvento>;
export type EntityArrayResponseType = HttpResponse<IEnderecoEvento[]>;

@Injectable({ providedIn: 'root' })
export class EnderecoEventoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/endereco-eventos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(enderecoEvento: IEnderecoEvento): Observable<EntityResponseType> {
    return this.http.post<IEnderecoEvento>(this.resourceUrl, enderecoEvento, { observe: 'response' });
  }

  update(enderecoEvento: IEnderecoEvento): Observable<EntityResponseType> {
    return this.http.put<IEnderecoEvento>(`${this.resourceUrl}/${getEnderecoEventoIdentifier(enderecoEvento) as number}`, enderecoEvento, {
      observe: 'response',
    });
  }

  partialUpdate(enderecoEvento: IEnderecoEvento): Observable<EntityResponseType> {
    return this.http.patch<IEnderecoEvento>(
      `${this.resourceUrl}/${getEnderecoEventoIdentifier(enderecoEvento) as number}`,
      enderecoEvento,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnderecoEvento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnderecoEvento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEnderecoEventoToCollectionIfMissing(
    enderecoEventoCollection: IEnderecoEvento[],
    ...enderecoEventosToCheck: (IEnderecoEvento | null | undefined)[]
  ): IEnderecoEvento[] {
    const enderecoEventos: IEnderecoEvento[] = enderecoEventosToCheck.filter(isPresent);
    if (enderecoEventos.length > 0) {
      const enderecoEventoCollectionIdentifiers = enderecoEventoCollection.map(
        enderecoEventoItem => getEnderecoEventoIdentifier(enderecoEventoItem)!
      );
      const enderecoEventosToAdd = enderecoEventos.filter(enderecoEventoItem => {
        const enderecoEventoIdentifier = getEnderecoEventoIdentifier(enderecoEventoItem);
        if (enderecoEventoIdentifier == null || enderecoEventoCollectionIdentifiers.includes(enderecoEventoIdentifier)) {
          return false;
        }
        enderecoEventoCollectionIdentifiers.push(enderecoEventoIdentifier);
        return true;
      });
      return [...enderecoEventosToAdd, ...enderecoEventoCollection];
    }
    return enderecoEventoCollection;
  }
}
