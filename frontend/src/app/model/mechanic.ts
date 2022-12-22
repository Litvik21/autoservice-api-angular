import {Order} from "./order";

export interface Mechanic {
  id: number;
  name: string;
  finishedOrders: Order[];
}
