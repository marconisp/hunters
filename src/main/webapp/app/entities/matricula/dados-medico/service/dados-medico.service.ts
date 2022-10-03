import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDadosMedico, getDadosMedicoIdentifier } from '../dados-medico.model';

export type EntityResponseType = HttpResponse<IDadosMedico>;
export type EntityArrayResponseType = HttpResponse<IDadosMedico[]>;

@Injectable({ providedIn: 'root' })
export class DadosMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dados-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dadosMedico: IDadosMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dadosMedico);
    return this.http
      .post<IDadosMedico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dadosMedico: IDadosMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dadosMedico);
    return this.http
      .put<IDadosMedico>(`${this.resourceUrl}/${getDadosMedicoIdentifier(dadosMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dadosMedico: IDadosMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dadosMedico);
    return this.http
      .patch<IDadosMedico>(`${this.resourceUrl}/${getDadosMedicoIdentifier(dadosMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDadosMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDadosMedico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDadosMedicoToCollectionIfMissing(
    dadosMedicoCollection: IDadosMedico[],
    ...dadosMedicosToCheck: (IDadosMedico | null | undefined)[]
  ): IDadosMedico[] {
    const dadosMedicos: IDadosMedico[] = dadosMedicosToCheck.filter(isPresent);
    if (dadosMedicos.length > 0) {
      const dadosMedicoCollectionIdentifiers = dadosMedicoCollection.map(dadosMedicoItem => getDadosMedicoIdentifier(dadosMedicoItem)!);
      const dadosMedicosToAdd = dadosMedicos.filter(dadosMedicoItem => {
        const dadosMedicoIdentifier = getDadosMedicoIdentifier(dadosMedicoItem);
        if (dadosMedicoIdentifier == null || dadosMedicoCollectionIdentifiers.includes(dadosMedicoIdentifier)) {
          return false;
        }
        dadosMedicoCollectionIdentifiers.push(dadosMedicoIdentifier);
        return true;
      });
      return [...dadosMedicosToAdd, ...dadosMedicoCollection];
    }
    return dadosMedicoCollection;
  }

  protected convertDateFromClient(dadosMedico: IDadosMedico): IDadosMedico {
    return Object.assign({}, dadosMedico, {
      data: dadosMedico.data?.isValid() ? dadosMedico.data.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? dayjs(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dadosMedico: IDadosMedico) => {
        dadosMedico.data = dadosMedico.data ? dayjs(dadosMedico.data) : undefined;
      });
    }
    return res;
  }
}
