import { ProductOrder } from 'pages/types';
import styles from './OrderModal.module.css';

interface AddButtonProps {
  closeOrderModal: () => void;
  sendOrderData: (handleAddOrder: (menuOrder: ProductOrder) => void) => void;
  handleAddOrder: (menuOrder: ProductOrder) => void;
  selectedSize: string;
  selectedTemp: string;
}

export default function AddButton({
  closeOrderModal,
  sendOrderData,
  handleAddOrder,
  selectedSize,
  selectedTemp,
}: AddButtonProps) {
  const isDisabled = selectedSize === '' || selectedTemp === '';
  const handleAddButtonClick = () => {
    closeOrderModal();
    sendOrderData(handleAddOrder);
  };

  return (
    <button className={styles.addButton} type="button" disabled={isDisabled} onClick={handleAddButtonClick}>
      담기
    </button>
  );
}
