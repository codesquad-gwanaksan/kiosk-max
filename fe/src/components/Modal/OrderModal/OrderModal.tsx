import MenuItem from 'components/MenuItem/MenuItem';
import ModalStyles from 'components/Modal/Modal.module.css';
import { Button } from 'components/atoms/Button';
import { EXTRA_PRICE } from 'constant';
import { ProductInfo, ProductOrder } from 'pages/types';
import { useState } from 'react';
import AddButton from './AddButton';
import MenuOption from './MenuOption';
import styles from './OrderModal.module.css';

interface OrderModalProps {
  menu: ProductInfo;
  closeOrderModal: () => void;
  handleAddOrder: (menuOrder: ProductOrder) => void;
}

export default function OrderModal({ menu, closeOrderModal, handleAddOrder }: OrderModalProps) {
  const [selectedSize, setSelectedSize] = useState('');
  const [selectedTemp, setSelectedTemp] = useState('');
  const [amount, setAmount] = useState(1);
  const sendOrderData = (handleAddOrder: (menuOrder: ProductOrder) => void) => {
    const orderData = {
      productId: menu.productId,
      name: menu.name,
      size: selectedSize,
      temperature: selectedTemp,
      amount: amount,
    };
    handleAddOrder(orderData);
  };
  const isLargeSize = selectedSize === 'Large';

  return (
    <div className={styles.wrap}>
      <Button label={'X'} className={ModalStyles.closeButton} onClick={closeOrderModal}></Button>
      <div className={styles.contents}>
        <MenuItem
          productId={menu.productId}
          className={styles.menuItem}
          menuName={menu.name}
          menuImg={menu.imgUrl}
          menuPrice={isLargeSize ? menu.price + EXTRA_PRICE : menu.price}
          hasHot={menu.hasHot}
          hasIce={menu.hasIce}
          hasLarge={menu.hasLarge}
          hasSmall={menu.hasSmall}
        />
        <MenuOption
          hasLarge={menu.hasLarge}
          hasSmall={menu.hasSmall}
          hasHot={menu.hasHot}
          hasIce={menu.hasIce}
          selectedSize={selectedSize}
          selectedTemp={selectedTemp}
          setSelectedSize={setSelectedSize}
          setSelectedTemp={setSelectedTemp}
          amount={amount}
          setAmount={setAmount}
        />
      </div>
      <AddButton
        handleAddOrder={handleAddOrder}
        closeOrderModal={closeOrderModal}
        sendOrderData={sendOrderData}
        selectedSize={selectedSize}
        selectedTemp={selectedTemp}
      />
    </div>
  );
}
