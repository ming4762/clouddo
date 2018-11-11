var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var vue;
            (function (vue) {
                var BaseComponentImpl = /** @class */ (function () {
                    /**
                     * 构造函数，创造组件
                     * @param {string} name
                     */
                    function BaseComponentImpl(name, constructData) {
                        this.name = name;
                    }
                    /**
                     * 组件
                     * @returns {{[p: string]: any}}
                     */
                    BaseComponentImpl.prototype.components = function () {
                        return {};
                    };
                    /**
                     * 创建组件
                     */
                    BaseComponentImpl.prototype.create = function () {
                        Vue.component(this.name, this.init());
                    };
                    /**
                     * 组件模板
                     */
                    BaseComponentImpl.prototype.template = function () {
                        return null;
                    };
                    /**
                     * 组件数据
                     * @returns {Function}
                     */
                    BaseComponentImpl.prototype.data = function () {
                        return null;
                    };
                    /**
                     * 初始化函数
                     * @returns {{[p: string]: any}}
                     */
                    BaseComponentImpl.prototype.init = function () {
                        var initData = {
                            template: this.template(),
                            filters: this.filters(),
                            components: this.components(),
                            data: this.data,
                            props: this.props(),
                            methods: this.methods(),
                            watch: this.watch(),
                            computed: this.computed(),
                            beforeCreate: this.beforeCreate,
                            created: this.created,
                            beforeMount: this.beforeMount,
                            mounted: this.mounted,
                            beforeUpdate: this.beforeUpdate,
                            updated: this.updated,
                            beforeDestroy: this.beforeDestroy,
                            destroyed: this.destroyed
                        };
                        return initData;
                    };
                    /**
                     * 组件方法
                     */
                    BaseComponentImpl.prototype.methods = function () {
                        return null;
                    };
                    /**
                     * 向子组件传值
                     * @returns {any}
                     */
                    BaseComponentImpl.prototype.props = function () {
                        return null;
                    };
                    /**
                     * 过滤器
                     * @returns {{[p: string]: Function}}
                     */
                    BaseComponentImpl.prototype.filters = function () {
                        return null;
                    };
                    /**
                     * 侦听器
                     */
                    BaseComponentImpl.prototype.watch = function () {
                        return null;
                    };
                    /**
                     * 计算属性
                     */
                    BaseComponentImpl.prototype.computed = function () {
                        return null;
                    };
                    BaseComponentImpl.prototype.beforeCreate = function () {
                    };
                    BaseComponentImpl.prototype.created = function () {
                    };
                    BaseComponentImpl.prototype.beforeMount = function () {
                    };
                    BaseComponentImpl.prototype.mounted = function () {
                    };
                    BaseComponentImpl.prototype.beforeUpdate = function () {
                    };
                    BaseComponentImpl.prototype.updated = function () {
                    };
                    BaseComponentImpl.prototype.beforeDestroy = function () {
                    };
                    BaseComponentImpl.prototype.destroyed = function () {
                    };
                    return BaseComponentImpl;
                }());
                vue.BaseComponentImpl = BaseComponentImpl;
            })(vue = ui.vue || (ui.vue = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
