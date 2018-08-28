/**
 * vue初始化类
 */
namespace com.clouddo.ui.vue {

    declare let Vue: any;
    declare let $
    export class VueInit {

        // vue绑定的element
        private ele: string
        //自定义方法
        private customMethods : {[index:string]: Function} = {};
        //自定义数据
        private customData : {[index:string]: any} = {};
        // vue挂在后事件
        private vueMountedFunction: Function

        /**
         * 构造函数
         * @param {string} ele vue绑定的element
         */
        constructor(ele: string) {
            this.ele = ele
        }

        /**
         * 初始化方法
         * @returns {any}
         */
        public init(): any {
            let $this = this
            let vue = new Vue({
                el: this.ele,
                data: $.extend({}, {

                }, this.customData),
                methods: $.extend({},{

                }, this.customMethods),
                // 生命周期钩子：挂在后状态
                mounted: function () {
                    if ($this.vueMountedFunction) {
                        $this.vueMountedFunction()
                    }
                }
            })
            return vue
        }

        /**
         * 设置vue自定义方法
         * @param {{[p: string]: Function}} customMethods
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setCustomMethods(customMethods : {[index:string]: Function}) {
            this.customMethods = customMethods;
            return this;
        }

        /**
         * 设置vue自定义数据
         * @param {{[p: string]: any}} customData
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setCustomData(customData : {[index:string]: any}) {
            this.customData = customData;
            return this;
        }

        /**
         * 设置vue自定义方法
         * @param {{[p: string]: Function}} customMethods
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public addCustomMethods(customMethods : {[index:string]: Function}) {
            this.customMethods = $.extend({}, this.customMethods, customMethods)
            return this;
        }

        /**
         * 设置vue自定义数据
         * @param {{[p: string]: any}} customData
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public addCustomData(customData : {[index:string]: any}) {
            this.customData = $.extend({}, this.customData, customData)
            return this;
        }

        /**
         * vue挂在后事件
         * @param {Function} vueMountedFunction
         */
        public vueMounted (vueMountedFunction: Function) {
            this.vueMountedFunction = vueMountedFunction
            return this
        }
    }
}