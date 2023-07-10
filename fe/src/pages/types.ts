type ProductId = number;
type CategoryId = number;

export interface Products {
  [key: ProductId]: ProductInfo;
}

export interface Categories {
  [key: CategoryId]: CategoryInfo;
}

export interface ProductInfo {
  name: string;
  productId: number;
  price: number;
  imgUrl: string;
  isBest?: boolean;
  hasLarge: boolean;
  hasSmall: boolean;
  hasHot: boolean;
  hasIce: boolean;
}

export interface CategoryInfo {
  categoryName: string;
  categoryId: number;
  products: ProductInfo[];
}

export interface ProductOrder {
  productId: number;
  name: string;
  size: string;
  temperature: string;
  amount: number;
}

export interface OrderSuccessInfo {
  orders: {
    orderId: number;
    orderNumber: number;
    orderDatetime: string;
  };
  orderProducts: OrderItem[];
  payment: {
    method: string;
    totalPrice: number;
    receivedPrice: number;
    remainedPrice: number;
  };
}

export interface OrderResult {
  success: boolean;
  data: { orderId: number };
  errorCode: { status: number; code: string; message: string };
}

export interface OrderItem {
  name: string;
  size: string;
  temperature: string;
  amount: number;
}