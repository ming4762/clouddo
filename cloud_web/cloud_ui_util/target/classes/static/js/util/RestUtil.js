///<reference path="StorageUtil.ts"/>
/**
 * rest工具类
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var util;
            (function (util) {
                var StorageUtil = com.clouddo.ui.util.StorageUtil;
                var RestUtil = /** @class */ (function () {
                    function RestUtil() {
                    }
                    /**
                     * 获取后台服务地址
                     * @returns {string}
                     */
                    RestUtil.getBackgroundURL = function () {
                        return localStorage.getItem(RestUtil.BACKGROUND_URL);
                    };
                    /**
                     * 设置后台服务地址
                     * @param {string} backgroundURL
                     */
                    RestUtil.setBackgroundURL = function (backgroundURL) {
                        localStorage.setItem(RestUtil.BACKGROUND_URL, backgroundURL);
                    };
                    /**
                     * 获取图片显示地址
                     * @returns {string}
                     */
                    RestUtil.getShowImageUrl = function (id, width, height) {
                        var url = this.getBackgroundURL() + "file/image/" + id;
                        //拼接token
                        url += "?";
                        //拼接widht height
                        if (width) {
                            url += "&width=" + width;
                        }
                        if (height) {
                            url += "&height=" + height;
                        }
                        return url;
                    };
                    /**
                     * 加载本地化配置信息
                     */
                    RestUtil.loadLocalConfig = function (callback) {
                        var _this = this;
                        this.postAjax(this.LOCAL_CONFIG_URL, null, function (result) {
                            StorageUtil.setToSession(_this.LOCAL_CONFIG_KEY, result);
                            // 执行回调函数
                            if (callback) {
                                callback();
                            }
                        }, function (result) {
                            _this.throwError('获取本地配置信息失败', result);
                        });
                    };
                    /**
                     * 获取本地化配置信息
                     * @returns {any}
                     */
                    RestUtil.getLocalConfig = function () {
                        return StorageUtil.get(this.LOCAL_CONFIG_KEY);
                    };
                    /**
                     * 通过key获取本地化配置
                     * @param {string} key
                     * @returns {any}
                     */
                    RestUtil.getLocalConfigByKey = function (key) {
                        return this.getLocalConfig()[key];
                    };
                    /**
                     * 抛出错误弹窗
                     * @param message
                     */
                    RestUtil.throwError = function (message, error) {
                        console.log(error);
                        layer.alert(message, {
                            icon: 2,
                            title: '错误'
                        });
                    };
                    /**
                     * 文件上传
                     * @param {Object} parameterSet
                     * @param {Function} success
                     * @param {Function} error
                     * @param successCallbackParameters
                     */
                    RestUtil.upload = function (file, parameterSet, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 4; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 4] = arguments[_i];
                        }
                        var index = this.loading();
                        var data = new FormData();
                        data.append("file", file);
                        //添加参数
                        for (var key in parameterSet) {
                            data.append(key, parameterSet[key]);
                        }
                        $.ajax({
                            type: "post",
                            url: RestUtil.getBackgroundURL() + "file/file/upload",
                            dataType: "json",
                            //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
                            // contentType: "multipart/form-data; charset=UTF-8",
                            contentType: false,
                            //取消帮我们格式化数据，是什么就是什么、
                            processData: false,
                            data: data,
                            success: function (data) {
                                RestUtil.loaded(index);
                                RestUtil.defalutSuccessFunction.apply(RestUtil, [data, success, error].concat(successCallbackParameters));
                            },
                            error: function (data) {
                                RestUtil.loaded(index);
                                RestUtil.defalutErrorFunction(data, error);
                            },
                            beforeSend: function (request) {
                                request.setRequestHeader(AuthRestUtil.TOKEN_KEY, AuthRestUtil.getToken());
                            }
                        });
                    };
                    /**
                     * 发送ajax请求，异步
                     * @param {string} url 请求URL
                     * @param {Object} parameterSet 请求参数
                     * @param {Function} success 请求成功回调函数
                     * @param {Function} error 请求失败回调函数
                     * @param successCallbackParameters 请求成功参数（可变参数）
                     */
                    RestUtil.postAjax = function (url, parameterSet, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 4; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 4] = arguments[_i];
                        }
                        RestUtil.postAjaxBase.apply(RestUtil, [false, true, url, parameterSet, success, error].concat(successCallbackParameters));
                    };
                    /**
                     * 发送ajax请求，同步
                     * @param {string} url 请求URL
                     * @param {Object} parameterSet 请求参数
                     * @param {Function} success 请求成功回调函数
                     * @param {Function} error 请求失败回调函数
                     * @param successCallbackParameters 请求成功参数（可变参数）
                     */
                    RestUtil.postAjaxAsync = function (url, parameterSet, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 4; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 4] = arguments[_i];
                        }
                        RestUtil.postAjaxBase.apply(RestUtil, [false, false, url, parameterSet, success, error].concat(successCallbackParameters));
                    };
                    /**
                     * 发送ajax请求，异步
                     * @param async ture同步  false异步
                     * @param {string} url 请求URL
                     * @param {Object} parameterSet 请求参数
                     * @param {Function} success 请求成功回调函数
                     * @param {Function} error 请求失败回调函数
                     * @param successCallbackParameters 请求成功参数（可变参数）
                     */
                    RestUtil.postAjaxBase = function (withLoading, async, url, parameterSet, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 6; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 6] = arguments[_i];
                        }
                        var index;
                        if (withLoading) {
                            index = this.loading();
                        }
                        $.ajax({
                            type: "post",
                            url: RestUtil.getBackgroundURL() + url,
                            dataType: "json",
                            async: async,
                            contentType: 'application/json; charset=UTF-8',
                            data: JSON.stringify(parameterSet || {}),
                            success: function (data) {
                                if (withLoading) {
                                    RestUtil.loaded(index);
                                }
                                RestUtil.defalutSuccessFunction.apply(RestUtil, [data, success, error].concat(successCallbackParameters));
                            },
                            error: function (data) {
                                if (withLoading) {
                                    RestUtil.loaded(index);
                                }
                                RestUtil.defalutErrorFunction(data, error);
                            },
                            beforeSend: function (request) {
                                request.setRequestHeader(AuthRestUtil.TOKEN_KEY, AuthRestUtil.getToken());
                            }
                        });
                    };
                    /**
                     * 加载中
                     * @returns {number}
                     */
                    RestUtil.loading = function () {
                        return layer.load(2, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    };
                    /**
                     * 加载完毕
                     */
                    RestUtil.loaded = function (index) {
                        layer.close(index);
                    };
                    RestUtil.defalutSuccessFunction = function (data, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 3; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 3] = arguments[_i];
                        }
                        if (data.code == 200) {
                            if (success != null) {
                                //执行回调函数
                                success.apply(void 0, [data.data].concat(successCallbackParameters));
                            }
                            else {
                                //没有请求成功回调，则打印请求数据
                                console.log(data);
                            }
                        }
                        else {
                            RestUtil.defalutErrorFunction(data, error);
                        }
                    };
                    RestUtil.defalutErrorFunction = function (data, error) {
                        if (error != null) {
                            error(data);
                        }
                        else {
                            console.log(data);
                            layer.alert('数据获取异常！', { icon: 0 });
                        }
                    };
                    RestUtil.BACKGROUND_URL = "backgroundURL";
                    /**
                     * 存储本地化配置信息的key
                     * @type {string}
                     */
                    RestUtil.LOCAL_CONFIG_KEY = "cloud_local_config";
                    /**
                     * 本地化配置信息请求地址
                     * @type {string}
                     */
                    RestUtil.LOCAL_CONFIG_URL = 'system/public/local';
                    return RestUtil;
                }());
                util.RestUtil = RestUtil;
                /**
                 * 带有认证功能的rest工具类
                 */
                var AuthRestUtil = /** @class */ (function (_super) {
                    __extends(AuthRestUtil, _super);
                    function AuthRestUtil() {
                        return _super !== null && _super.apply(this, arguments) || this;
                    }
                    /**
                     * 获取权限信息
                     * @returns {string}
                     */
                    AuthRestUtil.getPermissions = function () {
                        return localStorage.getItem(this.PERMISSIONS_KEY);
                    };
                    /**
                     * 保存权限信息
                     * @param permissions
                     */
                    AuthRestUtil.setPermissions = function (permissions) {
                        localStorage.setItem(this.PERMISSIONS_KEY, permissions);
                    };
                    /**
                     * 获取token
                     * @returns {string}
                     */
                    AuthRestUtil.getToken = function () {
                        return localStorage.getItem(this.TOKEN_KEY);
                    };
                    /**
                     * 存储token
                     * @param {string} token token
                     */
                    AuthRestUtil.setToken = function (token) {
                        localStorage.setItem(this.TOKEN_KEY, token);
                    };
                    /**
                     * 获取图片显示地址带有认证信息
                     * @param {string} id
                     * @param {number} width
                     * @param {number} height
                     * @returns {string}
                     */
                    AuthRestUtil.getShowImageUrl = function (id, width, height) {
                        var url = _super.getShowImageUrl.call(this, id, width, height);
                        if (width || height) {
                            url += "&" + this.TOKEN_KEY + "=" + this.getToken();
                        }
                        else {
                            url += "?" + this.TOKEN_KEY + "=" + this.getToken();
                        }
                        return url;
                    };
                    /**
                     * 统一登录接口
                     * @param {string} username 用户名
                     * @param {string} password 密码
                     * @param {Function} success 登录成功接口
                     * @param {Function} error 登录失败函数
                     */
                    AuthRestUtil.login = function (username, password, success, error) {
                        var parameters = { username: username, password: password };
                        RestUtil.postAjax("auth/login", parameters, successFunction, error);
                        function successFunction(data) {
                            //存储token
                            AuthRestUtil.setToken(data.token);
                            AuthRestUtil.setPermissions(data[AuthRestUtil.PERMISSIONS_KEY]);
                            //存储权限
                            success(data);
                        }
                    };
                    /**
                     * 登出操作
                     */
                    AuthRestUtil.logout = function () {
                        //想后台发送清除请求
                        var url = "auth/logout";
                        AuthRestUtil.postAjax(url, null, success, error);
                        function success(result) {
                            AuthRestUtil.goToIndex();
                            AuthRestUtil.setToken(null);
                            AuthRestUtil.setPermissions(null);
                        }
                        function error(result) {
                            console.log(result);
                            layer.alert("登出时发生错误！", { icon: 0 });
                        }
                    };
                    /**
                     * 发送ajax请求
                     * @param {string} url 请求地址
                     * @param {Object} parameterSet 请求参数
                     * @param {Function} success 请求成功回调
                     * @param {Function} error 请求失败回调
                     * @param successCallbackParameters 其他参数
                     */
                    AuthRestUtil.postAjax = function (url, parameterSet, success, error) {
                        var successCallbackParameters = [];
                        for (var _i = 4; _i < arguments.length; _i++) {
                            successCallbackParameters[_i - 4] = arguments[_i];
                        }
                        _super.postAjax.apply(this, [url, parameterSet, success, tokenFailError].concat(successCallbackParameters));
                        function tokenFailError(result) {
                            //如果失败原因是token认证失败导致的，跳转到登录页面
                            if (result["status"] == AuthRestUtil.EX_TOKEN_ERROR_CODE) {
                                AuthRestUtil.goToIndex();
                            }
                            else {
                                RestUtil.defalutErrorFunction(result, error);
                            }
                        }
                    };
                    /**
                     * 跳转到主页
                     */
                    AuthRestUtil.goToIndex = function () {
                        window.location.href = contextpath + AuthRestUtil.LOGIN_HREF;
                    };
                    //存储token的key
                    AuthRestUtil.TOKEN_KEY = "Authorization";
                    //存储权限的key
                    AuthRestUtil.PERMISSIONS_KEY = "userPermissions";
                    //token认证失败的状态码
                    AuthRestUtil.EX_TOKEN_ERROR_CODE = 40301;
                    AuthRestUtil.LOGIN_HREF = "web/public/login";
                    return AuthRestUtil;
                }(RestUtil));
                util.AuthRestUtil = AuthRestUtil;
                /**
                 * 存储器
                 */
                var Storage = /** @class */ (function () {
                    function Storage() {
                    }
                    Storage.getAuthStorage = function () {
                        return this.authStorage;
                    };
                    //普通存储器
                    Storage.storage = localStorage;
                    //安全存储器
                    Storage.authStorage = localStorage;
                    return Storage;
                }());
                util.Storage = Storage;
            })(util = ui.util || (ui.util = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
