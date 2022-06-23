import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUser1, getUser1Identifier } from '../user-1.model';

export type EntityResponseType = HttpResponse<IUser1>;
export type EntityArrayResponseType = HttpResponse<IUser1[]>;

@Injectable({ providedIn: 'root' })
export class User1Service {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-1-s');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(user1: IUser1): Observable<EntityResponseType> {
    return this.http.post<IUser1>(this.resourceUrl, user1, { observe: 'response' });
  }

  update(user1: IUser1): Observable<EntityResponseType> {
    return this.http.put<IUser1>(`${this.resourceUrl}/${getUser1Identifier(user1) as number}`, user1, { observe: 'response' });
  }

  partialUpdate(user1: IUser1): Observable<EntityResponseType> {
    return this.http.patch<IUser1>(`${this.resourceUrl}/${getUser1Identifier(user1) as number}`, user1, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUser1>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUser1[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUser1ToCollectionIfMissing(user1Collection: IUser1[], ...user1sToCheck: (IUser1 | null | undefined)[]): IUser1[] {
    const user1s: IUser1[] = user1sToCheck.filter(isPresent);
    if (user1s.length > 0) {
      const user1CollectionIdentifiers = user1Collection.map(user1Item => getUser1Identifier(user1Item)!);
      const user1sToAdd = user1s.filter(user1Item => {
        const user1Identifier = getUser1Identifier(user1Item);
        if (user1Identifier == null || user1CollectionIdentifiers.includes(user1Identifier)) {
          return false;
        }
        user1CollectionIdentifiers.push(user1Identifier);
        return true;
      });
      return [...user1sToAdd, ...user1Collection];
    }
    return user1Collection;
  }
}
