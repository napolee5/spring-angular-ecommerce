import { OrderItem } from "./order-item.model";
import { UserOrder } from "./user-order-dto.models";
import { User } from "./user.models";



export interface Order {
  id: number;
  user: UserOrder
  total: number;
  time: string;
  region: string;
  city: string;
  province: string;
  address: string;
  orderStatus: OrderStatus;
  orderItems: OrderItem[];
}

export enum OrderStatus {
  ORDINE_CONFERMATO = 'ORDINE_CONFERMATO',
  SPEDITO = 'SPEDITO',
  CONSEGNATO= 'CONSEGNATO',
  CANCELLATO = 'CANCELLATO'
}