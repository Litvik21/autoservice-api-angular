import { Car } from './car';
import { Order } from './order';

export interface CarOwner {
  id: number;
  name: string;
  cars: Car[];
  orders: Order[];
}
