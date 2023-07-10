import styles from './CashPayment.module.css';
import modalStyles from './Modal.module.css';
import { Button } from 'components/atoms/Button';

interface CashPaymentProps {
  totalPrice: number;
  receivedPrice: number;
  payWithCash: (amount: number) => void;
  handlePaymentCancelButtonClick: () => void;
  handleCashPaymentClick: () => void;
}

export default function CashPayment({
  totalPrice,
  receivedPrice,
  payWithCash,
  handlePaymentCancelButtonClick,
  handleCashPaymentClick,
}: CashPaymentProps) {
  return (
    <div className={modalStyles.modalContent}>
      <button className={modalStyles.closeButton} onClick={handlePaymentCancelButtonClick}>
        X
      </button>
      <div className={styles.cashOptionGrid}>
        <div className={styles.optionGridCell}>
          {' '}
          <Button
            label={'10000'}
            className={styles.cashOptionButton}
            onClick={() => {
              payWithCash(10000);
            }}
          />
        </div>
        <div className={styles.optionGridCell}>
          {' '}
          <Button
            label={'1000'}
            className={styles.cashOptionButton}
            onClick={() => {
              payWithCash(1000);
            }}
          />
        </div>
        <div className={styles.optionGridCell}>
          {' '}
          <Button
            label={'500'}
            className={styles.cashOptionButton}
            onClick={() => {
              payWithCash(500);
            }}
          />
        </div>
        <div className={styles.optionGridCell}>
          {' '}
          <Button
            label={'100'}
            className={styles.cashOptionButton}
            onClick={() => {
              payWithCash(100);
            }}
          />
        </div>
      </div>
      <div>
        <span>{`주문금액: ${totalPrice}`}</span>
        <span>{`투입금액: ${receivedPrice}`}</span>
      </div>
      <Button
        label={'현금 결제하기'}
        className={styles.cashPayment}
        disabled={totalPrice > receivedPrice}
        isSelected={totalPrice <= receivedPrice}
        selectedClassName={styles.selected}
        onClick={handleCashPaymentClick}
      />
    </div>
  );
}
