import resetStyles from '@unocss/reset/tailwind.css?inline';
import { consume } from '@lit/context';
import { toastContext } from './context';
import { ToastManager } from './lit-toast';
import baseStyles from './style/base';
import varStyles from './style/var';

import { LitElement, css, html, unsafeCSS } from 'lit';
import { property, state } from 'lit/decorators.js';
import './method/alipay';

export class payModal extends LitElement {
  @property({ type: Boolean, reflect: true })
  open = false;

  @property({ type: String })
  payType: 'alipay' | 'wechat' = 'alipay';

  @property({ type: String })
  postId = '';

  @property({ type: String })
  resourceTitle = '';
  @property({ type: String })
  userId = '';
  // 支付金额
  @property({ type: String })
  amount = '';

  // 支付状态
  @state()
  isPaying = false;
  // 支付状态
  @state()
  payLoading = false;
  @state()
  qcCode = '';
  @state()
  orderId = '';

  @state()
  @consume({ context: toastContext, subscribe: true })
  toastManager: ToastManager | undefined;

  override connectedCallback() {
    super.connectedCallback();
    console.log(this.toastManager); // 检查 toastManager 是否在此处已定义
  }

  private closeModal() {
    this.open = false;
  }
  private handlePay() {
    this.isPaying = true;
  }
  // 打开支付弹窗
  openModal() {
    if (this.userId === 'null' || !this.userId) {
      console.log(this.toastManager);
      this.toastManager?.warn('正在跳转登入');
      console.error('userId is required');

      //     跳转登录
      location.href = '/console';
      return;
    }
    this.open = true;
  }

  //     发起支付
  private async createOrder() {
    try {
      this.payLoading = true;
      const response = await fetch(
        '/apis/payment.plugin.halo.run/v1alpha1/plugins/pay/trade-precreate',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            postId: this.postId,
            userId: this.userId,
            title: this.resourceTitle,
            price: this.amount,
            payType: this.payType,
          }),
        }
      );

      if (!response.ok) {
        throw new Error('Failed to create order');
      }

      const { data } = await response.json();
      this.qcCode = data.qrCode;
      this.orderId = data.orderId;
      this.payLoading = false;
      this.handlePay();
    } catch (error) {
      console.error(error);
      this.payLoading = false;
    }
  }

  override render() {
    return html`
      <div
        class="fixed w-full h-screen flex justify-center items-center bg-white/50 top-0 left-0 z-50 backdrop-blur-sm"
        style="display: ${this.open ? 'flex' : 'none'};"
      >
        ${this.isPaying
          ? html`
              <method-alipay
                .postId="${this.postId}"
                .userId="${this.userId}"
                .resourceTitle="${this.resourceTitle}"
                .amount="${this.amount}"
                .open="${this.isPaying}"
                .orderId="${this.orderId}"
                .qrCode="${this.qcCode}"
              ></method-alipay>
            `
          : html`
              <div class="w-80 bg-white text-gray-7 relative rounded-lg shadow">
                <div class="px-8 py-2 ">
                  <div class="text-2xl font-bold p-5 hover:opacity-70 cursor-pointer">
                    <div
                      id="alipay"
                      class="flex justify-center items-center"
                      @click="${this.createOrder}"
                    >
                      <i class="i-ion-logo-alipay text-blue text-3xl"></i>
                      <span class="mx-3">支付宝</span>
                    </div>
                  </div>
                  <div style="${this.payLoading ? '' : 'display: none'}" class="text-center">
                    <svg
                      class="animate-spin  mx-auto h-7 w-7 text-blue-6"
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                    >
                      <circle
                        class="opacity-25"
                        cx="12"
                        cy="12"
                        r="10"
                        stroke="currentColor"
                        stroke-width="4"
                      ></circle>
                      <path
                        class="opacity-75"
                        fill="currentColor"
                        d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                      ></path>
                    </svg>
                  </div>
                </div>
                <button
                  @click=${this.closeModal}
                  class="absolute top-0 right-0 text-xl text-blue-6 p-2 bg-white shadow-md flex justify-center items-center rounded-full"
                >
                  <i class="i-ion-close"></i>
                </button>
              </div>
            `}
      </div>
    `;
  }
  static override styles = [
    varStyles,
    baseStyles,
    unsafeCSS(resetStyles),
    css`
      :host {
        width: 100%;
      }
      @unocss-placeholder;
    `,
  ];
}

customElements.get('pay-modal') || customElements.define('pay-modal', payModal);
declare global {
  interface HTMLElementTagNameMap {
    'pay-modal': payModal;
  }
}
