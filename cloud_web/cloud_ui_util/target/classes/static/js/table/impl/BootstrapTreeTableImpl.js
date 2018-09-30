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
                var BootstrapTreeTableImpl = /** @class */ (function () {
                    function BootstrapTreeTableImpl(url, pageModel, gridElementId) {
                        this.tableConfig = {};
                        this.elementId = "grid-table";
                        //是否有复选框，默认有
                        this.hasCheckbox = false;
                        //是否显示序号
                        this.showNumber = false;
                        //设置表格所在domId
                        if (gridElementId) {
                            this.elementId = gridElementId;
                        }
                        if (pageModel) {
                            this.pageModel = pageModel;
                        }
                        //设置表格的url
                        this.url = RestUtil.getBackgroundURL() + url;
                        this.initTable();
                    }
                    /**
                     * 设置code
                     * @param {string} code
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    BootstrapTreeTableImpl.prototype.setCode = function (code) {
                        this.tableConfig["code"] = code;
                        return this;
                    };
                    BootstrapTreeTableImpl.prototype.setParentCode = function (parentCode) {
                        this.tableConfig["parentCode"] = parentCode;
                        return this;
                    };
                    /**
                     *  创建表格
                     */
                    BootstrapTreeTableImpl.prototype.createTable = function () {
                        //初始化columns
                        this.initColumns();
                        $("#" + this.elementId).bootstrapTreeTable(this.tableConfig);
                    };
                    /**
                     * 设置是否显示付现款
                     * @param {boolean} hasCheckbox
                     */
                    BootstrapTreeTableImpl.prototype.setCheckbox = function (hasCheckbox) {
                        this.hasCheckbox = hasCheckbox;
                        return this;
                    };
                    /**
                     * 是否显示行号
                     * @param {boolean} showNumber
                     */
                    BootstrapTreeTableImpl.prototype.setShowNumber = function (showNumber) {
                        this.showNumber = showNumber;
                        return this;
                    };
                    /**
                     * 设置配置
                     * @param {string} key
                     * @param value
                     */
                    BootstrapTreeTableImpl.prototype.setConfig = function (key, value) {
                        this.tableConfig[key] = value;
                        return this;
                    };
                    /**
                     * 设置参数
                     * @param {string} key
                     * @param value
                     */
                    BootstrapTreeTableImpl.prototype.setParameter = function (key, value) {
                        this.tableConfig.ajaxParams[key] = value;
                        return this;
                    };
                    /**
                     * 在表格 body 渲染完成后触发
                     * @param {Function} onPostBodyFunction
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    BootstrapTreeTableImpl.prototype.onPostBody = function (onPostBodyFunction) {
                        return this;
                    };
                    /**
                     * 设置参数
                     * @param parameters
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    BootstrapTreeTableImpl.prototype.setParameters = function (parameters) {
                        this.tableConfig.ajaxParams = $.extend({}, this.tableConfig.ajaxParams, parameters);
                        return this;
                    };
                    BootstrapTreeTableImpl.prototype.getSelectRows = function () {
                        return undefined;
                    };
                    BootstrapTreeTableImpl.prototype.clearTableData = function () {
                    };
                    BootstrapTreeTableImpl.prototype.reload = function () {
                    };
                    BootstrapTreeTableImpl.prototype.onTableComplate = function (_function) {
                    };
                    //表格初始化
                    BootstrapTreeTableImpl.prototype.initTable = function () {
                        //设置默认参数
                        this.setDefaultConfig();
                        //设置后台地址
                        this.setConfig("url", this.url);
                    };
                    //设置默认配置
                    BootstrapTreeTableImpl.prototype.setDefaultConfig = function () {
                        //设置请求头
                        var headers = {};
                        headers[RestUtil.TOKEN_KEY] = RestUtil.getToken();
                        this.tableConfig = {
                            //请求方式
                            type: "POST",
                            contentType: 'application/json; charset=UTF-8',
                            striped: true,
                            bordered: true,
                            expandAll: false,
                            ajaxParams: {},
                            headers: headers,
                            dataFormat: function (data) {
                                //待完善
                                return data.data;
                            }
                        };
                    };
                    //初始化列信息
                    BootstrapTreeTableImpl.prototype.initColumns = function () {
                        //获取dom信息
                        this.setConfig("columns", TableUtil.analysisColumns(this.elementId, this.hasCheckbox, this.showNumber, this.pageModel));
                    };
                    return BootstrapTreeTableImpl;
                }());
                table.BootstrapTreeTableImpl = BootstrapTreeTableImpl;
            })(table = ui.table || (ui.table = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
