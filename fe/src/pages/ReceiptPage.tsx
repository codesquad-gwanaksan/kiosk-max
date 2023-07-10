import { LoadingIndicator } from 'components/LoadingIndicator/LoadingIndicator';
import Receipt from 'components/Receipt/Receipt';
import { OrderSuccessInfo } from 'pages/types';
import { useEffect, useState } from 'react';
import { fetchReceipt } from '../api';
import styles from './ReceiptPage.module.css';

export default function ReceiptPage({ goHome, orderId }: { goHome: () => void; orderId: number }) {
  const [receiptData, setReceiptData] = useState<OrderSuccessInfo | undefined>(undefined);
  const [seconds, setSeconds] = useState<number>(10);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | undefined>(undefined);

  const getReceipt = async () => {
    const fetchedReceipt = await fetchReceipt(orderId);
    setLoading(false);

    if (!fetchedReceipt) {
      setError('주문 정보를 불러오는 데 실패했습니다.');
      return;
    }
    setReceiptData(fetchedReceipt);
  };

  useEffect(() => {
    getReceipt();
  }, []);

  useEffect(() => {
    const timer = setInterval(() => {
      setSeconds(prev => prev - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    if (seconds <= 0) {
      goHome();
    }
  }, [seconds, goHome]);

  if (loading) return <LoadingIndicator text="결제 정보를 불러오는 중입니다. 잠시만 기다려주세요!" />;

  return (
    <div className={styles.receiptPage}>
      <h1 className={styles.title}>주문이 완료되었습니다!</h1>
      {error && (
        <>
          <span className={styles.error}>{error}</span>
          <button className={styles.againRequestButton} onClick={getReceipt}>
            영수증 다시 조회하기
          </button>
        </>
      )}
      {!error && receiptData && <Receipt info={receiptData} />}
      <span className={styles.timerInfo}>(주의: 이 화면은 10초 뒤에 자동으로 사라집니다)</span>
      <span className={styles.seconds}>{`${seconds}초`}</span>
    </div>
  );
}
