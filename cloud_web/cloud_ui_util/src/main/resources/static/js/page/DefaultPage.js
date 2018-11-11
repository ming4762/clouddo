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
            var page;
            (function (page) {
                //声明vue
                var RestUtil = com.clouddo.ui.util.RestUtil;
                var Auth = com.clouddo.ui.auth.Auth;
                var BootstrapTableImpl = com.clouddo.ui.table.BootstrapTableImpl;
                var BootstrapTreeTableImpl = com.clouddo.ui.table.BootstrapTreeTableImpl;
                /**
                 * 默认页面实体
                 */
                var DefaultPage = /** @class */ (function (_super) {
                    __extends(DefaultPage, _super);
                    function DefaultPage(getUrl, saveUpdateUrl, deleteUrl, tableType) {
                        var _this = _super.call(this) || this;
                        //需要权限验证domIds
                        _this.permissionDomIds = ['operationDiv'];
                        _this.tableType = "normal";
                        if (tableType) {
                            _this.tableType = tableType;
                        }
                        _this.getUrl = getUrl;
                        _this.deleteUrl = deleteUrl;
                        _this.saveUpdateUrl = saveUpdateUrl;
                        return _this;
                    }
                    /**
                     * 初始化
                     * @returns {com.clouddo.ui.page.Page}
                     */
                    DefaultPage.prototype.init = function () {
                        var pageModel = this;
                        //验证按钮权限
                        this.authPermission(this.permissionDomIds);
                        //初始化vue
                        this.initVue();
                        //创建表格
                        if (this.tableType == DefaultPage.TREE_TABLE_TYPE) {
                            this.table = new BootstrapTreeTableImpl(this.getUrl, this);
                        }
                        else {
                            this.table = new BootstrapTableImpl(this.getUrl, this);
                        }
                        //设置表格加载事件
                        this.table.onPostBody(function () {
                            //刷新vue
                            pageModel.mainVue.$forceUpdate();
                        });
                        return this;
                    };
                    /**
                     * 设置需要验证的domId集合
                     * @param permissionDomIds
                     */
                    DefaultPage.prototype.setPermissionDomIds = function () {
                        var permissionDomIds = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            permissionDomIds[_i] = arguments[_i];
                        }
                        this.permissionDomIds = permissionDomIds;
                        return this;
                    };
                    /**
                     * 创建表格方法
                     */
                    DefaultPage.prototype.createTable = function () {
                        this.table.createTable();
                        return this;
                    };
                    /**
                     * 获取表格实体
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    DefaultPage.prototype.getTable = function () {
                        return this.table;
                    };
                    /**
                     * 查询方法
                     */
                    DefaultPage.prototype.query = function () {
                        this.getTable()
                            .setParameters(this.searchVue.searchModel)
                            .reload();
                    };
                    //添加修改弹窗前操作
                    DefaultPage.prototype.beforAdd = function () {
                        var parameters = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            parameters[_i] = arguments[_i];
                        }
                        //清空数据
                        // this.saveUpdateVue.saveUpdateModel = {};
                        //显示窗口
                        this.showSaveUpdateWindow();
                    };
                    /**
                     * 添加前操作
                     * @param keys
                     */
                    DefaultPage.prototype.beforUpdate = function () {
                        var keys = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            keys[_i] = arguments[_i];
                        }
                        //设置参数key
                        var parameters = {};
                        if (this.keys) {
                            for (var i = 0; i < this.keys.length; i++) {
                                parameters[this.keys[i]] = keys[i];
                            }
                        }
                        //查询数据
                        RestUtil.postAjax(this.getUrl, parameters, this.beforUpdateSuccess, null);
                    };
                    /**
                     * 更新操作获取数据成功
                     * @param data
                     */
                    DefaultPage.prototype.beforUpdateSuccess = function (data) {
                        if (data && data.length > 0) {
                            this.mainVue.saveUpdateModel = data[0];
                            //显示窗口
                            this.showSaveUpdateWindow();
                        }
                    };
                    /**
                     * 保存添加操作
                     */
                    DefaultPage.prototype.saveUpdate = function (index) {
                        var pageModel = this;
                        RestUtil.postAjax(this.saveUpdateUrl, this.mainVue.saveUpdateModel, success, error);
                        function success(data) {
                            layer.close(index);
                            //重新加载表格
                            pageModel.table.reload();
                        }
                        function error(data) {
                            layer.alert('添加修改发生错误', { icon: 2 });
                        }
                    };
                    /**
                     * 显示添加修改窗口
                     */
                    DefaultPage.prototype.showSaveUpdateWindow = function () {
                        var pageModel = this;
                        layer.open({
                            type: 1,
                            content: $("#saveUpdateDiv"),
                            closeBtn: 0,
                            shadeClose: false,
                            area: ['50%', "60%"],
                            btn: ['确认', '取消'],
                            yes: function (index, layero) {
                                pageModel.saveUpdate(index);
                                // saveUpdate(index, _this);
                            },
                            btn2: function (index, layero) {
                                //按钮【按钮二】的回调
                                // _this.closeLayer(index, "addUpdateDiv");
                                //return false 开启该代码可禁止点击该按钮关闭
                            }
                        });
                    };
                    //初始化vue
                    DefaultPage.prototype.initVue = function () {
                        var pageModel = this;
                        this.searchVue = new Vue({
                            el: '#searchDiv',
                            data: {
                                searchModel: {}
                            },
                            methods: {
                                //查询方法
                                query: function (event) {
                                    pageModel.query();
                                }
                            }
                        });
                        //插入语更新VUE
                        this.mainVue = new Vue({
                            el: "#saveUpdateDiv",
                            data: {
                                //实体类
                                saveUpdateModel: {},
                                //显示实体
                                showModel: {}
                            },
                            methods: $.extend({}, {}, this.saveUpdateVueMethods),
                            watch: {
                                saveUpdateModel: function (newValue, oldValue, a) {
                                    console.log(newValue);
                                    console.log(oldValue);
                                }
                            }
                        });
                        //操作vue
                        this.mainVue = new Vue({
                            el: "#mainVue",
                            data: {},
                            methods: {
                                //添加操作
                                addMethod: function () {
                                    var data = [];
                                    for (var _i = 0; _i < arguments.length; _i++) {
                                        data[_i] = arguments[_i];
                                    }
                                    pageModel.beforAdd.apply(pageModel, data);
                                },
                                //更新操作
                                updateMethod: function () {
                                    var keys = [];
                                    for (var _i = 0; _i < arguments.length; _i++) {
                                        keys[_i] = arguments[_i];
                                    }
                                    pageModel.beforUpdate.apply(pageModel, keys);
                                },
                                //删除操作
                                deleteMethod: function () {
                                    var data = [];
                                    for (var _i = 0; _i < arguments.length; _i++) {
                                        data[_i] = arguments[_i];
                                    }
                                }
                            }
                        });
                    };
                    DefaultPage.TREE_TABLE_TYPE = "tree";
                    DefaultPage.SAVE_IDENT = "save";
                    DefaultPage.UPDATE_IDENT = "update";
                    return DefaultPage;
                }(Auth));
                page.DefaultPage = DefaultPage;
            })(page = ui.page || (ui.page = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
