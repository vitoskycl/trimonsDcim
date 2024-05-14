import { MatSnackBar } from "@angular/material/snack-bar";

export function ShowSnackbar(snackbar: MatSnackBar, message: string ):void {
  snackbar.open( message, 'done', {
    duration: 2500,
  })
}
