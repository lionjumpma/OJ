
import { createStore } from 'vuex'
import user from "./user.ts";

// 创建一个新的 store 实例
const store = createStore({
    modules:{
      user
    },
})
export default store;
