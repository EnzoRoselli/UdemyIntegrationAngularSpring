import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';
import { CLIENTES } from './clientes.json';
import { Cliente } from './cliente';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private urlEndpoint: string = "http://localhost:8080/api/clients";
  private httpHeaders = new HttpHeaders({'Content-Type':'application/json'})

  constructor(private http: HttpClient, private router: Router) { }

  getClientes(): Observable<Cliente[]>{
    // return of(CLIENTES);
    return this.http.get(this.urlEndpoint).pipe(
      map(response => {
        let clientes = response as Cliente[];

        return clientes.map(cliente => {

          cliente.name = cliente.name.toUpperCase();
          // cliente.createdAt = formatDate(cliente.createdAt, 'dd-MM-yyyy', 'en-US');
          return cliente;
        });
      })
    );
  }

  create(client:Cliente): Observable<Cliente>{
    return this.http.post(this.urlEndpoint, client, { headers: this.httpHeaders }).pipe(
      map((response:any) => response.cliente as Cliente),
      catchError(e => {
        
        if (e.status == 400) {
          console.log(e);
          return throwError(e);
        }

        Swal.fire('Error al crear el cliente', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  getCliente(id:number): Observable<Cliente>{
    return this.http.get<Cliente>(this.urlEndpoint.concat('/' + id)).pipe(
      catchError(e => {
        this.router.navigate(['/clientes']);
        console.error(e.error.mensaje);
        Swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }
  
  update(client: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(this.urlEndpoint.concat("/" + client.id), client, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        
        if (e.status == 400) {
          return throwError(e);
        }

        Swal.fire('Error al editar el cliente', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(this.urlEndpoint.concat("/" + id)).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire('Error al eliminar el cliente', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

}
