import styles from './MenuItem.module.css';

interface MenuItemProps {
  productId: number;
  menuName: string;
  menuImg: string;
  menuPrice: number;
  className?: string;
  isBest?: boolean;
  hasLarge?: boolean;
  hasSmall?: boolean;
  hasHot?: boolean;
  hasIce?: boolean;
  openOrderModal?: () => void;
  setSelectedMenu?: any;
}

export default function MenuItem({
  productId,
  menuName,
  menuImg,
  menuPrice,
  isBest,
  hasLarge,
  hasSmall,
  hasHot,
  hasIce,
  className = styles.menuItem,
  openOrderModal,
  setSelectedMenu,
}: MenuItemProps) {
  const clickMenu = () => {
    setSelectedMenu &&
      setSelectedMenu({
        name: menuName,
        productId: productId,
        price: menuPrice,
        imgUrl: menuImg,
        hasLarge: hasLarge,
        hasSmall: hasSmall,
        hasHot: hasHot,
        hasIce: hasIce,
      });
    openOrderModal && openOrderModal();
  };

  return (
    <div onClick={clickMenu} className={className}>
      {isBest && <div className={styles.best}>인기</div>}
      <img src={menuImg} alt={menuName} />
      <span>{menuName}</span>
      <span>{menuPrice}</span>
    </div>
  );
}
