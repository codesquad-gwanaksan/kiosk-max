import styles from './CategoryNavbar.module.css';

interface CategoryButtonProps {
  id: number;
  categoryName: string;
  isActive: boolean;
  handleCategoryClick: (categoryId: number) => void;
}

export default function CategoryButton({ id, handleCategoryClick, categoryName, isActive }: CategoryButtonProps) {
  const className = styles.categoryButton;

  return (
    <button onClick={() => handleCategoryClick(id)} className={isActive ? `${className} ${styles.active}` : className}>
      {categoryName}
    </button>
  );
}