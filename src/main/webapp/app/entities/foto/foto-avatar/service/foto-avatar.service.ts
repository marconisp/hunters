import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoAvatar, getFotoAvatarIdentifier } from '../foto-avatar.model';

export type EntityResponseType = HttpResponse<IFotoAvatar>;
export type EntityArrayResponseType = HttpResponse<IFotoAvatar[]>;

@Injectable({ providedIn: 'root' })
export class FotoAvatarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-avatars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoAvatar: IFotoAvatar): Observable<EntityResponseType> {
    return this.http.post<IFotoAvatar>(this.resourceUrl, fotoAvatar, { observe: 'response' });
  }

  update(fotoAvatar: IFotoAvatar): Observable<EntityResponseType> {
    return this.http.put<IFotoAvatar>(`${this.resourceUrl}/${getFotoAvatarIdentifier(fotoAvatar) as number}`, fotoAvatar, {
      observe: 'response',
    });
  }

  partialUpdate(fotoAvatar: IFotoAvatar): Observable<EntityResponseType> {
    return this.http.patch<IFotoAvatar>(`${this.resourceUrl}/${getFotoAvatarIdentifier(fotoAvatar) as number}`, fotoAvatar, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFotoAvatar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFotoAvatar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFotoAvatarToCollectionIfMissing(
    fotoAvatarCollection: IFotoAvatar[],
    ...fotoAvatarsToCheck: (IFotoAvatar | null | undefined)[]
  ): IFotoAvatar[] {
    const fotoAvatars: IFotoAvatar[] = fotoAvatarsToCheck.filter(isPresent);
    if (fotoAvatars.length > 0) {
      const fotoAvatarCollectionIdentifiers = fotoAvatarCollection.map(fotoAvatarItem => getFotoAvatarIdentifier(fotoAvatarItem)!);
      const fotoAvatarsToAdd = fotoAvatars.filter(fotoAvatarItem => {
        const fotoAvatarIdentifier = getFotoAvatarIdentifier(fotoAvatarItem);
        if (fotoAvatarIdentifier == null || fotoAvatarCollectionIdentifiers.includes(fotoAvatarIdentifier)) {
          return false;
        }
        fotoAvatarCollectionIdentifiers.push(fotoAvatarIdentifier);
        return true;
      });
      return [...fotoAvatarsToAdd, ...fotoAvatarCollection];
    }
    return fotoAvatarCollection;
  }
}
