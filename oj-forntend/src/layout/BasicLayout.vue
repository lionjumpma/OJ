<script setup lang="ts">

import Header from "../components/Header.vue";
</script>

<template>
  <a-layout style="height: 100vh;width:100vw;" >
    <a-layout-header id="header" >
      <Header/>
    </a-layout-header>
    <a-layout-content id="content">
      <router-view />
    </a-layout-content>
    <a-layout-footer id="footer">Footer</a-layout-footer>
  </a-layout>
</template>

<style scoped>
#header {
  height:10vh;
  background: #bfbfbf;
}
#content {

  background: #e6e6e6;
}
#footer {

  background: #bfbfbf;
}
</style>
