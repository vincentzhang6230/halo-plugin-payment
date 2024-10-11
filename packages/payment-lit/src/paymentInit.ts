import resetStyles from '@unocss/reset/tailwind.css?inline';
import { LitElement, css, unsafeCSS, html } from 'lit';
import { property, state } from 'lit/decorators.js';
import { payModal } from './payModal';
import { LitToast } from './lit-toast';

// 构造支付组件
export class paymentInit extends LitElement {
  @property({ type: String })
  postId = '';

  @property({ type: String })
  resourceTitle = '';
  @property({ type: String })
  userId = '';
  // 支付金额
  @property({ type: String })
  amount = '';
  // 支付方式
  @property({ type: String })
  payType: 'alipay' | 'wechat' = 'alipay';
  @state()
  loading = false;
  @property({ type: Boolean })
  showModal = false;

  init() {
    const payModalEl = document.querySelector('pay-modal') as payModal;
    const toastManager = document.querySelector('pay-toast-container') as LitToast;
    // 创建支付弹窗
    if (!toastManager) {
      const toast = document.createElement('pay-toast-container') as LitToast;
      document.body.appendChild(toast);
    }
    if (!payModalEl) {
      const modalOverlay = document.createElement('pay-modal') as payModal;
      modalOverlay.payType = this.payType;
      modalOverlay.open = false;
      modalOverlay.postId = this.postId;
      modalOverlay.userId = this.userId;
      modalOverlay.resourceTitle = this.resourceTitle;
      modalOverlay.amount = this.amount;
      document.body.appendChild(modalOverlay);
    }
  }

  override render() {
    return html`
      <script>
        ${this.init()};
      </script>
    `;
  }

  static override styles = [
    unsafeCSS(resetStyles),
    css`
      :host {
        width: 100%;
      }
      @unocss-placeholder;
    `,
  ];
}
customElements.get('payment-init') || customElements.define('payment-init', paymentInit);
declare global {
  interface HTMLElementTagNameMap {
    'payment-init': paymentInit;
  }
}
