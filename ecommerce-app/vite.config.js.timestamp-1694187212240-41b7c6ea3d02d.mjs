// vite.config.js
import { defineConfig } from "file:///C:/Users/dbori/Desktop/ecommerce-app/node_modules/vite/dist/node/index.js";
import react from "file:///C:/Users/dbori/Desktop/ecommerce-app/node_modules/@vitejs/plugin-react/dist/index.mjs";
import eslint from "file:///C:/Users/dbori/Desktop/ecommerce-app/node_modules/vite-plugin-eslint/dist/index.mjs";
var vite_config_default = defineConfig({
  plugins: [react(), eslint()],
});
export { vite_config_default as default };
