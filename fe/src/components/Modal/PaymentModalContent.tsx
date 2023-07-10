import { requestCardOrder, requestCashOrder } from 'api';
import { LoadingIndicator } from 'components/LoadingIndicator/LoadingIndicator';
import { ProductOrder } from 'pages/types';
import { useRef, useState } from 'react';
import useOutsideClick from '../../hooks/useOutsideClick';
import CashPayment from './CashPayment';
import modalStyles from './Modal.module.css';
import styles from './PaymentModalContent.module.css';
import ConfirmModal from './ConfirmModal';

interface PaymentModalContentProps {
  totalPrice: number;
  handlePaymentCancelButtonClick: () => void;
  navigate: (path: string) => void;
  orderList: ProductOrder[];
}

export default function PaymentModalContent({
  totalPrice,
  handlePaymentCancelButtonClick,
  navigate,
  orderList,
}: PaymentModalContentProps) {
  const outsideModal = useRef<HTMLDivElement>(null);
  const [paymentOption, setPaymentOption] = useState<'card' | 'cash' | 'select'>('select');
  const [receivedPrice, setReceivedPrice] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [errorMessage, setErrorMessage] = useState<string>('');
  const [showConfirmModal, setShowConfirmModal] = useState<boolean>(false);

  const isCardPaymentOption = paymentOption === 'card';

  const handleCardPaymentClick = async () => {
    setPaymentOption('card');
    const response = await requestCardOrder(orderList, totalPrice);

    if (!response) {
      setErrorMessage('서버 에러: 잠시 후 결제를 다시 요청해주세요.');
      return;
    }

    setLoading(false);
    switch (response.errorCode.status) {
      case 200: {
        const orderId = response.data.orderId;
        navigate(`/receipt/orderId/${orderId}`);
        return;
      }
      case 400: {
        setErrorMessage(response.errorCode.message);
        return;
      }
      case 500: {
        setErrorMessage('서버 에러: 잠시 후 결제를 다시 요청해주세요.');
        return;
      }
    }
  };

  const handleCashPaymentClick = async () => {
    const response = await requestCashOrder(orderList, totalPrice, receivedPrice);

    if (!response) {
      setErrorMessage('서버 에러: 잠시 후 결제를 다시 요청해주세요.');
      return;
    }

    setLoading(false);
    switch (response.errorCode.status) {
      case 200: {
        const orderId = response.data.orderId;
        navigate(`/receipt/orderId/${orderId}`);
        return;
      }
      case 400: {
        setErrorMessage(response.errorCode.message);
        return;
      }
      case 500: {
        setErrorMessage('서버 에러: 잠시 후 결제를 다시 요청해주세요.');
        return;
      }
    }
  };

  const payWithCash = (amount: number) => {
    setReceivedPrice(receivedPrice + amount);
  };

  const handleCloseConfirmModal = () => {
    setShowConfirmModal(false);
  };

  const handleCancelPayment = () => {
    setShowConfirmModal(false);
    handlePaymentCancelButtonClick();
  };

  const handleOpenConfirmModal = () => {
    setShowConfirmModal(true);
  };

  useOutsideClick(outsideModal, handlePaymentCancelButtonClick);

  return (
    <div ref={outsideModal} className={modalStyles.dim}>
      {paymentOption === 'select' && (
        <div className={modalStyles.modalContent}>
          <button className={modalStyles.closeButton} onClick={handleOpenConfirmModal}>
            X
          </button>
          <div className={styles.paymentMethod}>
            <div className={styles.paymentIcon}>💳</div>
            <button className={styles.paymentOptionButton} onClick={handleCardPaymentClick}>
              카드결제
            </button>
          </div>
          <div className={styles.paymentMethod}>
            <div className={styles.paymentIcon}>💵</div>
            <button className={styles.paymentOptionButton} onClick={() => setPaymentOption('cash')}>
              현금결제
            </button>
          </div>
        </div>
      )}
      {paymentOption === 'cash' && (
        <CashPayment
          totalPrice={totalPrice}
          receivedPrice={receivedPrice}
          payWithCash={payWithCash}
          handlePaymentCancelButtonClick={handlePaymentCancelButtonClick}
          handleCashPaymentClick={handleCashPaymentClick}
        />
      )}
      {isCardPaymentOption && loading && <LoadingIndicator text="카드 결제중..." />}
      {errorMessage && <div className={styles.errorMessage}>{errorMessage}</div>}
      {showConfirmModal && (
        <ConfirmModal
          text={'결제를 취소하시겠습니까?'}
          onClickYesButton={handleCancelPayment}
          onClickNoButton={handleCloseConfirmModal}
        />
      )}
    </div>
  );
}
