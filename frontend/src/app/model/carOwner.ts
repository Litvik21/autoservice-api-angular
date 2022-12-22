import {Car} from "./car";
import {Order} from "./order";

export interface CarOwner {
  id: number;
  cars: Car[];
  orders: Order[];
}
