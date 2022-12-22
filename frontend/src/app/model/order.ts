import {Car} from "./car";
import {Product} from "./product";
import {Status} from "./status";
import {Task} from "./task";

export interface Order {
  id?: number;
  car: Car;
  description: string;
  dateReceived?: Date;
  tasks: Task[];
  products: Product[];
  status?: Status;
  totalPrice?: number;
  dateFinished: Date;
}
