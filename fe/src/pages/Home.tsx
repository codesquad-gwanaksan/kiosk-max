import { LoadingIndicator } from 'components/LoadingIndicator/LoadingIndicator';
import Main from 'components/Main';
import Cart from 'components/Main/Cart';
import CategoryNavbar from 'components/Navbar';
import useProducts from 'hooks/useProducts';
import menuOrderReducer from 'menuOrderReducer';
import { useEffect, useReducer, useRef, useState } from 'react';
import { formatAllCategories, formatProducts } from 'utils';
import styles from './Home.module.css';
import { CategoryInfo, ProductOrder } from './types';

interface HomeProps {
  navigate: (path: string) => void;
}

export default function Home({ navigate }: HomeProps) {
  const [selectedCategoryId, setSelectedCategoryId] = useState<number>(0);
  const [products, loading, error] = useProducts();
  const [orderList, dispatch] = useReducer(menuOrderReducer, []);
  const homeRef = useRef<HTMLDivElement>(null);
  const [mainAnimationClassName, setMainAnimationClassName] = useState<string>(styles.fadeEnter);

  const categoryNavbarInfo = products.map((category: CategoryInfo) => {
    return { categoryId: category.categoryId, categoryName: category.categoryName };
  });
  const formattedMenuData = formatAllCategories(products);
  const formattedMenus = formatProducts(products);
  const currentMenus = selectedCategoryId && formattedMenuData[selectedCategoryId];
  const isOrderListEmpty = orderList.length === 0;

  const handleAddOrder = (menuOrder: ProductOrder) => dispatch({ type: 'ADD_ORDER', payload: { newOrder: menuOrder } });
  const handleRemoveOrder = (productId: number, size: string) =>
    dispatch({ type: 'REMOVE_ORDER', payload: { productId: productId, size: size } });
  const handleRemoveAllOrders = () => dispatch({ type: 'RESET' });
  const handleCategoryClick = (clickCategoryId: number) => {
    if (clickCategoryId === selectedCategoryId) return;
    setMainAnimationClassName(styles.fadeLeave);

    setTimeout(() => {
      setMainAnimationClassName(styles.fadeEnter);
      setSelectedCategoryId(clickCategoryId);
    }, 500);
  };

  useEffect(() => {
    if (!loading) {
      setSelectedCategoryId(products[0].categoryId);
    }
  }, [loading, products]);

  if (loading) return <LoadingIndicator text="메뉴를 불러오는 중입니다. 잠시만 기다려주세요!" />;
  if (error) return <div>{error}</div>;

  return (
    <div ref={homeRef} className={styles.home}>
      <CategoryNavbar
        selectedCategoryId={selectedCategoryId}
        categories={categoryNavbarInfo}
        handleCategoryClick={handleCategoryClick}
      />
      {currentMenus && (
        <Main
          animationClassName={mainAnimationClassName}
          handleAddOrder={handleAddOrder}
          products={currentMenus.products}
        />
      )}
      {!isOrderListEmpty && (
        <Cart
          navigate={navigate}
          homeRef={homeRef}
          products={formattedMenus}
          orderList={orderList}
          handleRemoveOrder={handleRemoveOrder}
          handleRemoveAllOrders={handleRemoveAllOrders}
        />
      )}
    </div>
  );
}
