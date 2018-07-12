/**
 * rest工具类
 */
declare let $;
declare let layer;

namespace com.clouddo.ui.util {
    export class RestUtil {

        /**
         * 获取后台服务地址
         * @returns {string}
         */
        public static getBackgroundURL() : string {
            return localStorage.getItem("backgroundURL");
        }

        /**
         * 获取token
         * @returns {string}
         */
        public static getToken() : string {
            return localStorage.getItem("Authorization");
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
            $.ajax({
                type: "post",
                url : RestUtil.getBackgroundURL() + url,
                dataType:"json",
                async : async,
                contentType : 'application/json; charset=UTF-8',
                // data : JSON.stringify、(parameterSet || {}),
                data : JSON.stringify(parameterSet || {}),
                success : function (data) {
                    RestUtil.defalutSuccessFunction(data, success, error, ...successCallbackParameters);
                },
                error : function (data) {
                    RestUtil.defalutErrorFunction(data, error);
                },
                beforeSend : function (request) {
                    request.setRequestHeader("Authorization", RestUtil.getToken());
                }
            });
        }


        private static defalutSuccessFunction(data : any, success : Function, error : Function, ...successCallbackParameters) {
            if (data.code == "SUCCESS") {
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