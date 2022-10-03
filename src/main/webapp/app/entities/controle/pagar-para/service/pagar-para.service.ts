import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPagarPara, getPagarParaIdentifier } from '../pagar-para.model';

export type EntityResponseType = HttpResponse<IPagarPara>;
export type EntityArrayResponseType = HttpResponse<IPagarPara[]>;

@Injectable({ providedIn: 'root' })
export class PagarParaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pagar-paras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pagarPara: IPagarPara): Observable<EntityResponseType> {
    return this.http.post<IPagarPara>(this.resourceUrl, pagarPara, { observe: 'response' });
  }

  update(pagarPara: IPagarPara): Observable<EntityResponseType> {
    return this.http.put<IPagarPara>(`${this.resourceUrl}/${getPagarParaIdentifier(pagarPara) as number}`, pagarPara, {
      observe: 'response',
    });
  }

  partialUpdate(pagarPara: IPagarPara): Observable<EntityResponseType> {
    return this.http.patch<IPagarPara>(`${this.resourceUrl}/${getPagarParaIdentifier(pagarPara) as number}`, pagarPara, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPagarPara>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPagarPara[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPagarParaToCollectionIfMissing(
    pagarParaCollection: IPagarPara[],
    ...pagarParasToCheck: (IPagarPara | null | undefined)[]
  ): IPagarPara[] {
    const pagarParas: IPagarPara[] = pagarParasToCheck.filter(isPresent);
    if (pagarParas.length > 0) {
      const pagarParaCollectionIdentifiers = pagarParaCollection.map(pagarParaItem => getPagarParaIdentifier(pagarParaItem)!);
      const pagarParasToAdd = pagarParas.filter(pagarParaItem => {
        const pagarParaIdentifier = getPagarParaIdentifier(pagarParaItem);
        if (pagarParaIdentifier == null || pagarParaCollectionIdentifiers.includes(pagarParaIdentifier)) {
          return false;
        }
        pagarParaCollectionIdentifiers.push(pagarParaIdentifier);
        return true;
      });
      return [...pagarParasToAdd, ...pagarParaCollection];
    }
    return pagarParaCollection;
  }
}
