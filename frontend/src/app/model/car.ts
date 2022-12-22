import {CarOwner} from "./carOwner";

export interface Car{
  id: number;
  model: string;
  brand: string;
  year: string;
  number: string;
  carOwner: CarOwner;
}
