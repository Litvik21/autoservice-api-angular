import { Mechanic } from './mechanic';
import { TypeOfTask } from './typeOfTask';
import { PaymentStatus } from './paymentStatus';
import { Order } from './order';

export interface Task {
  id?: number;
  title?: string;
  typeOfTask?: TypeOfTask;
  order?: Order;
  mechanic?: Mechanic;
  price?: number;
  paymentStatus?: PaymentStatus;
}
