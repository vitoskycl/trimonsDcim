import { Component, OnInit } from '@angular/core';
import { Datacenter } from '../../interfaces/datacenter.interface';
import { DatacenterService } from '../../services/datacenter.service';
import { ShowSnackbar } from '../../../material/functionutil';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDeleteDialogComponent } from '../../components/confirm-delete-dialog/confirm-delete-dialog.component';
import { DatacenterDialogComponent } from '../datacenter-dialog/datacenter-dialog.component';

@Component({
  selector: 'app-datacenter-list-page',
  templateUrl: './datacenter-list-page.component.html',
  styles: ``
})
export class DatacenterListPageComponent implements OnInit {

  public datacenters : Datacenter[] = [];

  constructor(
    private datacenterService: DatacenterService,
    private snackbar: MatSnackBar,
    private dialog:MatDialog){

  }

  ngOnInit(): void {
   this.datacenterService.getAll().subscribe(lista => this.datacenters = lista);
  }

  editDatacenter(idDatacenter:number):void{
    if (!idDatacenter) return;

    this.datacenterService.getById(idDatacenter).subscribe(resp => {
      const dialogRef = this.dialog.open(DatacenterDialogComponent, {
        data: resp, position: { top: '10px' }
      });

      dialogRef.afterClosed().subscribe({
        next: (val) => {
          if (val) {
            this.datacenterService.getAll().subscribe(lista => this.datacenters = lista);
          }
        }
      });
    });
  }

  deleteDatacenter(idDatacenter:number): void {
    const dialogRef = this.dialog.open(ConfirmDeleteDialogComponent, {
      data: {titulo: "Eliminar Datacenter", mensaje: "el Datacenter", id:idDatacenter}, position: { top: '10px' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if ( !result ) return;
      this.datacenterService.delete(idDatacenter).subscribe(resp => {
        if (resp){
          ShowSnackbar(this.snackbar, `Datacenter Id ${idDatacenter} fue eliminado!`);
          this.datacenterService.getAll().subscribe(lista => this.datacenters = lista);
        }else{
          ShowSnackbar(this.snackbar, "No fue posible eliminar el registro");
        }
      })
    });
  }


  newDatacenter():void{
    const dialogRef = this.dialog.open(DatacenterDialogComponent, {position: { top: '10px' }});
    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.datacenterService.getAll().subscribe(lista => this.datacenters = lista);
        }
      }
    });
  }


  displayedColumns: string[] = ['acciones', 'datacenterId', 'nombre', 'direccion', 'vigente'];
  dataSource = this.datacenters;
}
