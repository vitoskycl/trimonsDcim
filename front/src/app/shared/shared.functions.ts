import { environments } from "../../environments/environments";

export function MyLogger(mensaje:any):void{
  if (environments.debugger){
    console.log(mensaje);
  }
}
