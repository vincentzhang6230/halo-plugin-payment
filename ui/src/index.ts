import { definePlugin } from "@halo-dev/console-shared";
import HomeView from "./views/HomeView.vue";
// import { ExtensionResourceCard } from "@/editor/product-cards";
import { IconPlug } from "@halo-dev/components";
import { markRaw } from "vue";

export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "Root",
      route: {
        path: "/todos",
        name: "Todos",
        component: HomeView,
        meta: {
          title: "todo页面",
          searchable: true,
          menu: {
            name: "Todo List",
            group: "工具",
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
  ],
  // extensionPoints: {
  //   "default:editor:extension:create": () => {
  //     return [ExtensionResourceCard];
  //   },
  // },
});
