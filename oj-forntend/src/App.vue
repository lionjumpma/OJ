<script setup lang="ts">
import BasicLayout from "./layout/BasicLayout.vue";
import {onMounted} from "vue";
import {useRoute} from "vue-router";
const route=useRoute();
const doInit = () => {
  console.log("init");
};
onMounted(() => {
  doInit();
});
</script>

<template>
  <div id="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view></router-view>
    </template>
    <template v-else>
      <BasicLayout></BasicLayout>
    </template>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
