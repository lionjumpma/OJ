import AccessEnum from "./accessEnum.ts";

/**
 *
 * @param loginUser
 * @param needAccess
 */
const checkAccess = (loginUser:any,needAccess=AccessEnum.NOT_LOGGED) => {
    const access=loginUser?.userRole??AccessEnum.NOT_LOGGED;
    if(needAccess===AccessEnum.NOT_LOGGED){
        return true;
    }
    if(needAccess===AccessEnum.LOGGED){
        return access!==AccessEnum.NOT_LOGGED;
    }
    if(needAccess===AccessEnum.ADMIN){
        return access===AccessEnum.ADMIN;
    }
    return false;

}
export default checkAccess
