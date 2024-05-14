import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Datacenter } from '../../interfaces/datacenter.interface';
import { DatacenterService } from '../../services/datacenter.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ShowSnackbar } from '../../../material/functionutil';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { MyLogger } from '../../../shared/shared.functions';

@Component({
  selector: 'app-datacenter-dialog',
  templateUrl: './datacenter-dialog.component.html',
  styles: ``
})
export class DatacenterDialogComponent implements OnInit{
  public datacenterForm = new FormGroup({
    datacenterId: new FormControl<number | null>(null),
    nombre: new FormControl(''),
    direccion: new FormControl(''),
    vigente: new FormControl<boolean>(false),
  })

  get currentDatacenter(): Datacenter{
    const datacenter = this.datacenterForm.value as Datacenter;
    MyLogger(datacenter);
    return datacenter;
  }

  constructor(
    public dialogRef: MatDialogRef<DatacenterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Datacenter,
    private datacenterService:DatacenterService,
    private router: Router,
    private snackbar: MatSnackBar,){

  }
  ngOnInit(): void {
    if (this.data){
      this.datacenterForm.reset(this.data);
    }else{
      this.datacenterForm.setValue({
        datacenterId: null,
        nombre: '',
        direccion: '',
        vigente: false
      });
    }

  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  onSumit():void{
    if (this.datacenterForm.valid){
      if (this.data){
        this.datacenterService.update(this.currentDatacenter).subscribe(respId => {
          if (respId>0){
            ShowSnackbar(this.snackbar, `Datacenter Id ${ respId} fue actualizado!`);
          }else{
            ShowSnackbar(this.snackbar, `Ha ocurrido un error al momento de actualizar!`);
          }
        })
      }else{
        this.datacenterService.insert(this.currentDatacenter).subscribe(newId => {
            MyLogger('Nuevo id' + newId);
            ShowSnackbar(this.snackbar, `Datacenter Id ${ newId} fue creado!`);
          });
      }
      this.dialogRef.close(true);
    }
  }
}
