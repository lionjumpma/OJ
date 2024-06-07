<script setup lang="ts">

import {routes} from "../router/routes.ts";
import {computed} from "vue";
import checkAccess from "../access/checkAccess.ts";
import {useStore} from "vuex";
const store=useStore();
const loginUser=store.state.user.loginUser;
const visibleRouter=computed(()=>{
  return routes.filter((item,index)=>{
    if(item.meta?.hideInMenu){
      return false;
    }
    if(!checkAccess(loginUser,item?.meta?.access as string)){
      return false;
    }
    return true;
  })
})
//console.log(loginUser);
</script>

<template>
  <div class="grid grid-cols-12 h-full ">
    <div class="col-span-10 menu">
      <a-menu class=" h-full" mode="horizontal" :default-selected-keys="['1']" >
        <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px' }" disabled>
          <div class="title-bar">
            <img class="logo" src="../assets/oj-logo.svg" />
            <div class="title">ma OJ</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRouter" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </div>

    <div class="col-span-2 m-auto">
      <div class="">
        {{loginUser?.userName ?? "未登录" }}
      </div>
    </div>SS


  </div>


</template>

<style scoped>
.menu{
  box-sizing: border-box;
  width: 100%;
  background-color: var(--color-neutral-2);
}

</style>
