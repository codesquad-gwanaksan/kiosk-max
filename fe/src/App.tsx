import Home from 'pages/Home';
import ReceiptPage from 'pages/ReceiptPage';
import { useState } from 'react';
import './App.css';

export default function App() {
  return <Router />;
}

function Router() {
  const [page, setPage] = useState('/');
  const navigate = (path: string) => {
    setPage(path);
    window.history.pushState({}, '', path);
  };

  const goHome = () => navigate('/');

  let content;
  const orderId = Number(page.split('/')[3]);

  switch (page) {
    case '/': {
      content = <Home navigate={navigate} />;
      break;
    }
    case `/receipt/orderId/${orderId}`: {
      content = <ReceiptPage orderId={orderId} goHome={goHome} />;
    }
  }
  return <div className="App">{content}</div>;
}