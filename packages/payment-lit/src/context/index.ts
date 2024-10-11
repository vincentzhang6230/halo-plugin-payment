import { createContext } from '@lit/context';
import { ToastManager } from '../lit-toast';

export const toastContext = createContext<ToastManager | undefined>(Symbol('toastContext'));
