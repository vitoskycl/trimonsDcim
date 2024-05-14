import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environments } from '../../../environments/environments';
import { Observable, catchError, map, of } from 'rxjs';
import { Datacenter } from '../interfaces/datacenter.interface';

@Injectable({providedIn: 'root'})
export class DatacenterService {
  private apiUrl = `${ environments.baseUrl}/datacenters`;

  constructor(private http: HttpClient) { }

  getAll():Observable<Datacenter[]>{
    return this.http.get<Datacenter[]>( this.apiUrl)
    .pipe(
      catchError( () => of([]) ),
    );
  }

  getById(datacenterId:number):Observable<Datacenter|undefined>{
    return this.http.get<Datacenter>(`${this.apiUrl}/${datacenterId}`)
    .pipe(
      catchError( error => of(undefined) )
    );
  }

  insert(datacenter: Datacenter):Observable<number>{
    return this.http.post<number>(this.apiUrl, datacenter)
    .pipe(
      catchError( () => of(0) )
    );
  }

  delete(datacenterId:number):Observable<boolean>{
    return this.http.delete<boolean>(`${this.apiUrl}/${datacenterId}`)
    .pipe(
      map( resp => true ),
      catchError( () => of(false) )
    );
  }

  update(datacenter: Datacenter):Observable<number>{
    return this.http.put<number>(`${this.apiUrl}/${datacenter.datacenterId}`, datacenter)
    .pipe(
      catchError( () => of(0) )
    );
  }
}
