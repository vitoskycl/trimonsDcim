import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DcimRoutingModule } from './dcim-routing.module';
import { LayoutDcimComponent } from './pages/layout-dcim/layout-dcim.component';
import { DatacenterListPageComponent } from './pages/datacenter-list-page/datacenter-list-page.component';
import { DatacenterNewPageComponent } from './pages/datacenter-new-page/datacenter-new-page.component';
import { GabineteListPageComponent } from './pages/gabinete-list-page/gabinete-list-page.component';
import { MaterialModule } from '../material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ConfirmDeleteDialogComponent } from './components/confirm-delete-dialog/confirm-delete-dialog.component';
import { DatacenterEditPageComponent } from './pages/datacenter-edit-page/datacenter-edit-page.component';
import { DatacenterDialogComponent } from './pages/datacenter-dialog/datacenter-dialog.component';



@NgModule({
  declarations: [
    LayoutDcimComponent,
    DatacenterListPageComponent,
    DatacenterNewPageComponent,
    GabineteListPageComponent,
    ConfirmDeleteDialogComponent,
    DatacenterEditPageComponent,
    DatacenterDialogComponent,

  ],
  imports: [
    CommonModule,
    DcimRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class DcimModule { }
