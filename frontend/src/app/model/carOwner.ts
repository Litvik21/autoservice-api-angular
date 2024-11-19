import { Car } from './car';
import { Order } from './order';

export interface CarOwner {
  id: number;
  name: string;
  lastName: string;
  phoneNumber: string;
  carsId: number[];
  cars: Car[];
  carIds: Car;
  orders: Order[];
  ordersId: number[];
}
