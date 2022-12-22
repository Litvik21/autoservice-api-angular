import {Mechanic} from "./mechanic";
import {TypeOfTask} from "./typeOfTask";
import {PaymentStatus} from "./paymentStatus";
import {Order} from "./order";

export interface Task {
  id?: number;
  typeOfTask?: TypeOfTask;
  order?: Order;
  mechanic?: Mechanic;
  price?: number;
  paymentStatus?: PaymentStatus;
}
