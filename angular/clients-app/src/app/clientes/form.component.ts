import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import { Router,ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {
  client: Cliente = new Cliente();
  title: string = "Crear cliente";
  errores: string[];

  constructor(private clientService: ClienteService, private router: Router, private activatedRoute:ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }

  public create():void {
    this.clientService.create(this.client).subscribe(
      cliente => { 
        this.router.navigate(['/clientes']);
        swal.fire('Nuevo cliente', `Cliente ${cliente.name} creado con exito!`, 'success');
      },
      err => {
        this.errores = err.error.errors as string[];
        console.log(this.errores);
      }
    );
  }

  public cargarCliente(): void{ /*para obtener un cliente*/
    this.activatedRoute.params.subscribe(params => { /*para leer los parametros de la url*/ 
      let id = params['id'];

      if (id) {
        this.clientService.getCliente(id).subscribe(cliente => this.client = cliente);
      }
    });
  }

  public update(): void{
    this.clientService.update(this.client).subscribe(cliente => {
      this.router.navigate(['/clientes']);
      swal.fire('Cliente Actualizado', `Cliente ${cliente.name} actualizado con exito!`, 'success');
      },
      err => {
        this.errores = err.error.errors as string[];
        console.log(this.errores);
      }
    );
  }

}
