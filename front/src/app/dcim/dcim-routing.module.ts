import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutDcimComponent } from './pages/layout-dcim/layout-dcim.component';
import { DatacenterListPageComponent } from './pages/datacenter-list-page/datacenter-list-page.component';
import { GabineteListPageComponent } from './pages/gabinete-list-page/gabinete-list-page.component';
import { DatacenterNewPageComponent } from './pages/datacenter-new-page/datacenter-new-page.component';
import { DatacenterEditPageComponent } from './pages/datacenter-edit-page/datacenter-edit-page.component';

const routes: Routes = [
  {
    path:'datacenter',
    component:LayoutDcimComponent,
    children:[
      { path: '', component: DatacenterListPageComponent, pathMatch:'full' },
      { path: 'new', component: DatacenterNewPageComponent },
      { path: 'edit/:id', component: DatacenterEditPageComponent },
      {path:'**', redirectTo:''}
    ]
  },
  {
    path: 'gabinete',
    component: LayoutDcimComponent,
    children:[
      {path:'', component:GabineteListPageComponent},
      {path:'', redirectTo:'list', pathMatch:'full'}
    ]
  },
  {
    path:'',
    redirectTo:'datacenter',
    pathMatch:'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DcimRoutingModule { }
