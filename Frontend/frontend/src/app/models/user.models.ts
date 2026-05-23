import { Cart } from "./cart.models";
import { Order } from "./order.models";

export interface User {
  name: string;
  surname: string;
  email: string;
  ruolo: Role
  orderList: Order[];
  cart: Cart;
}

export enum Role{
  ADMIN='ADMIN',
  USER='USER'
}