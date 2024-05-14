// To parse this data:
//
//   import { Convert, Datacenter } from "./file";
//
//   const datacenter = Convert.toDatacenter(json);

export interface Datacenter {
  datacenterId: number;
  nombre:       string;
  direccion:    string;
  vigente:      boolean;
}

// Converts JSON strings to/from your types
export class Convert {
  public static toDatacenter(json: string): Datacenter {
      return JSON.parse(json);
  }

  public static datacenterToJson(value: Datacenter): string {
      return JSON.stringify(value);
  }
}
