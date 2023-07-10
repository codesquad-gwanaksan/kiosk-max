import { ProductOrder } from 'pages/types';

export type ActionMap = {
  ADD_ORDER: { newOrder: ProductOrder };
  REMOVE_ORDER: { productId: number; size: string };
  RESET: {};
};

export type MenuOrderAction = {
  [Key in keyof ActionMap]: {
    type: Key;
    payload?: ActionMap[Key];
  };
}[keyof ActionMap];

export default function menuOrderReducer(initialOrders: ProductOrder[], action: MenuOrderAction): ProductOrder[] {
  switch (action.type) {
    case 'ADD_ORDER': {
      if (!action.payload) {
        throw new Error('REMOVE_ORDER action must have payload');
      }
      return [...initialOrders, action.payload.newOrder];
    }
    case 'REMOVE_ORDER': {
      if (!action.payload) {
        throw new Error('REMOVE_ORDER action must have payload');
      }
      const { productId, size } = action.payload;
      return initialOrders.filter(order => order.productId !== productId || order.size !== size);
    }
    case 'RESET': {
      return [];
    }
    default:
      return initialOrders;
  }
}