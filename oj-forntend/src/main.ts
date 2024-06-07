import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import ArcoVue from "@arco-design/web-vue";
import '@arco-design/web-vue/dist/arco.css';
import router from './router'
import store from './store/store.ts'
import 'tailwindcss/tailwind.css'
import  "./access"
const app=createApp(App)
app.use(ArcoVue)
app.use(router)
app.use(store)
app.mount('#app')
