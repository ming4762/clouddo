/**
 * 认证类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var auth;
            (function (auth) {
                var AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
                var Auth = /** @class */ (function () {
                    function Auth() {
                    }
                    /**
                     * 进行权限认证
                     * @param domIds 要认证的domId集合
                     */
                    Auth.prototype.authPermission = function () {
                        var domIds = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            domIds[_i] = arguments[_i];
                        }
                        var authObject = this;
                        //获取用户拥有的所有权限并转为数据
                        if (domIds.length > 0) {
                            //遍历dom
                            for (var _a = 0, domIds_1 = domIds; _a < domIds_1.length; _a++) {
                                var domId = domIds_1[_a];
                                //验证dom权限
                                authPermission($("#" + domId));
                            }
                        }
                        //使用递归调用按钮显示
                        function authPermission(dom) {
                            //判断dom是否包含权限标签
                            var permission = dom.attr(Auth.ATTRIBUTE_KEY);
                            if (permission) {
                                if (!authObject.checkPermission(permission)) {
                                    //如果权限验证失败，隐藏该标签
                                    dom.hide();
                                }
                            }
                            else if (dom.children().length > 0) {
                                //如果有下级，则遍历下级
                                for (var _i = 0, _a = dom.children(); _i < _a.length; _i++) {
                                    var child = _a[_i];
                                    //递归调用
                                    authPermission($(child));
                                }
                            }
                        }
                    };
                    /**
                     * 验证是否有相应权限
                     * @param {string} permissionStr
                     * @returns {boolean}
                     */
                    Auth.prototype.checkPermission = function (permissionStr) {
                        if (permissionStr == null || permissionStr.trim() == "") {
                            return true;
                        }
                        //解析权限，权限格式XXXX or XXXX, XXXX
                        var result = true;
                        for (var _i = 0, _a = permissionStr.split(","); _i < _a.length; _i++) {
                            var permission = _a[_i];
                            //获取分隔后的权限
                            permission = permission.trim();
                            //判断权限是否包含or
                            if (permission.indexOf("or") != -1) {
                                var result1 = false;
                                for (var _b = 0, _c = permission.split("or"); _b < _c.length; _b++) {
                                    var permissionOr = _c[_b];
                                    //判断是否含有权限字符串
                                    permissionOr = permissionOr.trim();
                                    if (Auth.userPermissionList.indexOf(permissionOr) != -1) {
                                        result1 = true;
                                        break;
                                    }
                                }
                                result = result1;
                            }
                            else {
                                result = Auth.userPermissionList.indexOf(permission) != -1;
                            }
                            //如果一个权限没有验证通过，其他权限无需验证
                            if (!result) {
                                break;
                            }
                        }
                        return result;
                    };
                    /**
                     * 设置权限的dom属性
                     * @type {string}
                     */
                    Auth.ATTRIBUTE_KEY = "cloud-hasPermission";
                    Auth.userPermissionStr = AuthRestUtil.getPermissions();
                    Auth.userPermissionList = !Auth.userPermissionStr ? [] : Auth.userPermissionStr.split(",");
                    return Auth;
                }());
                auth.Auth = Auth;
            })(auth = ui.auth || (ui.auth = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
