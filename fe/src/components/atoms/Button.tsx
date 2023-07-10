type ButtonProps = {
  label: string;
  className: string;
  isSelected?: boolean;
  selectedClassName?: string;
  onClick: () => void;
  disabled?: boolean;
};

export function Button({ label, className, isSelected, selectedClassName, onClick, disabled }: ButtonProps) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={`${className} ${isSelected ? `${selectedClassName}` : ''}`}
    >
      {label}
    </button>
  );
}

type ImgButtonProps = {
  label: string;
  className: string;
  isSelected?: boolean;
  selectedClassName?: string;
  onClick: () => void;
  imgSrc: string;
  imgAlt: string;
  disabled?: boolean;
};

export function ImgButton({
  label,
  className,
  isSelected,
  selectedClassName,
  onClick,
  imgSrc,
  imgAlt,
  disabled,
}: ImgButtonProps) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={`${className} ${isSelected ? `${selectedClassName}` : ''}`}
    >
      <div>
        <img src={imgSrc} alt={imgAlt} />
      </div>
      {label}
    </button>
  );
}
