/**
 * rest工具类
 */

namespace com.clouddo.ui.util {
    declare let $ : any;
    declare let layer;
    export class RestUtil {

        //存储token的key
        public static TOKEN_KEY : string = "Authorization";

        //存储权限的key
        public static PERMISSIONS_KEY : string = "userPermissions";


        public static BACKGROUND_URL : string = "backgroundURL";

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
            localStorage.setItem("RestUtil.BACKGROUND_URL", backgroundURL);
        }

        /**
         * 获取图片显示地址
         * @returns {string}
         */
        public static getShowImageUrl(id: string, width ? : number, height ? : number) : string {
            let url : string = this.getBackgroundURL() + "file/image/" + id;
            //拼接token
            url += "?" + this.TOKEN_KEY + "=" + this.getToken();
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
                RestUtil.setToken(data.token);
                RestUtil.setPermissions(data[RestUtil.PERMISSIONS_KEY]);
                //存储权限
                success(data);
            }
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
                    request.setRequestHeader("Authorization", RestUtil.getToken());
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
            RestUtil.postAjaxBase(true, url , parameterSet, success, error, ...successCallbackParameters);
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
            RestUtil.postAjaxBase(false, url , parameterSet, success, error, ...successCallbackParameters);
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
        public static postAjaxBase(async : boolean ,url : string, parameterSet : Object, success : Function, error : Function, ...successCallbackParameters : any[]) {

            let index = this.loading();
            $.ajax({
                type: "post",
                url : RestUtil.getBackgroundURL() + url,
                dataType:"json",
                async : async,
                contentType : 'application/json; charset=UTF-8',
                // data : JSON.stringify、(parameterSet || {}),
                data : JSON.stringify(parameterSet || {}),
                success : function (data) {
                    RestUtil.loaded(index);
                    RestUtil.defalutSuccessFunction(data, success, error, ...successCallbackParameters);
                },
                error : function (data) {
                    RestUtil.loaded(index);
                    RestUtil.defalutErrorFunction(data, error);
                },
                beforeSend : function (request) {
                    request.setRequestHeader("Authorization", RestUtil.getToken());
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

        private static defalutErrorFunction(data : any, error : Function) {
            if (error != null) {
                error(data);
            } else {
                console.log(data);
                layer.alert('数据获取异常！', {icon: 0});
            }
        }
    }
}