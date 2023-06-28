import { Categories, CategoryInfo, MenuInfo, MenuOrder, Menus, OrderSuccessInfo } from 'pages/types';

export const formatProducts = (menuData: CategoryInfo[]) => {
  const menus: Menus = {};

  menuData.forEach((category: CategoryInfo) => {
    category.menus.forEach((menu: MenuInfo) => {
      menus[menu.menuId] = menu;
    });
  });

  return menus;
};

export const formatAllCategories = (menuData: CategoryInfo[]) => {
  const formattedMenuData: Categories = {};
  menuData.forEach(category => {
    formattedMenuData[category.categoryId] = category;
  });

  return formattedMenuData;
};

export function formatSameMenuIdList(orderList: MenuOrder[]) {
  const formattedOrderList: MenuOrder[] = [];

  orderList.forEach(order => {
    const { menuId, amount } = order;
    const orderItem = formattedOrderList.find(item => item.menuId === menuId);
    if (orderItem) {
      orderItem.amount += amount;
    } else {
      formattedOrderList.push({ ...order });
    }
  });

  return formattedOrderList;
}

export function formatMenuOptionOrderList(orderList: MenuOrder[]) {
  const formattedOrderList: MenuOrder[] = [];

  orderList.forEach(order => {
    const { menuId, size, temperature, amount } = order;
    const orderItem = formattedOrderList.find(
      item => item.menuId === menuId && item.size === size && item.temperature === temperature
    );
    if (orderItem) {
      orderItem.amount += amount;
    } else {
      formattedOrderList.push({ ...order });
    }
  });

  return formattedOrderList;
}

export function calculateTotalAmount(data: OrderSuccessInfo): Record<string, number> {
  const AmountByMenu: Record<string, number> = {};
  data.orderItems.forEach(item => {
    const { name, amount } = item;
    if (AmountByMenu[name]) {
      AmountByMenu[name] += amount;
    } else {
      AmountByMenu[name] = amount;
    }
  });
  return AmountByMenu;
}