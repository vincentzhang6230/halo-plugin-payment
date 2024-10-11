import resetStyles from '@unocss/reset/tailwind.css?inline';
import { LitElement, css, html, unsafeCSS } from 'lit';
import { property, state } from 'lit/decorators.js';
import { payModal } from './payModal';
import varStyles from './style/var';
import baseStyles from './style/base';

// 构造支付组件
export class PaymentResourceCard extends LitElement {
  @property({ type: String })
  postId = '';

  @property({ type: String })
  resourceTitle = '';
  @property({ type: String })
  userId = '';
  // 支付金额
  @property({ type: String })
  amount = '';
  @property({ type: String })
  resourcesAddress = '';
  @state()
  loading = false;
  @property({ type: Boolean })
  showModal = false;

  override connectedCallback() {
    super.connectedCallback();
  }

  toggleModal() {
    const payModalEl = document.querySelector('pay-modal') as payModal;
    // 创建支付弹窗
    if (payModalEl) {
      payModalEl.openModal();
    }
  }
  // 根据是否支付显示资源链接
  renderContent() {
    if (this.resourcesAddress === 'null' || !this.resourcesAddress) {
      return html`
        <div class="w-full text-center   ">
          <div class="text-[#ff5722] mb-4 text-xl flex justify-center items-center font-bold">
            <i class="i-ion-locked  mr-1"></i>
            资源链接已隐藏
          </div>
          <div class="mb-4 text-center">
            <button
              @click="${this.toggleModal}"
              class="mx-auto cursor-pointer rounded-full  px-6 py-1.5 bg-[#b62335] text-white flex justify-center items-center"
            >
              <i class="i-ion-bag-remove-sharp mr-1"></i>
              支付 ${this.amount} 查看
            </button>
          </div>
        </div>
      `;
    }
    return html`
      <div class="text-center overflow-hidden">
        <div class="">
          <div class="font-bold">资源名称：${this.resourceTitle}</div>
          <div class="font-bold">链接地址：${this.resourcesAddress}</div>
        </div>
      </div>
    `;
  }

  override render() {
    return html`
      <div
        class="w-full rounded-md relative overflow-hidden py-6 px-4 bg-[#eaf6ff] border-dashed border-2 border-[#ffb1cb] mb-4"
      >
        <span
          class="absolute right-0 top-0 text-xs py-1.4 px-2 leading-4 rounded-bl-2 bg-[#ffb1cb] flex text-white"
        >
          <i class="i-ion-locked mr-1"></i>
          资源链接
        </span>
        ${this.renderContent()}
      </div>
    `;
  }
  static override styles = [
    unsafeCSS(resetStyles),
    varStyles,
    baseStyles,
    css`
      :host {
      }
      @unocss-placeholder;
    `,
  ];
}

customElements.get('payment-resource-card') ||
  customElements.define('payment-resource-card', PaymentResourceCard);
declare global {
  interface HTMLElementTagNameMap {
    'payment-resource-card': PaymentResourceCard;
  }
}
