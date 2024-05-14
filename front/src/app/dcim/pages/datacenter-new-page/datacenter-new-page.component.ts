import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Datacenter } from '../../interfaces/datacenter.interface';
import { DatacenterService } from '../../services/datacenter.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ShowSnackbar } from '../../../material/functionutil';

@Component({
  selector: 'app-datacenter-new-page',
  templateUrl: './datacenter-new-page.component.html',
  styles: ``
})
export class DatacenterNewPageComponent {

}
