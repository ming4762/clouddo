/**
 * rest工具类
 */

namespace com.clouddo.ui.util {
    declare let $ : any;
    declare let layer;
    //项目路径
    declare let contextpath;


    export class RestUtil{

        public static BACKGROUND_URL : string = "backgroundURL"
        /**
         * 存储本地化配置信息的key
         * @type {string}
         */
        public static LOCAL_CONFIG_KEY: string = "cloud_local_config"
        /**
         * 本地化配置信息请求地址
         * @type {string}
         */
        public static LOCAL_CONFIG_URL: string = 'system/public/local'

        /**
         * 获取后台服务地址
         * @returns {string}
         */
        public static getBackgroundURL() : string {
            return localStorage.getItem(RestUtil.BACKGROUND_URL);
        }

        /**
         * 设置后台服务地址
         * @param {string} backgroundURL
         */
        public static setBackgroundURL(backgroundURL: string) : void {
            localStorage.setItem(RestUtil.BACKGROUND_URL, backgroundURL);
        }

        /**
         * 获取图片显示地址
         * @returns {string}
         */
        public static getShowImageUrl(id: string, width ? : number, height ? : number) : string {
            let url : string = this.getBackgroundURL() + "file/image/" + id;
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
        }

        /**
         * 加载本地化配置信息
         */
        public static loadLocalConfig (callback?: Function): void {
            this.postAjax(this.LOCAL_CONFIG_URL, null, (result) => {
                StorageUtil.setToSession(this.LOCAL_CONFIG_KEY, result)
                // 执行回调函数
                if (callback) {
                    callback()
                }
            }, (result) => {
                this.throwError('获取本地配置信息失败', result)
            });
        }

        /**
         * 获取本地化配置信息
         * @returns {any}
         */
        public static getLocalConfig (): any {
            return StorageUtil.get(this.LOCAL_CONFIG_KEY)
        }

        /**
         * 抛出错误弹窗
         * @param message
         */
        public static throwError (message, error): void {
            console.log(error)
            layer.alert(message, {
                icon: 2,
                title: '错误'
            })
        }

        /**
         * 文件上传
         * @param {Object} parameterSet
         * @param {Function} success
         * @param {Function} error
         * @param successCallbackParameters
         */
        public static upload(file : any, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) : void {

            let index = this.loading();
            let data = new FormData();
            data.append("file", file);
            //添加参数
            for (let key in parameterSet) {
                data.append(key, parameterSet[key]);
            }

            $.ajax({
                type: "post",
                url : RestUtil.getBackgroundURL() + "file/file/upload",
                dataType:"json",
                //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
                // contentType: "multipart/form-data; charset=UTF-8",
                contentType: false,
                //取消帮我们格式化数据，是什么就是什么、
                processData: false,
                data : data,
                success : function (data) {
                    RestUtil.loaded(index);
                    RestUtil.defalutSuccessFunction(data, success, error, ...successCallbackParameters);
                },
                error : function (data) {
                    RestUtil.loaded(index);
                    RestUtil.defalutErrorFunction(data, error);
                },
                beforeSend : function (request) {
                    request.setRequestHeader(AuthRestUtil.TOKEN_KEY, AuthRestUtil.getToken());
                }
            });
        }

        /**
         * 发送ajax请求，异步
         * @param {string} url 请求URL
         * @param {Object} parameterSet 请求参数
         * @param {Function} success 请求成功回调函数
         * @param {Function} error 请求失败回调函数
         * @param successCallbackParameters 请求成功参数（可变参数）
         */
        public static postAjax(url : string, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) {
            RestUtil.postAjaxBase(false, true, url , parameterSet, success, error, ...successCallbackParameters);
        }
        /**
         * 发送ajax请求，同步
         * @param {string} url 请求URL
         * @param {Object} parameterSet 请求参数
         * @param {Function} success 请求成功回调函数
         * @param {Function} error 请求失败回调函数
         * @param successCallbackParameters 请求成功参数（可变参数）
         */
        public static postAjaxAsync(url : string, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) {
            RestUtil.postAjaxBase(false,false, url , parameterSet, success, error, ...successCallbackParameters);
        }


        /**
         * 发送ajax请求，异步
         * @param async ture同步  false异步
         * @param {string} url 请求URL
         * @param {Object} parameterSet 请求参数
         * @param {Function} success 请求成功回调函数
         * @param {Function} error 请求失败回调函数
         * @param successCallbackParameters 请求成功参数（可变参数）
         */
        public static postAjaxBase(withLoading: boolean, async : boolean ,url : string, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) {
            let index
            if (withLoading) {
                index = this.loading();
            }
            $.ajax({
                type: "post",
                url : RestUtil.getBackgroundURL() + url,
                dataType:"json",
                async : async,
                contentType : 'application/json; charset=UTF-8',
                data : JSON.stringify(parameterSet || {}),
                success : function (data) {
                    if (withLoading) {
                        RestUtil.loaded(index);
                    }
                    RestUtil.defalutSuccessFunction(data, success, error, ...successCallbackParameters);
                },
                error : function (data) {
                    if (withLoading) {
                        RestUtil.loaded(index);
                    }
                    RestUtil.defalutErrorFunction(data, error);
                },
                beforeSend : function (request) {
                    request.setRequestHeader(AuthRestUtil.TOKEN_KEY, AuthRestUtil.getToken());
                }
            });
        }

        /**
         * 加载中
         * @returns {number}
         */
        public static loading() : number {
            return layer.load(2, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
        }

        /**
         * 加载完毕
         */
        public static loaded(index : number) : void {
            layer.close(index);
        }

        private static defalutSuccessFunction(data : any, success : Function, error : Function, ...successCallbackParameters) {
            if (data.code == 200) {
                if (success != null) {
                    //执行回调函数
                    success(data.data,  ...successCallbackParameters);
                } else {
                    //没有请求成功回调，则打印请求数据
                    console.log(data);
                }
            } else {
                RestUtil.defalutErrorFunction(data, error);
            }
        }

        public static defalutErrorFunction(data : any, error : Function) {
            if (error != null) {
                error(data);
            } else {
                console.log(data);
                layer.alert('数据获取异常！', {icon: 0});
            }
        }
    }

    /**
     * 带有认证功能的rest工具类
     */
    export class AuthRestUtil extends RestUtil {
        //存储token的key
        public static TOKEN_KEY : string = "Authorization";

        //存储权限的key
        public static PERMISSIONS_KEY : string = "userPermissions";

        //token认证失败的状态码
        public static EX_TOKEN_ERROR_CODE : number = 40301;

        public static LOGIN_HREF = "web/public/login";


        /**
         * 获取权限信息
         * @returns {string}
         */
        public static getPermissions() : string {
            return localStorage.getItem(this.PERMISSIONS_KEY);
        }

        /**
         * 保存权限信息
         * @param permissions
         */
        public static setPermissions(permissions : any) : void {
            localStorage.setItem(this.PERMISSIONS_KEY, permissions);
        }

        /**
         * 获取token
         * @returns {string}
         */
        public static getToken() : string {
            return localStorage.getItem(this.TOKEN_KEY);
        }

        /**
         * 存储token
         * @param {string} token token
         */
        public static setToken(token : string) : void {
            localStorage.setItem(this.TOKEN_KEY, token);
        }


        /**
         * 获取图片显示地址带有认证信息
         * @param {string} id
         * @param {number} width
         * @param {number} height
         * @returns {string}
         */
        public static getShowImageUrl(id: string, width ? : number, height ? : number) : string {
            let url = super.getShowImageUrl(id, width, height);
            if (width || height) {
                url += "&" + this.TOKEN_KEY + "=" + this.getToken();
            } else {
                url += "?" + this.TOKEN_KEY + "=" + this.getToken();
            }
            return url;
        }


        /**
         * 统一登录接口
         * @param {string} username 用户名
         * @param {string} password 密码
         * @param {Function} success 登录成功接口
         * @param {Function} error 登录失败函数
         */
        public static login(username : string, password : string, success : Function, error : Function) : void {
            let parameters = {username: username, password : password};
            RestUtil.postAjax("auth/login", parameters, successFunction, error);

            function successFunction(data) {
                //存储token
                AuthRestUtil.setToken(data.token);
                AuthRestUtil.setPermissions(data[AuthRestUtil.PERMISSIONS_KEY]);
                //存储权限
                success(data);
            }
        }

        /**
         * 登出操作
         */
        public static logout() {
            //想后台发送清除请求
            let url : string = "auth/logout";
            AuthRestUtil.postAjax(url, null, success, error);

            function success(result) {

                AuthRestUtil.goToIndex();
                AuthRestUtil.setToken(null);
                AuthRestUtil.setPermissions(null);
            }

            function error(result) {
                console.log(result);
                layer.alert("登出时发生错误！", {icon : 0});
            }
        }

        /**
         * 发送ajax请求
         * @param {string} url 请求地址
         * @param {Object} parameterSet 请求参数
         * @param {Function} success 请求成功回调
         * @param {Function} error 请求失败回调
         * @param successCallbackParameters 其他参数
         */
        public static postAjax(url : string, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) {
            super.postAjax(url, parameterSet, success, tokenFailError, ...successCallbackParameters);


            function tokenFailError(result) {
                //如果失败原因是token认证失败导致的，跳转到登录页面
                if (result["status"] == AuthRestUtil.EX_TOKEN_ERROR_CODE) {
                    AuthRestUtil.goToIndex();
                } else {
                    RestUtil.defalutErrorFunction(result, error);
                }
            }
        }

        /**
         * 跳转到主页
         */
        public static goToIndex() : void {
            window.location.href = contextpath + AuthRestUtil.LOGIN_HREF;
        }
    }


    /**
     * 存储器
     */
    export class Storage {

        //普通存储器
        public static storage : any = localStorage;
        //安全存储器
        public static authStorage : any = localStorage;

        public static getAuthStorage() : any {
            return this.authStorage;
        }
    }

}