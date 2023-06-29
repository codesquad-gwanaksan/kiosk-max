import PaymentModalContent from 'components/Modal/PaymentModalContent';
import { ProductOrder, Products } from 'pages/types';
import { useEffect, useRef, useState } from 'react';
import { createPortal } from 'react-dom';
import { formatSameProductIdList } from 'utils';
import styles from './Main.module.css';
import MenuItem from './MenuItem';
import ConfirmModal from 'components/Modal/ConfirmModal';

interface CartProps {
  homeRef: React.RefObject<HTMLDivElement>;
  orderList: ProductOrder[];
  products: Products;
  navigate: (path: string) => void;
  handleRemoveOrder: (productId: number) => void;
  handleRemoveAllOrders: () => void;
}

export default function Cart({
  navigate,
  homeRef,
  handleRemoveAllOrders,
  handleRemoveOrder,
  products,
  orderList,
}: CartProps) {
  const [seconds, setSeconds] = useState(30);
  const intervalRef: { current: null | NodeJS.Timer } = useRef(null);
  const [showPaymentModal, setShowPaymentModal] = useState<boolean>(false);
  const [showConfirmModal, setShowConfirmModal] = useState<boolean>(false);

  const formattedSameProduct = formatSameProductIdList(orderList);
  const totalPrice = orderList.reduce((acc, cur) => {
    const { productId, amount } = cur;
    return acc + products[productId].price * amount;
  }, 0);

  const resetCounter = (seconds: number) => {
    setSeconds(seconds);

    intervalRef.current = setInterval(() => {
      setSeconds(prev => prev - 1);
    }, 1000);
  };

  const handlePaymentButtonClick = () => {
    clearInterval(intervalRef.current!);
    setShowPaymentModal(true);
  };

  const handlePaymentCancelButtonClick = () => {
    setShowPaymentModal(false);
    resetCounter(30);
  };

  const confirmRemoveAllOrders = () => {
    clearInterval(intervalRef.current!);
    setShowConfirmModal(true);
  };

  const closeConfirmModal = () => {
    setShowConfirmModal(false);
    resetCounter(30);
  };

  const removeAllOrders = () => {
    handleRemoveAllOrders();
    setShowConfirmModal(false);
  };

  useEffect(() => {
    intervalRef.current = setInterval(() => {
      setSeconds(prev => prev - 1);
    }, 1000);

    return () => clearInterval(intervalRef.current!);
  }, []);

  useEffect(() => {
    setSeconds(30);
  }, [orderList]);

  useEffect(() => {
    if (seconds <= 0) {
      handleRemoveAllOrders();
    }
  }, [seconds]);

  return (
    <div className={styles.cart}>
      <div className={styles.orderItems}>
        {formattedSameProduct.map(order => {
          const { productId, amount } = order;
          const menu = products[productId];
          return (
            <div key={menu.productId} className={styles.itemWrapper}>
              <div className={styles.amount}>{amount}</div>
              <MenuItem
                classNames={[styles.orderItem]}
                productId={menu.productId}
                menuName={menu.name}
                menuImg={menu.imgUrl}
                menuPrice={menu.price}
              />
              <button className={styles.menuCancelButton} onClick={() => handleRemoveOrder(menu.productId)}>
                X
              </button>
            </div>
          );
        })}
      </div>
      <div className={styles.buttons}>
        <span>
          총 결제 금액 <span className={styles.totalPrice}>₩ {totalPrice.toLocaleString('ko-KR')}</span>
        </span>
        <span className={styles.timer}>{seconds}초 뒤에 메뉴가 전체 취소돼요!</span>
        <button onClick={confirmRemoveAllOrders} className={styles.allCancelButton}>
          전체취소
        </button>
      </div>
      <button className={styles.orderButton} onClick={handlePaymentButtonClick}>
        결제하기
      </button>
      {showPaymentModal &&
        createPortal(
          <PaymentModalContent
            navigate={navigate}
            totalPrice={totalPrice}
            orderList={orderList}
            handlePaymentCancelButtonClick={handlePaymentCancelButtonClick}
          />,
          homeRef.current!
        )}
      {showConfirmModal &&
        createPortal(
          <ConfirmModal
            text={'메뉴를 모두 취소하시겠습니까?'}
            onClickYesButton={removeAllOrders}
            onClickNoButton={closeConfirmModal}
          />,
          homeRef.current!
        )}
    </div>
  );
}
