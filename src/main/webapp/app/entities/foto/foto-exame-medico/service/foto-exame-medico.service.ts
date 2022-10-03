import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoExameMedico, getFotoExameMedicoIdentifier } from '../foto-exame-medico.model';

export type EntityResponseType = HttpResponse<IFotoExameMedico>;
export type EntityArrayResponseType = HttpResponse<IFotoExameMedico[]>;

@Injectable({ providedIn: 'root' })
export class FotoExameMedicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-exame-medicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoExameMedico: IFotoExameMedico): Observable<EntityResponseType> {
    return this.http.post<IFotoExameMedico>(this.resourceUrl, fotoExameMedico, { observe: 'response' });
  }

  update(fotoExameMedico: IFotoExameMedico): Observable<EntityResponseType> {
    return this.http.put<IFotoExameMedico>(
      `${this.resourceUrl}/${getFotoExameMedicoIdentifier(fotoExameMedico) as number}`,
      fotoExameMedico,
      { observe: 'response' }
    );
  }

  partialUpdate(fotoExameMedico: IFotoExameMedico): Observable<EntityResponseType> {
    return this.http.patch<IFotoExameMedico>(
      `${this.resourceUrl}/${getFotoExameMedicoIdentifier(fotoExameMedico) as number}`,
      fotoExameMedico,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoExameMedico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoExameMedico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoExameMedicoToCollectionIfMissing(
    fotoExameMedicoCollection: IFotoExameMedico[],
    ...fotoExameMedicosToCheck: (IFotoExameMedico | null | undefined)[]
  ): IFotoExameMedico[] {
    const fotoExameMedicos: IFotoExameMedico[] = fotoExameMedicosToCheck.filter(isPresent);
    if (fotoExameMedicos.length > 0) {
      const fotoExameMedicoCollectionIdentifiers = fotoExameMedicoCollection.map(
        fotoExameMedicoItem => getFotoExameMedicoIdentifier(fotoExameMedicoItem)!
      );
      const fotoExameMedicosToAdd = fotoExameMedicos.filter(fotoExameMedicoItem => {
        const fotoExameMedicoIdentifier = getFotoExameMedicoIdentifier(fotoExameMedicoItem);
        if (fotoExameMedicoIdentifier == null || fotoExameMedicoCollectionIdentifiers.includes(fotoExameMedicoIdentifier)) {
          return false;
        }
        fotoExameMedicoCollectionIdentifiers.push(fotoExameMedicoIdentifier);
        return true;
      });
      return [...fotoExameMedicosToAdd, ...fotoExameMedicoCollection];
    }
    return fotoExameMedicoCollection;
  }
}
