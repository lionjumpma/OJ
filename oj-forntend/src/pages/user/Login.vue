<template>
  <div class="login-container">
    <a-form @submit="handleSubmit" layout="vertical" class="login-form" :model="loginForm">
      <a-typography>
        <h1 class="title">登录</h1>
      </a-typography>
      <a-form-item label="用户名" name="userAccount" :rules="[{ required: true, message: '请输入用户名' }]">
        <a-input v-model="loginForm.userAccount" placeholder="用户名" />
      </a-form-item>
      <a-form-item label="密码" name="userPassword" :rules="[{ required: true, message: '请输入密码' }]">
        <a-input v-model="loginForm.userPassword" type="password" placeholder="密码" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" htmlType="submit" long>登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import {UserControllerService} from "../../api/services/UserControllerService.ts";
import message from "@arco-design/web-vue/es/message"
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {reactive} from "vue";
export default {
  setup() {
    // 在 setup 函数内使用 useRouter 和 useStore
    const router = useRouter();
    const store = useStore();
    let loginForm=reactive({
      userAccount: '',
      userPassword: '',
    });

    // 返回一个对象，使得 handleSubmit 方法可以访问 router 和 store
    return {
      loginForm,
      async handleSubmit() {
        const res = await UserControllerService.userLoginUsingPost(loginForm);
        if (res.code === 0) {
          console.log("login success");
          await store.dispatch("user/getLoginUser");
          router.push({
            path: "/",
            replace: true,
          });
        } else {
          message.error("login error" + res.message);
        }
      },
    };
  },
};
</script>

<style>
.login-container {
  max-width: 300px;
  margin: 50px auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-form .title {
  text-align: center;
  margin-bottom: 24px;
}
</style>
