import { createRoot } from 'react-dom/client';
import './global.css';
import App from './App';

/* index seguindo novo padrão do react 18 */
const container = document.getElementById('root');
const root = createRoot(container);

root.render(<App tab="App" />);