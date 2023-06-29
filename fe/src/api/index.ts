import { CategoryInfo, OrderResult, ProductOrder } from 'pages/types';
import { formatMenuOptionOrderList } from 'utils';

const BASE_API_DOMAIN = new URL(`https://59e962f7-9105-47c3-9543-c5b5b596f0e7.mock.pstmn.io`);

const fetchJSON = async (url: URL, option?: {}) => {
  const response = await fetch(url, option);

  if (!response.ok) {
    throw new Error(response.statusText);
  }

  return response.json();
};

export const fetchMenus = async (): Promise<CategoryInfo[] | undefined> => {
  try {
    const url = new URL('products', BASE_API_DOMAIN);
    return await fetchJSON(url);
  } catch (error) {
    console.error(error);
  }
};

export const requestCardOrder = async (
  orderList: ProductOrder[],
  totalPrice: number
): Promise<OrderResult | undefined> => {
  const formattedOrderList = formatMenuOptionOrderList(orderList);
  const json = JSON.stringify({
    orderProducts: formattedOrderList,
    totalPrice: totalPrice,
  });
  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: json,
  };

  try {
    const url = new URL('api/payment/card', BASE_API_DOMAIN);
    return await fetchJSON(url, option);
  } catch (error) {
    console.error(error);
  }
};

export const fetchReceipt = async (orderId: number) => {
  try {
    const url = new URL(`api/receipt?orderId=${orderId}`, BASE_API_DOMAIN);
    return await fetchJSON(url);
  } catch (error) {
    console.error(error);
  }
};

export const requestCashOrder = async (
  orderList: ProductOrder[],
  totalPrice: number,
  receivedPrice: number
): Promise<OrderResult | undefined> => {
  const formattedOrderList = formatMenuOptionOrderList(orderList);
  const json = JSON.stringify({
    orderProducts: formattedOrderList,
    totalPrice: totalPrice,
    receivedPrice: receivedPrice,
  });
  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: json,
  };

  try {
    const url = new URL('api/payment/cash', BASE_API_DOMAIN);
    return await fetchJSON(url, option);
  } catch (error) {
    console.error(error);
  }
};

export const failCardOrder = async (
  orderList: ProductOrder[],
  totalPrice: number
): Promise<OrderResult | undefined> => {
  const formattedOrderList = formatMenuOptionOrderList(orderList);
  const json = JSON.stringify({
    orderItems: formattedOrderList,
    totalPrice: totalPrice,
  });
  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: json,
  };

  try {
    const url = new URL('api/payment/card?fail=500', BASE_API_DOMAIN);
    return await fetchJSON(url, option);
  } catch (error) {
    console.error(error);
  }
};
