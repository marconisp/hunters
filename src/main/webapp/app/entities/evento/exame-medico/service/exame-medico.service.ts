import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExameMedico, getExameMedicoIdentifier } from '../exame-medico.model';

export type EntityResponseType = HttpResponse<IExameMedico>;
export type EntityArrayResponseType = HttpResponse<IExameMedico[]>;

@Injectable({ providedIn: 'root' })
export class ExameMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/exame-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(exameMedico: IExameMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exameMedico);
    return this.http
      .post<IExameMedico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(exameMedico: IExameMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exameMedico);
    return this.http
      .put<IExameMedico>(`${this.resourceUrl}/${getExameMedicoIdentifier(exameMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(exameMedico: IExameMedico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exameMedico);
    return this.http
      .patch<IExameMedico>(`${this.resourceUrl}/${getExameMedicoIdentifier(exameMedico) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExameMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExameMedico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addExameMedicoToCollectionIfMissing(
    exameMedicoCollection: IExameMedico[],
    ...exameMedicosToCheck: (IExameMedico | null | undefined)[]
  ): IExameMedico[] {
    const exameMedicos: IExameMedico[] = exameMedicosToCheck.filter(isPresent);
    if (exameMedicos.length > 0) {
      const exameMedicoCollectionIdentifiers = exameMedicoCollection.map(exameMedicoItem => getExameMedicoIdentifier(exameMedicoItem)!);
      const exameMedicosToAdd = exameMedicos.filter(exameMedicoItem => {
        const exameMedicoIdentifier = getExameMedicoIdentifier(exameMedicoItem);
        if (exameMedicoIdentifier == null || exameMedicoCollectionIdentifiers.includes(exameMedicoIdentifier)) {
          return false;
        }
        exameMedicoCollectionIdentifiers.push(exameMedicoIdentifier);
        return true;
      });
      return [...exameMedicosToAdd, ...exameMedicoCollection];
    }
    return exameMedicoCollection;
  }

  protected convertDateFromClient(exameMedico: IExameMedico): IExameMedico {
    return Object.assign({}, exameMedico, {
      data: exameMedico.data?.isValid() ? exameMedico.data.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((exameMedico: IExameMedico) => {
        exameMedico.data = exameMedico.data ? dayjs(exameMedico.data) : undefined;
      });
    }
    return res;
  }
}
