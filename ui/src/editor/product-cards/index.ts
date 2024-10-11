// import {
//   type Editor,
//   mergeAttributes,
//   Node,
//   VueNodeViewRenderer,
//   ToolboxItem,
// } from "@halo-dev/richtext-editor";
// import ResourceCardView from "./ResourceCardView.vue";
// import { markRaw } from "vue";
// import RiArrowRightSLine from "~icons/ri/arrow-right-s-line";
//
// const temporaryDocument = document.implementation.createHTMLDocument();
// export const ExtensionResourceCard = Node.create({
//   name: "resource-card",
//   inline: false,
//   content: "",
//   marks: "",
//   group: "block",
//   code: true,
//   atom: true,
//   defining: true,
//   addAttributes() {
//     return {
//       type: {
//         default: "",
//       },
//       resourceContent: {
//         default: "测试",
//       },
//     };
//   },
//   parseHTML() {
//     return [
//       {
//         tag: "resource-card",
//       },
//     ];
//   },
//   renderHTML({ node, HTMLAttributes }) {
//     const container = temporaryDocument.createElement("div");
//     container.innerHTML = node.attrs.resourceContent;
//     return ["resource-card", mergeAttributes(HTMLAttributes), container];
//   },
//   addNodeView() {
//     return VueNodeViewRenderer(ResourceCardView);
//   },
//   addOptions() {
//     return {
//       ...this.parent?.(),
//       getToolboxItems({ editor }: { editor: Editor }) {
//         return [
//           {
//             priority: 100,
//             component: markRaw(ToolboxItem),
//             props: {
//               editor,
//               icon: markRaw(RiArrowRightSLine),
//               title: "资源卡片",
//               action: () => {
//                 editor
//                   .chain()
//                   .focus()
//                   .insertContent([{ type: "resource-card" }])
//                   .run();
//               },
//             },
//           },
//         ];
//       },
//     };
//   },
// });
