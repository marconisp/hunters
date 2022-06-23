import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMensagem, getMensagemIdentifier } from '../mensagem.model';

export type EntityResponseType = HttpResponse<IMensagem>;
export type EntityArrayResponseType = HttpResponse<IMensagem[]>;

@Injectable({ providedIn: 'root' })
export class MensagemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mensagems');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mensagem: IMensagem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mensagem);
    return this.http
      .post<IMensagem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mensagem: IMensagem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mensagem);
    return this.http
      .put<IMensagem>(`${this.resourceUrl}/${getMensagemIdentifier(mensagem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(mensagem: IMensagem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mensagem);
    return this.http
      .patch<IMensagem>(`${this.resourceUrl}/${getMensagemIdentifier(mensagem) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMensagem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMensagem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMensagemToCollectionIfMissing(mensagemCollection: IMensagem[], ...mensagemsToCheck: (IMensagem | null | undefined)[]): IMensagem[] {
    const mensagems: IMensagem[] = mensagemsToCheck.filter(isPresent);
    if (mensagems.length > 0) {
      const mensagemCollectionIdentifiers = mensagemCollection.map(mensagemItem => getMensagemIdentifier(mensagemItem)!);
      const mensagemsToAdd = mensagems.filter(mensagemItem => {
        const mensagemIdentifier = getMensagemIdentifier(mensagemItem);
        if (mensagemIdentifier == null || mensagemCollectionIdentifiers.includes(mensagemIdentifier)) {
          return false;
        }
        mensagemCollectionIdentifiers.push(mensagemIdentifier);
        return true;
      });
      return [...mensagemsToAdd, ...mensagemCollection];
    }
    return mensagemCollection;
  }

  protected convertDateFromClient(mensagem: IMensagem): IMensagem {
    return Object.assign({}, mensagem, {
      data: mensagem.data?.isValid() ? mensagem.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((mensagem: IMensagem) => {
        mensagem.data = mensagem.data ? dayjs(mensagem.data) : undefined;
      });
    }
    return res;
  }
}
