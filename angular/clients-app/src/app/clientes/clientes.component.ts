import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[];

  constructor(private clienteService: ClienteService) { }

  ngOnInit(){
    this.clienteService.getClientes().subscribe(
      clients => this.clientes = clients
    );
  }

  delete(client: Cliente): void{
    
    swal.fire({
      title: 'Esta seguro?',
      text: "Seguro que desea eliminar al cliente ".concat(client.name + " " + client.surname + "?"),
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.value) {
        this.clienteService.delete(client.id).subscribe(response => {
          this.clientes = this.clientes.filter(cli => cli !== client);

          swal.fire(
            'Eliminado!',
            'El cliente fue eliminado con exito.',
            'success'
          );
        });

        
      }
    })

  }

}
