<!--<script setup lang="ts">-->
<!--import { nodeViewProps, NodeViewWrapper } from "@halo-dev/richtext-editor";-->
<!--import { computed } from "vue";-->

<!--const props = defineProps(nodeViewProps);-->
<!--const resource = computed({-->
<!--  get: () => {-->
<!--    return props.node.attrs.resourceContent;-->
<!--  },-->
<!--  set: (value) => {-->
<!--    props.updateAttributes({ resourceContent: value });-->
<!--  },-->
<!--});-->
<!--</script>-->
<!--<template>-->
<!--  <node-view-wrapper class="resource-card">-->
<!--    <div class="container">-->
<!--      <div class="zib-widget pay-box order-type-2" id="posts-pay">-->
<!--        <div class="flex pay-flexbox">-->
<!--          <div class="flex0 relative mr20 hide-sm pay-thumb">-->
<!--            <div class="graphic">-->
<!--              <img-->
<!--                src="https://static.novalinx.top/hexo-blog/keqing.jpg"-->
<!--                alt="测试购买资源-四次元资源网"-->
<!--                class="fit-cover ls-is-cached lazyloaded"-->
<!--              />-->
<!--              <div class="abs-center text-center left-bottom"></div>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="flex1 flex xx jsb">-->
<!--            <dt class="text-ellipsis pay-title" style="padding-right: 48px">-->
<!--              <input v-model="resource" />-->
<!--            </dt>-->
<!--            <div class="mt6 em09 muted-2-color">-->
<!--              此内容为付费资源，请付费后查看-->
<!--            </div>-->
<!--            <div class="price-box">-->
<!--              <div class="price-box">-->
<!--                <div class="c-red">-->
<!--                  <b class="em3x"><span class="pay-mark">￥</span>0.01</b>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
<!--            <div>-->
<!--              <span class="vip-price-buts but-average inline"-->
<!--                ><span-->
<!--                  href="javascript:;"-->
<!--                  class="but vip-price signin-loader"-->
<!--                  vip-level="1"-->
<!--                  data-toggle="tooltip"-->
<!--                  title=""-->
<!--                  data-original-title="开通黄金会员"-->
<!--                  ><svg-->
<!--                    class="mr3"-->
<!--                    aria-hidden="true"-->
<!--                    data-viewbox="0 0 1024 1024"-->
<!--                    viewBox="0 0 1024 1024"-->
<!--                  >-->
<!--                    <use xlink:href="#icon-vip_1"></use></svg-->
<!--                  >黄金会员<span class="em12 ml3 vip-price-text"-->
<!--                    >免费</span-->
<!--                  ></span-->
<!--                ><span-->
<!--                  href="javascript:;"-->
<!--                  class="but vip-price signin-loader"-->
<!--                  vip-level="2"-->
<!--                  data-toggle="tooltip"-->
<!--                  title=""-->
<!--                  data-original-title="开通钻石会员"-->
<!--                  ><svg-->
<!--                    class="mr3"-->
<!--                    aria-hidden="true"-->
<!--                    data-viewbox="0 0 1024 1024"-->
<!--                    viewBox="0 0 1024 1024"-->
<!--                  >-->
<!--                    <use xlink:href="#icon-vip_2"></use></svg-->
<!--                  >钻石会员<span class="em12 ml3 vip-price-text"-->
<!--                    >免费</span-->
<!--                  ></span-->
<!--                ></span-->
<!--              >-->
<!--            </div>-->
<!--            <div class="text-right mt10">-->
<!--              <span class="badg px12 c-yellow-2"-->
<!--                >暂时无法购买，请与站长联系</span-->
<!--              >-->
<!--              <div class="pay-extra-hide px12 mt6" style="font-size: 12px">-->
<!--                您当前未登录！建议登陆后购买，可保存购买订单-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="pay-tag abs-center">-->
<!--          <i class="fa fa-download mr3"></i>付费资源-->
<!--        </div>-->
<!--        <badge class="img-badge hot jb-blue px12">已售 1</badge>-->
<!--      </div>-->
<!--    </div>-->
<!--  </node-view-wrapper>-->
<!--</template>-->
<!--<style>-->
<!--/*@media (max-width: 996px) {*/-->
<!--/*    .container {*/-->
<!--/*        width: 95%;*/-->
<!--/*    }*/-->
<!--/*}*/-->
<!--:root {-->
<!--  &#45;&#45;this-bg: linear-gradient(135deg, #ff5e7f 30%, #ff967e 100%);-->
<!--  &#45;&#45;this-color: #fff;-->
<!--  &#45;&#45;body-bg-color: #f5f6f7;-->
<!--  &#45;&#45;main-color: #4e5358;-->
<!--  &#45;&#45;main-bg-color: #fff;-->
<!--  &#45;&#45;main-shadow: rgba(116, 116, 116, 0.08);-->
<!--  &#45;&#45;main-radius: 8px;-->
<!--  &#45;&#45;lazy-animation: lazy_blur;-->
<!--  &#45;&#45;muted-2-color: #999;-->
<!--  &#45;&#45;muted-border-color: rgba(0, 0, 0, 0.03);-->
<!--  &#45;&#45;muted-color: #777;-->
<!--}-->

<!--.container {-->
<!--  max-width: 996px;-->
<!--}-->

<!--.pay-box {-->
<!--  font-size: 14px;-->
<!--  position: relative;-->
<!--  overflow: hidden;-->
<!--  line-height: 1.4;-->
<!--}-->

<!--.pay-thumb {-->
<!--  width: 40%;-->
<!--}-->

<!--.zib-widget {-->
<!--  clear: both;-->
<!--  background: var(&#45;&#45;main-bg-color);-->
<!--  padding: 15px;-->
<!--  box-shadow: 0 0 10px var(&#45;&#45;main-shadow);-->
<!--  border-radius: var(&#45;&#45;main-radius);-->
<!--  margin-bottom: 20px;-->
<!--}-->

<!--.flex .flex0 {-->
<!--  flex: none;-->
<!--}-->

<!--.mr20 {-->
<!--  margin-right: 20px;-->
<!--}-->

<!--.relative {-->
<!--  position: relative;-->
<!--}-->

<!--.graphic,-->
<!--.page-cover {-->
<!--  text-shadow: 0 0 5px rgba(0, 0, 0, 0.2);-->
<!--  &#45;&#45;main-color: #fff;-->
<!--  color: #fff;-->
<!--  position: relative;-->
<!--  overflow: hidden;-->
<!--  box-shadow: 0 0 10px var(&#45;&#45;main-shadow);-->
<!--  padding: 0;-->
<!--  padding-bottom: 70%;-->
<!--  border-radius: var(&#45;&#45;main-radius);-->
<!--}-->

<!--.graphic > .fit-cover {-->
<!--  position: absolute;-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--}-->

<!--.fit-cover,-->
<!--.radius-cover {-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  transition: all 0.2s;-->
<!--  overflow: hidden;-->
<!--  -o-object-fit: cover;-->
<!--  object-fit: cover;-->
<!--}-->

<!--.lazyloaded {-->
<!--  opacity: 1;-->
<!--  -webkit-filter: blur(0);-->
<!--  filter: blur(0);-->
<!--  transform: unset;-->
<!--  transition: 0.3s;-->
<!--  &#45;&#45;lazy-animation: lazy_blur;-->
<!--}-->

<!--img {-->
<!--  max-width: 100%;-->
<!--  max-height: 100%;-->
<!--  vertical-align: middle;-->
<!--  border: 0;-->
<!--}-->

<!--.abs-center.conter-bottom,-->
<!--.abs-center.left-bottom,-->
<!--.abs-center.right-bottom {-->
<!--  top: auto;-->
<!--  transform: unset;-->
<!--  bottom: 0;-->
<!--}-->

<!--.abs-center {-->
<!--  left: 0;-->
<!--  width: 100%;-->
<!--}-->

<!--.abs-center,-->
<!--.form-select::before,-->
<!--.payvip-icon:before,-->
<!--.toggle-radius .fa,-->
<!--.toggle-radius .icon {-->
<!--  position: absolute;-->
<!--  top: 50%;-->
<!--  right: 0.7em;-->
<!--  transform: translateY(-50%);-->
<!--  z-index: 1;-->
<!--}-->

<!--.text-center {-->
<!--  text-align: center;-->
<!--}-->

<!--.pay-box .pay-tag,-->
<!--.vip-product .vip-tag {-->
<!--  font-size: 13px;-->
<!--  padding: 3px 10px;-->
<!--  right: auto;-->
<!--  width: auto;-->
<!--  top: 10px;-->
<!--  background: var(&#45;&#45;this-bg);-->
<!--  color: var(&#45;&#45;this-color);-->
<!--  border-radius: var(&#45;&#45;main-radius) 0 var(&#45;&#45;main-radius) 0;-->
<!--  line-height: 1.4;-->
<!--  z-index: 1;-->
<!--}-->

<!--.order-type-2 .pay-tag {-->
<!--  &#45;&#45;this-bg: linear-gradient(135deg, #ff5e7f 30%, #ff967e 100%);-->
<!--}-->

<!--.flex .flex1 {-->
<!--  flex: auto;-->
<!--  overflow: hidden;-->
<!--}-->

<!--.flex.jsb,-->
<!--.inflex.jsb {-->
<!--  justify-content: space-between;-->
<!--}-->

<!--.flex.xx,-->
<!--.inflex.xx {-->
<!--  flex-direction: column;-->
<!--}-->

<!--.flex {-->
<!--  display: flex;-->
<!--}-->

<!--.text-ellipsis {-->
<!--  overflow: hidden;-->
<!--  text-overflow: ellipsis;-->
<!--  white-space: nowrap;-->
<!--  padding-right: 48px;-->
<!--}-->

<!--dd,-->
<!--dl,-->
<!--dt,-->
<!--ol,-->
<!--ul {-->
<!--  margin: 0;-->
<!--  padding: 0;-->
<!--}-->

<!--dt {-->
<!--  font-weight: 700;-->
<!--}-->

<!--dd,-->
<!--dt {-->
<!--  line-height: 1.42857143;-->
<!--}-->

<!--.mt6 {-->
<!--  margin-top: 6px;-->
<!--}-->

<!--.em09 {-->
<!--  font-size: 0.9em;-->
<!--}-->

<!--.muted-2-color {-->
<!--  color: var(&#45;&#45;muted-2-color);-->
<!--}-->

<!--.c-red {-->
<!--  &#45;&#45;this-color: #ff5473;-->
<!--  &#45;&#45;this-bg: rgba(255, 84, 115, 0.1);-->
<!--}-->

<!--.c-yellow-2 {-->
<!--  color: var(&#45;&#45;this-color);-->
<!--}-->

<!--.em3x {-->
<!--  font-size: 3em;-->
<!--}-->

<!--b,-->
<!--strong {-->
<!--  font-weight: 700;-->
<!--}-->

<!--.pay-box .pay-mark {-->
<!--  font-size: 12px;-->
<!--  margin-right: 3px;-->
<!--}-->

<!--.but-average.inline {-->
<!--  display: inline-flex;-->
<!--}-->

<!--.but-average {-->
<!--  display: flex;-->
<!--  border-radius: 4px;-->
<!--  overflow: hidden;-->
<!--}-->

<!--.pay-box .vip-price {-->
<!--  border: none;-->
<!--  font-size: 12px;-->
<!--  padding: 4px 12px;-->
<!--  flex: content;-->
<!--}-->

<!--.but-average > .but {-->
<!--  margin: 0 !important;-->
<!--  border-radius: 0;-->
<!--  width: 100%;-->
<!--}-->

<!--.badg,-->
<!--.but {-->
<!--  border-radius: 4px;-->
<!--  display: inline-block;-->
<!--  border-radius: 4px;-->
<!--  transition: 0.15s;-->
<!--  border: 1px solid var(&#45;&#45;this-border);-->
<!--  vertical-align: middle;-->
<!--  padding: 0.3em 0.6em;-->
<!--  text-align: center;-->
<!--  font-weight: 400;-->
<!--  box-shadow: var(&#45;&#45;this-shadow);-->
<!--  background: var(&#45;&#45;this-bg);-->
<!--  color: var(&#45;&#45;this-color);-->
<!--  &#45;&#45;main-color: var(&#45;&#45;this-color);-->
<!--  &#45;&#45;this-bg: rgba(136, 136, 136, 0.1);-->
<!--  &#45;&#45;this-border: transparent;-->
<!--  &#45;&#45;this-shadow: none;-->
<!--  &#45;&#45;this-color: #888;-->
<!--  text-shadow: 0 0 0;-->
<!--  line-height: 1.44;-->
<!--}-->

<!--svg:not(:root) {-->
<!--  overflow: hidden;-->
<!--}-->

<!--.mr3 {-->
<!--  margin-right: 3px;-->
<!--}-->

<!--svg {-->
<!--  width: 1em;-->
<!--  height: 1em;-->
<!--  vertical-align: -0.15em;-->
<!--  fill: currentColor;-->
<!--  overflow: hidden;-->
<!--}-->

<!--.vip-price-text {-->
<!--  display: inline-block;-->
<!--}-->

<!--.ml3 {-->
<!--  margin-left: 3px;-->
<!--}-->

<!--.em12 {-->
<!--  font-size: 1.2em;-->
<!--}-->

<!--.pay-box .vip-price {-->
<!--  border: none;-->
<!--  font-size: 12px;-->
<!--  padding: 4px 12px;-->
<!--  flex: content;-->
<!--}-->

<!--.but-average > .but {-->
<!--  margin: 0 !important;-->
<!--  border-radius: 0;-->
<!--  width: 100%;-->
<!--}-->

<!--[href],-->
<!--a.but,-->
<!--botton {-->
<!--  cursor: pointer;-->
<!--}-->

<!--.mt10 {-->
<!--  margin-top: 10px;-->
<!--}-->

<!--.text-right {-->
<!--  text-align: right;-->
<!--}-->

<!--.c-yellow-2 {-->
<!--  &#45;&#45;this-color: #c58516;-->
<!--  &#45;&#45;this-bg: rgba(179, 103, 8, 0.1);-->
<!--}-->

<!--.pay-box .pay-extra-hide {-->
<!--  font-size: 13px;-->
<!--  background: var(&#45;&#45;muted-border-color);-->
<!--  display: block;-->
<!--  padding: 6px 10px;-->
<!--  line-height: 1.4;-->
<!--  color: var(&#45;&#45;muted-color);-->
<!--  border-radius: 4px;-->
<!--}-->

<!--.img-badge {-->
<!--  position: absolute;-->
<!--  top: 0.6em;-->
<!--  right: 0;-->
<!--  border-radius: 50px 0 0 50px;-->
<!--  text-shadow: none;-->
<!--  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);-->
<!--  z-index: 1;-->
<!--  padding: 0.2em 0.6em;-->
<!--}-->

<!--.jb-blue,-->
<!--.order-type-7 .pay-tag {-->
<!--  &#45;&#45;this-bg: linear-gradient(135deg, #59c3fb 10%, #268df7 100%);-->
<!--}-->

<!--.jb-blue {-->
<!--  color: var(&#45;&#45;this-color);-->
<!--  background: var(&#45;&#45;this-bg);-->
<!--  &#45;&#45;this-color: #fff;-->
<!--}-->
<!--</style>-->
