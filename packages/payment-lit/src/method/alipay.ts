import resetStyles from '@unocss/reset/tailwind.css?inline';
import { LitElement, css, html, unsafeCSS, PropertyValues } from 'lit';
import { property, state } from 'lit/decorators.js';
import alipayIcon from '../../assets/image/alipay.png';
import varStyles from '../style/var';
import baseStyles from '../style/base';

export class methodAlipay extends LitElement {
  @property({ type: Boolean, reflect: true })
  open = false;

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
  orderId = '';
  @state()
  loading = false; // Loading 状态

  @property({ type: String })
  //     返回的支付二维码
  qrCode = '';

  //     发起支付
  //     private async createOrder() {
  //         try {
  //             this.loading = true;
  //             const response = await fetch('/apis/payment.plugin.halo.run/v1alpha1/plugins/pay/trade-precreate', {
  //                 method: 'POST',
  //                 headers: {
  //                     'Content-Type': 'application/json',
  //                 },
  //                 body: JSON.stringify({
  //                     postId: this.postId,
  //                     userId: this.userId,
  //                     title: this.resourceTitle,
  //                     price: this.amount,
  //                 }),
  //             });
  //
  //             if (!response.ok) {
  //                 throw new Error('Failed to create order');
  //             }
  //
  //             const { data } = await response.json();
  //             //    返回的支付二维码
  //             this.qrCode = `https://api.qrserver.com/v1/create-qr-code/?data=${data.qrCode}`;
  //             this.loading = false;
  //
  //             await this.pollOrderStatus(data.orderId);
  //
  //         }catch (error) {
  //             console.error(error);
  //             this.loading = false;
  //         }
  //
  //
  //     }
  private async pollOrderStatus(maxAttempts = 10) {
    let attempt = 0;
    const interval = setInterval(async () => {
      attempt++;
      const response = await fetch(
        `/apis/payment.plugin.halo.run/v1alpha1/plugins/pay/query-order?order=${this.orderId}`,
        {
          method: 'GET',
        }
      );

      if (!response.ok) {
        throw new Error('Failed to query order');
      }

      const { data } = await response.json();
      console.log(data);
      console.log(data === 'TRADE_SUCCESS');

      if (data === 'TRADE_SUCCESS') {
        clearInterval(interval);
        this.loading = false;
        this.open = false;
        // 刷新页面
        location.reload();
      }
      //     轮训次数
      if (attempt >= maxAttempts) {
        clearInterval(interval);
        this.loading = false;
        this.open = false;
        console.warn('Polling order status timeout');
        // 刷新页面
        location.reload();
      }
    }, 5000);
  }
  // 打开后发起轮训
  override async updated(changedProperties: PropertyValues) {
    if (changedProperties.has('open') && this.open) {
      // this.createOrder();
      await this.pollOrderStatus();
    }
  }

  private closeOrder() {
    this.open = false;
    //     刷新页面
    location.reload();
  }

  override render() {
    return html`
      <div class="mx-auto w-80 bg-white relative overflow-hidden rounded-lg shadow-xl">
        <div class="px-7 py-4">
          <div class="text-center">
            <div class="my-3">
              <h5 class="">
                <img src="${alipayIcon}" alt="支付宝支付" class="w-30 mx-auto" />
              </h5>
            </div>
            <span class="text-lg text-gray-4"> 支付宝扫码支付 ${this.amount} 元</span>
          </div>
          <div>
            <img id="pay-qrCode" src="${this.qrCode}" alt="支付二维码" />
          </div>
        </div>
        <div class="bg-blue-5 text-white text-center py-3">
          <span class="font-bold text-lg">请使用支付宝扫一扫</span>
          <p class="text-sm">扫码后等待5秒左右，切勿关闭扫码窗口</p>
        </div>
        <button
          @click=${this.closeOrder}
          class="absolute top-0 right-0 text-xl text-blue-6 p-2 bg-white shadow-md flex justify-center items-center rounded-full"
        >
          <i class="i-ion-close"></i>
        </button>
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

customElements.get('method-alipay') || customElements.define('method-alipay', methodAlipay);
declare global {
  interface HTMLElementTagNameMap {
    'method-alipay': methodAlipay;
  }
}
