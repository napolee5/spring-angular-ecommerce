import { OrderItem } from "./order-item.model";

export interface Cart{
    id: number;
    items: OrderItem[];
    grandTotal: number;
}