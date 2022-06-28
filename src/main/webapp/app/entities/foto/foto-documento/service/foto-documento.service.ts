import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoDocumento, getFotoDocumentoIdentifier } from '../foto-documento.model';

export type EntityResponseType = HttpResponse<IFotoDocumento>;
export type EntityArrayResponseType = HttpResponse<IFotoDocumento[]>;

@Injectable({ providedIn: 'root' })
export class FotoDocumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-documentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoDocumento: IFotoDocumento): Observable<EntityResponseType> {
    return this.http.post<IFotoDocumento>(this.resourceUrl, fotoDocumento, { observe: 'response' });
  }

  update(fotoDocumento: IFotoDocumento): Observable<EntityResponseType> {
    return this.http.put<IFotoDocumento>(`${this.resourceUrl}/${getFotoDocumentoIdentifier(fotoDocumento) as number}`, fotoDocumento, {
      observe: 'response',
    });
  }

  partialUpdate(fotoDocumento: IFotoDocumento): Observable<EntityResponseType> {
    return this.http.patch<IFotoDocumento>(`${this.resourceUrl}/${getFotoDocumentoIdentifier(fotoDocumento) as number}`, fotoDocumento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAllByPessoaId(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoDocumento[]>(`${this.resourceUrl}/documento/${id}`, { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoDocumentoToCollectionIfMissing(
    fotoDocumentoCollection: IFotoDocumento[],
    ...fotoDocumentosToCheck: (IFotoDocumento | null | undefined)[]
  ): IFotoDocumento[] {
    const fotoDocumentos: IFotoDocumento[] = fotoDocumentosToCheck.filter(isPresent);
    if (fotoDocumentos.length > 0) {
      const fotoDocumentoCollectionIdentifiers = fotoDocumentoCollection.map(
        fotoDocumentoItem => getFotoDocumentoIdentifier(fotoDocumentoItem)!
      );
      const fotoDocumentosToAdd = fotoDocumentos.filter(fotoDocumentoItem => {
        const fotoDocumentoIdentifier = getFotoDocumentoIdentifier(fotoDocumentoItem);
        if (fotoDocumentoIdentifier == null || fotoDocumentoCollectionIdentifiers.includes(fotoDocumentoIdentifier)) {
          return false;
        }
        fotoDocumentoCollectionIdentifiers.push(fotoDocumentoIdentifier);
        return true;
      });
      return [...fotoDocumentosToAdd, ...fotoDocumentoCollection];
    }
    return fotoDocumentoCollection;
  }
}
