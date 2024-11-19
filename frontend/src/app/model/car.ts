import { CarOwner } from './carOwner';

export interface Car{
  id: number;
  brand: string;
  model: string;
  year: string;
  number: string;
  carOwner: CarOwner;
}
