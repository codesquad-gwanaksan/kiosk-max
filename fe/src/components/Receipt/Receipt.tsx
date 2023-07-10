import { OrderSuccessInfo } from '../../pages/types';
import { calculateTotalAmount } from '../../utils';
import styles from './Receipt.module.css';

export default function Receipt({ info }: { info: OrderSuccessInfo }) {
  const { orders, orderProducts, payment } = info;

  const paymentMethod = payment.method === 'cash' ? '현금' : '카드';
  const isCashPayment = payment.method === 'cash';
  const calculatedAmount = calculateTotalAmount(orderProducts);
  const keys = Object.keys(calculatedAmount);
  const mappedArray = keys.map(key => ({
    name: key,
    amount: calculatedAmount[key],
  }));

  return (
    <div className={styles.receiptWrap}>
      <h1 className={styles.orderNumber}>{`주문번호 ${orders.orderNumber}`}</h1>
      <span className={styles.orderTime}>{`${orders.orderDatetime}`}</span>
      <div className={styles.orderLists}>
        {mappedArray.map((list: OrderListProps, index: number) => {
          return <OrderList key={index} list={list} />;
        })}
      </div>
      <div className={styles.paymentInfo}>
        <span>{`결제방식: ${paymentMethod}`}</span>
        {isCashPayment && <span>{`투입금액: ${payment.receivedPrice}`}</span>}
        <span>{`총 결제금액: ${payment.totalPrice}`}</span>
        {isCashPayment && <span>{`거스름돈: ${payment.remainedPrice}`}</span>}
      </div>
    </div>
  );
}

interface OrderListProps {
  name: string;
  amount: number;
}

function OrderList({ list }: { list: OrderListProps }) {
  return (
    <div className={styles.orderList}>
      <span>{`${list.name}`}</span>
      <span>{`${list.amount}`}</span>
    </div>
  );
}
