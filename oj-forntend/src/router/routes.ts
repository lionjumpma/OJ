import {RouteRecordRaw} from "vue-router";
import noAuth from "../pages/NoAuth.vue";
import Home from "../pages/Home.vue";

export const routes: Array<RouteRecordRaw> = [
    {
        path: '/user',
        name: '用户',
        component: () => import('../layout/UserLayout.vue'),
        children:[
            {
                path:"login",
                name:"用户登录",
                component:()=>import('../pages/user/Login.vue')
            }
        ],

        meta:{
            hideInMenu:true
        }
    },
    {
      path:"/" ,
      name:"Home",
      component:  Home
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../components/HelloWorld.vue'),

    },
    {
        path:'/noAuth',
        name: '无权限',
        component: noAuth
    },
    // {
    //     path:'/question',
    //     name: "浏览题目",
    //     component:
    // }


    ]
