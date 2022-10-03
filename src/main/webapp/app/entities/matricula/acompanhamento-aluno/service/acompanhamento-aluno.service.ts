import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAcompanhamentoAluno, getAcompanhamentoAlunoIdentifier } from '../acompanhamento-aluno.model';

export type EntityResponseType = HttpResponse<IAcompanhamentoAluno>;
export type EntityArrayResponseType = HttpResponse<IAcompanhamentoAluno[]>;

@Injectable({ providedIn: 'root' })
export class AcompanhamentoAlunoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/acompanhamento-alunos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(acompanhamentoAluno: IAcompanhamentoAluno): Observable<EntityResponseType> {
    return this.http.post<IAcompanhamentoAluno>(this.resourceUrl, acompanhamentoAluno, { observe: 'response' });
  }

  update(acompanhamentoAluno: IAcompanhamentoAluno): Observable<EntityResponseType> {
    return this.http.put<IAcompanhamentoAluno>(
      `${this.resourceUrl}/${getAcompanhamentoAlunoIdentifier(acompanhamentoAluno) as number}`,
      acompanhamentoAluno,
      { observe: 'response' }
    );
  }

  partialUpdate(acompanhamentoAluno: IAcompanhamentoAluno): Observable<EntityResponseType> {
    return this.http.patch<IAcompanhamentoAluno>(
      `${this.resourceUrl}/${getAcompanhamentoAlunoIdentifier(acompanhamentoAluno) as number}`,
      acompanhamentoAluno,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAcompanhamentoAluno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcompanhamentoAluno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAcompanhamentoAlunoToCollectionIfMissing(
    acompanhamentoAlunoCollection: IAcompanhamentoAluno[],
    ...acompanhamentoAlunosToCheck: (IAcompanhamentoAluno | null | undefined)[]
  ): IAcompanhamentoAluno[] {
    const acompanhamentoAlunos: IAcompanhamentoAluno[] = acompanhamentoAlunosToCheck.filter(isPresent);
    if (acompanhamentoAlunos.length > 0) {
      const acompanhamentoAlunoCollectionIdentifiers = acompanhamentoAlunoCollection.map(
        acompanhamentoAlunoItem => getAcompanhamentoAlunoIdentifier(acompanhamentoAlunoItem)!
      );
      const acompanhamentoAlunosToAdd = acompanhamentoAlunos.filter(acompanhamentoAlunoItem => {
        const acompanhamentoAlunoIdentifier = getAcompanhamentoAlunoIdentifier(acompanhamentoAlunoItem);
        if (acompanhamentoAlunoIdentifier == null || acompanhamentoAlunoCollectionIdentifiers.includes(acompanhamentoAlunoIdentifier)) {
          return false;
        }
        acompanhamentoAlunoCollectionIdentifiers.push(acompanhamentoAlunoIdentifier);
        return true;
      });
      return [...acompanhamentoAlunosToAdd, ...acompanhamentoAlunoCollection];
    }
    return acompanhamentoAlunoCollection;
  }
}
