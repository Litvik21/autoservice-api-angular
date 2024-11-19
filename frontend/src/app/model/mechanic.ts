import { Order } from './order';

export interface Mechanic {
  id: number;
  name: string;
  lastName: string;
  finishedOrders: Order[];
  status: string;
}
