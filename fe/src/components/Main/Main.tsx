import MenuItem from 'components/MenuItem';
import OrderModal from 'components/Modal/OrderModal';
import { ProductInfo, ProductOrder } from 'pages/types';
import { useRef, useState } from 'react';
import useOutsideClick from '../../hooks/useOutsideClick';
import styles from './Main.module.css';

interface MainProps {
  animationClassName: string;
  handleAddOrder: (menuOrder: ProductOrder) => void;
  products: ProductInfo[];
}

export default function Main({ animationClassName, handleAddOrder, products }: MainProps) {
  const outsideModal = useRef<HTMLDivElement>(null);
  const [isOrderModalOpen, setOrderModal] = useState<boolean>(false);
  const [selectedMenu, setSelectedMenu] = useState<ProductInfo | undefined>(undefined);
  const mainRef = useRef<HTMLDivElement>(null);

  const openOrderModal = () => setOrderModal(true);
  const closeOrderModal = () => setOrderModal(false);

  useOutsideClick(outsideModal, closeOrderModal);

  return (
    <div ref={mainRef} className={`${styles.main} ${animationClassName}`}>
      {products.map(menu => (
        <MenuItem
          key={menu.productId}
          productId={menu.productId}
          menuName={menu.name}
          isBest={menu.isBest}
          menuImg={menu.imgUrl}
          menuPrice={menu.price}
          hasLarge={menu.hasLarge}
          hasSmall={menu.hasSmall}
          hasHot={menu.hasHot}
          hasIce={menu.hasIce}
          openOrderModal={openOrderModal}
          setSelectedMenu={setSelectedMenu}
        />
      ))}
      {isOrderModalOpen && selectedMenu && (
        <div ref={outsideModal} className={styles.mainDim}>
          <OrderModal handleAddOrder={handleAddOrder} menu={selectedMenu} closeOrderModal={closeOrderModal} />
        </div>
      )}
    </div>
  );
}
