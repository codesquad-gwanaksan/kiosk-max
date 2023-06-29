import Button from 'components/atoms/Button';
import styles from './ConfirmModal.module.css';
import modalStyles from './Modal.module.css';
import useOutsideClick from '../../hooks/useOutsideClick';
import { useRef } from 'react';

type ConfirmModalProps = {
  text: string;
  onClickYesButton: () => void;
  onClickNoButton: () => void;
};

export default function ConfirmModal({ text, onClickYesButton, onClickNoButton }: ConfirmModalProps) {
  const outsideModal = useRef<HTMLDivElement>(null);

  useOutsideClick(outsideModal, onClickNoButton);

  return (
    <div ref={outsideModal} className={modalStyles.dim}>
      <div className={styles.modalContent}>
        <p className={styles.textContents}>{text}</p>
        <div className={styles.buttons}>
          <Button label={'돌아가기'} className={styles.noButton} onClick={onClickNoButton} />
          <Button label={'취소하기'} className={styles.yesButton} onClick={onClickYesButton} />
        </div>
      </div>
    </div>
  );
}
