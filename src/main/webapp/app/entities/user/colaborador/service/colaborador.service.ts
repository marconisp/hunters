import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IColaborador, getColaboradorIdentifier } from '../colaborador.model';

export type EntityResponseType = HttpResponse<IColaborador>;
export type EntityArrayResponseType = HttpResponse<IColaborador[]>;

@Injectable({ providedIn: 'root' })
export class ColaboradorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/colaboradors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(colaborador: IColaborador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(colaborador);
    return this.http
      .post<IColaborador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(colaborador: IColaborador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(colaborador);
    return this.http
      .put<IColaborador>(`${this.resourceUrl}/${getColaboradorIdentifier(colaborador) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(colaborador: IColaborador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(colaborador);
    return this.http
      .patch<IColaborador>(`${this.resourceUrl}/${getColaboradorIdentifier(colaborador) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IColaborador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IColaborador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addColaboradorToCollectionIfMissing(
    colaboradorCollection: IColaborador[],
    ...colaboradorsToCheck: (IColaborador | null | undefined)[]
  ): IColaborador[] {
    const colaboradors: IColaborador[] = colaboradorsToCheck.filter(isPresent);
    if (colaboradors.length > 0) {
      const colaboradorCollectionIdentifiers = colaboradorCollection.map(colaboradorItem => getColaboradorIdentifier(colaboradorItem)!);
      const colaboradorsToAdd = colaboradors.filter(colaboradorItem => {
        const colaboradorIdentifier = getColaboradorIdentifier(colaboradorItem);
        if (colaboradorIdentifier == null || colaboradorCollectionIdentifiers.includes(colaboradorIdentifier)) {
          return false;
        }
        colaboradorCollectionIdentifiers.push(colaboradorIdentifier);
        return true;
      });
      return [...colaboradorsToAdd, ...colaboradorCollection];
    }
    return colaboradorCollection;
  }

  protected convertDateFromClient(colaborador: IColaborador): IColaborador {
    return Object.assign({}, colaborador, {
      dataCadastro: colaborador.dataCadastro?.isValid() ? colaborador.dataCadastro.format(DATE_FORMAT) : undefined,
      dataAdmissao: colaborador.dataAdmissao?.isValid() ? colaborador.dataAdmissao.format(DATE_FORMAT) : undefined,
      dataRecisao: colaborador.dataRecisao?.isValid() ? colaborador.dataRecisao.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCadastro = res.body.dataCadastro ? dayjs(res.body.dataCadastro) : undefined;
      res.body.dataAdmissao = res.body.dataAdmissao ? dayjs(res.body.dataAdmissao) : undefined;
      res.body.dataRecisao = res.body.dataRecisao ? dayjs(res.body.dataRecisao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((colaborador: IColaborador) => {
        colaborador.dataCadastro = colaborador.dataCadastro ? dayjs(colaborador.dataCadastro) : undefined;
        colaborador.dataAdmissao = colaborador.dataAdmissao ? dayjs(colaborador.dataAdmissao) : undefined;
        colaborador.dataRecisao = colaborador.dataRecisao ? dayjs(colaborador.dataRecisao) : undefined;
      });
    }
    return res;
  }
}
