/**
 * vue初始化类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var vue;
            (function (vue_1) {
                var VueInit = /** @class */ (function () {
                    /**
                     * 构造函数
                     * @param {string} ele vue绑定的element
                     */
                    function VueInit(ele) {
                        //自定义方法
                        this.customMethods = {};
                        //自定义数据
                        this.customData = {};
                        this.customWatch = {};
                        this.ele = ele;
                    }
                    /**
                     * 初始化方法
                     * @returns {any}
                     */
                    VueInit.prototype.init = function () {
                        var $this = this;
                        var vue = new Vue({
                            el: this.ele,
                            data: $.extend({}, {}, this.customData),
                            methods: $.extend({}, {}, this.customMethods),
                            // 生命周期钩子：挂在后状态
                            mounted: function () {
                                if ($this.vueMountedFunction) {
                                    $this.vueMountedFunction();
                                }
                            },
                            watch: this.customWatch
                        });
                        return vue;
                    };
                    /**
                     * 设置vue自定义方法
                     * @param {{[p: string]: Function}} customMethods
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    VueInit.prototype.setCustomMethods = function (customMethods) {
                        this.customMethods = customMethods;
                        return this;
                    };
                    /**
                     * 设置vue自定义数据
                     * @param {{[p: string]: any}} customData
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    VueInit.prototype.setCustomData = function (customData) {
                        this.customData = customData;
                        return this;
                    };
                    /**
                     * 设置vue自定义方法
                     * @param {{[p: string]: Function}} customMethods
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    VueInit.prototype.addCustomMethods = function (customMethods) {
                        this.customMethods = $.extend({}, this.customMethods, customMethods);
                        return this;
                    };
                    /**
                     * 设置vue自定义数据
                     * @param {{[p: string]: any}} customData
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    VueInit.prototype.addCustomData = function (customData) {
                        this.customData = $.extend({}, this.customData, customData);
                        return this;
                    };
                    /**
                     * vue挂在后事件
                     * @param {Function} vueMountedFunction
                     */
                    VueInit.prototype.vueMounted = function (vueMountedFunction) {
                        this.vueMountedFunction = vueMountedFunction;
                        return this;
                    };
                    /**
                     * 设置自定义观察者
                     * @param {{[p: string]: Function}} customWatch
                     * @returns {this}
                     */
                    VueInit.prototype.setCustomWatch = function (customWatch) {
                        this.customWatch = customWatch;
                        return this;
                    };
                    return VueInit;
                }());
                vue_1.VueInit = VueInit;
            })(vue = ui.vue || (ui.vue = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
