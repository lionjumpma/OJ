import accessEnum from "../access/accessEnum.ts";
import {UserControllerService} from "../api/services/UserControllerService.ts";

export default{
    namespaced:true,
    state:()=>({
        loginUser:{
            userName:"未登录",
            userRole: accessEnum.NOT_LOGGED
        }
    }),

    mutations:{
        updateUser(state,payload){
            state.loginUser=payload;
        }
    },
    actions:{
        async getLoginUser({commit,state},payload){
            console.log("getLoginUser",state.loginUser);
            const res=await UserControllerService.getLoginUserUsingGet();
            if(res.code===0){
                commit("updateUser",res.data);
            }else{
                commit("updateUser",{
                    ...state.loginUser,
                    userRole:accessEnum.NOT_LOGGED,
                })
            }

        }
    }

}
