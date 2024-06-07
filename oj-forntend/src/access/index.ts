import router from "../router";
import store from "../store/store.ts"
import AccessEnum from "./accessEnum.ts";
import checkAccess from "./checkAccess.ts";
router.beforeEach(async (to,from,next)=>{
    console.log("login info",store.state.user.loginUser);
    const loginUser=store.state.user.loginUser;
    //console.log(loginUser);
    if(!loginUser||!loginUser.userRole){
        await store.dispatch("user/getLoginUser");
    }
    const needAccess=(to.meta?.access as string)??AccessEnum.NOT_LOGGED;
    if(needAccess!==AccessEnum.NOT_LOGGED){
        if(!loginUser||!loginUser.userRole||!loginUser.userName){
            next(`/user/login`);
            return;
        }
        if(!checkAccess( loginUser,needAccess)){
            // not auth
            next(`/noAuth`);
            return;
        }
    }
    next();

})
