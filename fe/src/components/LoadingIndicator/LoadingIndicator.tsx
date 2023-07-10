import styles from './LoadingIndicator.module.css';

export function LoadingIndicator({ text }: { text: string }) {
  return (
    <div className={styles.loadingIndicator}>
      <Indicator />
      <span>{text}</span>
    </div>
  );
}

function Indicator() {
  return (
    <div className={styles.indicatorWrap}>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  );
}
