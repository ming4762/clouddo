/**
 * BootstrapTable实现
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var table;
            (function (table) {
                var RestUtil = com.clouddo.ui.util.RestUtil;
                var TableUtil = com.clouddo.ui.table.TableUtil;
                var BootstrapTableImpl = /** @class */ (function () {
                    //构造方法
                    function BootstrapTableImpl(url, pageModel, gridElementId) {
                        this.tableConfig = {};
                        this.elementId = "grid-table";
                        //是否有复选框，默认有
                        this.hasCheckbox = true;
                        //是否显示序号
                        this.showNumber = true;
                        /**
                         * 发送到后台的数据
                         * @type {{}}
                         */
                        this.queryParameters = {};
                        //设置表格所在domId
                        if (gridElementId) {
                            this.elementId = gridElementId;
                        }
                        if (pageModel) {
                            this.pageModel = pageModel;
                        }
                        //设置表格的url
                        this.url = RestUtil.getBackgroundURL() + url;
                        //初始化表格
                        this.initTable();
                    }
                    /**
                     * 设置是否显示付现款
                     * @param {boolean} hasCheckbox
                     */
                    BootstrapTableImpl.prototype.setCheckbox = function (hasCheckbox) {
                        this.hasCheckbox = hasCheckbox;
                        return this;
                    };
                    /**
                     * 是否显示行号
                     * @param {boolean} showNumber
                     */
                    BootstrapTableImpl.prototype.setShowNumber = function (showNumber) {
                        this.showNumber = showNumber;
                        return this;
                    };
                    /**
                     *  创建表格
                     */
                    BootstrapTableImpl.prototype.createTable = function () {
                        //初始化事件
                        this.initEvent();
                        //初始化columns
                        this.initColumns();
                        $("#" + this.elementId).bootstrapTable(this.tableConfig);
                    };
                    BootstrapTableImpl.prototype.setParameters = function (parameters) {
                        this.queryParameters = $.extend({}, this.queryParameters, parameters);
                        return this;
                    };
                    BootstrapTableImpl.prototype.setCode = function (code) {
                        return this;
                    };
                    BootstrapTableImpl.prototype.setParentCode = function (parentCode) {
                        return this;
                    };
                    BootstrapTableImpl.prototype.getSelectRows = function () {
                        return undefined;
                    };
                    BootstrapTableImpl.prototype.clearTableData = function () {
                    };
                    /**
                     * 重新加载数据
                     */
                    BootstrapTableImpl.prototype.reload = function () {
                        $('#' + this.elementId).bootstrapTable('refresh', this.queryParameters);
                    };
                    BootstrapTableImpl.prototype.onTableComplate = function (_function) {
                    };
                    /**
                     * 设置配置信息
                     * @param {string} key 配置的key
                     * @param value 配置的值
                     */
                    BootstrapTableImpl.prototype.setConfig = function (key, value) {
                        this.tableConfig[key] = value;
                        return this;
                    };
                    /**
                     * 设置参数
                     * @param {string} key
                     * @param value
                     */
                    BootstrapTableImpl.prototype.setParameter = function (key, value) {
                        this.queryParameters[key] = value;
                        return this;
                    };
                    /**
                     * 在表格 body 渲染完成后触发
                     * @param {Function} onPostBodyFunction
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    BootstrapTableImpl.prototype.onPostBody = function (onPostBodyFunction) {
                        this.onPostBodyFunction = onPostBodyFunction;
                        return this;
                    };
                    /**
                     * 初始化事件
                     */
                    BootstrapTableImpl.prototype.initEvent = function () {
                        if (this.onPostBodyFunction) {
                            this.setConfig("onPostBody", this.onPostBodyFunction);
                        }
                    };
                    //初始化表格
                    BootstrapTableImpl.prototype.initTable = function () {
                        //设置默认参数
                        this.setDefaultConfig();
                        //设置后台地址
                        this.setConfig("url", this.url);
                    };
                    //初始化列信息
                    BootstrapTableImpl.prototype.initColumns = function () {
                        var columns = TableUtil.analysisColumns(this.elementId, this.hasCheckbox, this.showNumber, this.pageModel);
                        this.setConfig("columns", columns);
                    };
                    //设置默认配置
                    BootstrapTableImpl.prototype.setDefaultConfig = function () {
                        //设置请求头
                        var headers = {};
                        headers[RestUtil.TOKEN_KEY] = RestUtil.getToken();
                        this.tableConfig = {
                            //请求方式
                            method: "post",
                            //显示分页条
                            pagination: true,
                            //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置服务器数据地址（url）或者重写ajax方法。
                            sidePagination: "server",
                            //如果设置了分页，页面数据条数
                            pageSize: 20,
                            ajaxOptions: {
                                //设置请求头
                                headers: headers
                            },
                            //设置发送到后台的参数
                            queryParams: function (params) {
                                return $.extend({}, this.queryParameters, params);
                            },
                            // height: 300,
                            //隔行变色
                            striped: true,
                            //显示刷新按钮
                            showRefresh: false,
                            //解析数据
                            responseHandler: function (result) {
                                console.log(result);
                                var data = {};
                                data["total"] = result.data.total ? result.data.total : (result.data.rows ? result.data.rows.length : 0);
                                data["rows"] = result.data.rows;
                                return data;
                            }
                        };
                    };
                    return BootstrapTableImpl;
                }());
                table.BootstrapTableImpl = BootstrapTableImpl;
            })(table = ui.table || (ui.table = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
