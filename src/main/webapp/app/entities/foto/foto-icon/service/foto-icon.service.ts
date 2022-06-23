import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoIcon, getFotoIconIdentifier } from '../foto-icon.model';

export type EntityResponseType = HttpResponse<IFotoIcon>;
export type EntityArrayResponseType = HttpResponse<IFotoIcon[]>;

@Injectable({ providedIn: 'root' })
export class FotoIconService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-icons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoIcon: IFotoIcon): Observable<EntityResponseType> {
    return this.http.post<IFotoIcon>(this.resourceUrl, fotoIcon, { observe: 'response' });
  }

  update(fotoIcon: IFotoIcon): Observable<EntityResponseType> {
    return this.http.put<IFotoIcon>(`${this.resourceUrl}/${getFotoIconIdentifier(fotoIcon) as number}`, fotoIcon, { observe: 'response' });
  }

  partialUpdate(fotoIcon: IFotoIcon): Observable<EntityResponseType> {
    return this.http.patch<IFotoIcon>(`${this.resourceUrl}/${getFotoIconIdentifier(fotoIcon) as number}`, fotoIcon, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoIcon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoIcon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoIconToCollectionIfMissing(fotoIconCollection: IFotoIcon[], ...fotoIconsToCheck: (IFotoIcon | null | undefined)[]): IFotoIcon[] {
    const fotoIcons: IFotoIcon[] = fotoIconsToCheck.filter(isPresent);
    if (fotoIcons.length > 0) {
      const fotoIconCollectionIdentifiers = fotoIconCollection.map(fotoIconItem => getFotoIconIdentifier(fotoIconItem)!);
      const fotoIconsToAdd = fotoIcons.filter(fotoIconItem => {
        const fotoIconIdentifier = getFotoIconIdentifier(fotoIconItem);
        if (fotoIconIdentifier == null || fotoIconCollectionIdentifiers.includes(fotoIconIdentifier)) {
          return false;
        }
        fotoIconCollectionIdentifiers.push(fotoIconIdentifier);
        return true;
      });
      return [...fotoIconsToAdd, ...fotoIconCollection];
    }
    return fotoIconCollection;
  }
}
