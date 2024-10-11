import { payModal } from './payModal';
export * from './paymentContentCard';
export * from './paymentResourceCard';
export * from './payModal';
export * from './lit-toast';
export { paymentInit } from './paymentInit';

const payModalEl = document.querySelector('pay-modal') as payModal;
// 初始化
export function pay() {
  if (payModalEl) {
    payModalEl.openModal();
  }
}
