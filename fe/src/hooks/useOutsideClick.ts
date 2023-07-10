import { useEffect, MutableRefObject } from 'react';

const useOutsideClick = <T extends HTMLElement>(ref: MutableRefObject<T | null>, callback: () => void) => {
  const handleClickOutside = (event: MouseEvent) => {
    if (ref.current && ref.current === event.target) callback();
  };

  useEffect(() => {
    document.addEventListener('click', handleClickOutside);

    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, []);
};

export default useOutsideClick;
