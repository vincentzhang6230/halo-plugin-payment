import resetStyles from '@unocss/reset/tailwind.css?inline';
import { LitElement, css, html, unsafeCSS } from 'lit';
import { property, state } from 'lit/decorators.js';
import { payModal } from './payModal';
import varStyles from './style/var';
import baseStyles from './style/base';

// 构造支付组件
export class PaymentContentCard extends LitElement {
  @property({ type: String })
  postId = '';

  @property({ type: String })
  resourceTitle = '';
  @property({ type: String })
  userId = '';
  // 支付金额
  @property({ type: String })
  amount = '';
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

  override render() {
    return html`
            <div class="w-full rounded-md relative overflow-hidden py-6 px-4 bg-[#eaf6ff] border-dashed border-2 border-[#ffb1cb] mb-4">
                <span class="absolute right-0 top-0 text-xs py-1.4 px-2 leading-4 rounded-bl-2 bg-[#ffb1cb] flex text-white">
                    <i class="i-ion-locked mr-1"></i>
                    隐藏内容
                </span>
                <div class="w-full text-center   ">
                    <div class="text-[#ff5722] mb-4 text-xl flex justify-center items-center font-bold">
                        <i class="i-ion-locked  mr-1"></i>
                        本内容需权限查看
                        
                    </div>
                    <div class="mb-4 text-center">
                        <button  @click="${this.toggleModal}" class="mx-auto cursor-pointer rounded-full  px-6 py-1.5 bg-[#b62335] text-white flex justify-center items-center" >
                            <i class="i-ion-bag-remove-sharp mr-1"></i>
                            购买查看权限
                        </button>
                    </div>
                    <div class="text-[#999] mt-3 relative ">
                        <ul class=" bg-[#DBECFC] rounded-md py-1 px-2 z-3 flex flex-wrap inline-flex  justify-center items-center before:content-[''] before:absolute before:-top-4 before:left-1/2 before:-translate-x-1/2 before:border-[10px] before:border-transparent before:border-b-[#C3DDF9]"">
                            <li class="flex justify-center items-center mx-3 my-1 text-green-6">
                                <i class="i-ion-logo-bitcoin "></i>
                                <span class="">${this.amount}</span>
                            </li>
                        </ul>
                        
                        
                    </div>
                    
                </div>
                
            </div>
        `;
  }
  static override styles = [
    varStyles,
    baseStyles,
    unsafeCSS(resetStyles),
    css`
      :host {
      }
      @unocss-placeholder;
    `,
  ];
}

customElements.get('payment-content-card') ||
  customElements.define('payment-content-card', PaymentContentCard);
declare global {
  interface HTMLElementTagNameMap {
    'payment-content-card': PaymentContentCard;
  }
}
