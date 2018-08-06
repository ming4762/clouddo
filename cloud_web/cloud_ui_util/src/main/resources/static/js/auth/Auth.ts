/**
 * 认证类
 */

namespace com.clouddo.ui.auth {

    import RestUtil = com.clouddo.ui.util.RestUtil;

    declare let $ : any;

    export class Auth {
        /**
         * 设置权限的dom属性
         * @type {string}
         */
        public static ATTRIBUTE_KEY : string = "cloud-hasPermission";

        private static userPermissionStr = RestUtil.getPermissions();
        private static userPermissionList = !Auth.userPermissionStr ? [] : Auth.userPermissionStr.split(",");

        /**
         * 进行权限认证
         * @param domIds 要认证的domId集合
         */
        public authPermission(... domIds) {
            let authObject = this;
            //获取用户拥有的所有权限并转为数据
            if(domIds.length > 0) {
                //遍历dom
                for(let domId of domIds) {
                    //验证dom权限
                    authPermission($("#" + domId));
                }
            }
            //使用递归调用按钮显示
            function authPermission(dom : any) {
                //判断dom是否包含权限标签
                var permission = dom.attr(Auth.ATTRIBUTE_KEY);
                if(permission) {
                    if (!authObject.checkPermission(permission)) {
                        //如果权限验证失败，隐藏该标签
                        dom.hide();
                    }
                } else if(dom.children().length > 0) {
                    //如果有下级，则遍历下级
                    for (let child of dom.children()) {
                        //递归调用
                        authPermission($(child));
                    }
                }
            }
        }

        /**
         * 验证是否有相应权限
         * @param {string} permissionStr
         * @returns {boolean}
         */
        public checkPermission(permissionStr : string) : boolean {
            if (permissionStr == null || permissionStr.trim() == "") {
                return true;
            }
            //解析权限，权限格式XXXX or XXXX, XXXX
            let result : boolean = true
            for (let permission of permissionStr.split(",")) {
                //获取分隔后的权限
                permission = permission.trim();
                //判断权限是否包含or
                if (permission.indexOf("or") != -1) {
                    let result1 = false;
                    for (let permissionOr of permission.split("or")) {
                        //判断是否含有权限字符串
                        permissionOr = permissionOr.trim();
                        if (Auth.userPermissionList.indexOf(permissionOr) != -1) {
                            result1 = true;
                            break;
                        }
                    }
                    result = result1;
                } else {
                    result = Auth.userPermissionList.indexOf(permission) != -1;
                }
                //如果一个权限没有验证通过，其他权限无需验证
                if (!result) {
                    break;
                }

            }
            return result;

        }
    }
}