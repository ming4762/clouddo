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
/**
 * 基于ElementUI封装的table实现
 * 支持普通表格  树形表格
 * @author zhongming
 * @since 3.0
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var table;
            (function (table) {
                //声明vue
                var RestUtil = com.clouddo.ui.util.RestUtil;
                var AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
                var Auth = com.clouddo.ui.auth.Auth;
                var ElementTableImpl = /** @class */ (function (_super) {
                    __extends(ElementTableImpl, _super);
                    /**
                     * 构造函数
                     * @param {string} queryUrl 查询接口地址
                     * @param {string} deleteUrl 删除接口地址
                     * @param keys 页面对应实体的key
                     */
                    function ElementTableImpl(queryUrl, deleteUrl, addPageUrl, tableType, keys, name) {
                        var _this = _super.call(this) || this;
                        //分页信息
                        //是否分页
                        _this.isPaging = true;
                        /**
                         * 绑定的dom
                         * @type {string}
                         */
                        _this.elementId = "tableVue";
                        //是否显示行号
                        _this.showNumber = true;
                        //是否有复选框
                        _this.hasCheckbox = true;
                        //发送到后台的参数数据
                        _this.queryParameters = {};
                        //按钮权限
                        _this.buttons = {};
                        //表格类型默认普通
                        _this.tableType = ElementTableImpl.NORMAL_TABLE;
                        //自定义方法
                        _this.customMethods = {};
                        //自定义过滤器
                        _this.customFilters = {};
                        // 自定义数据
                        _this.customData = {};
                        /**
                         * 展开节点
                         * @param data
                         * @param index 点击节点的位置
                         */
                        _this.appendChildren = function (data, index) {
                            if (data) {
                                for (var i = 0; i < data.length; i++) {
                                    //在指定位置添加数据
                                    _this.tableVue.tableData.splice(index + i + 1, 0, data[i]);
                                }
                            }
                        };
                        if (name) {
                            _this.name = name;
                        }
                        _this.keys = keys;
                        _this.tableType = tableType;
                        _this.queryUrl = queryUrl;
                        _this.deleteUrl = deleteUrl;
                        _this.addPageUrl = addPageUrl;
                        return _this;
                    }
                    /**
                     * 创建表格
                     */
                    ElementTableImpl.prototype.createTable = function () {
                        var _object = this;
                        //创建表格
                        this.tableVue = new Vue({
                            el: "#" + this.elementId,
                            data: $.extend({}, {
                                //表格数据
                                tableData: [],
                                columns: [],
                                loading: false,
                                //分页信息
                                page: {
                                    //每页记录数
                                    limit: 20,
                                    //起始记录数
                                    offset: 0,
                                    //总记录数
                                    total: 0,
                                    //当前页
                                    currentPage: 1
                                },
                                //按钮信息
                                buttons: this.buttons,
                                //多选结果
                                multipleSelection: [],
                                tableHeight: _object.getTableHeight()
                            }, this.customData),
                            methods: $.extend({}, {
                                //复选框选中事件
                                handleSelectionChange: function (value) {
                                    this.multipleSelection = value;
                                },
                                //改变每页记录数
                                handleSizeChange: function (val) {
                                    this.page.limit = val;
                                    _object.startPage(this.page);
                                },
                                //排序事件
                                handleSortChange: function (sortData) {
                                    _object.sortChangeMethod(sortData);
                                },
                                //页数改变
                                handleCurrentChange: function (val) {
                                    this.page.currentPage = val;
                                    _object.startPage(this.page);
                                    // console.log(`当前页: ${val}`);
                                },
                                //显示下级操作
                                handleShowHideChildren: function (index, row) {
                                    //改变样式
                                    //如果不存在样式信息，则默认是未展开的状态，点击后展开
                                    if (!row[ElementTableImpl.ROW_TREE_CLASS] || row[ElementTableImpl.ROW_TREE_CLASS] == "close") {
                                        //展开节点
                                        row[ElementTableImpl.ROW_TREE_CLASS] = "open";
                                        //展开节点
                                        _object.openTree(index, row);
                                    }
                                    else {
                                        row[ElementTableImpl.ROW_TREE_CLASS] = "close";
                                        //关闭节点
                                        _object.closeTree(index, row);
                                    }
                                },
                                //添加操作
                                handleAdd: function (index, row) {
                                    _object.addMethod(index, row);
                                },
                                //编辑操作
                                handleEdit: function (index, row) {
                                    _object.editMethod(index, row);
                                },
                                //删除操作
                                handleDelete: function (index, row) {
                                    //执行删除操作
                                    _object.deleteMethod(index, row);
                                }
                            }, this.customMethods),
                            mounted: function () {
                            },
                            filters: $.extend({}, {
                                //格式化树形结构按钮样式
                                formatTreeClass: function (row) {
                                    if (row[ElementTableImpl.ROW_TREE_CLASS] == "open") {
                                        return ElementTableImpl.TREE_OPEN_CLASS;
                                    }
                                    else {
                                        return ElementTableImpl.TREE_CLOSE_CLASS;
                                    }
                                },
                                //格式化树形节点的style（根据级别缩进） padding-left:
                                formatTreeStyle: function (row, number) {
                                    var px = 0;
                                    if (row && row[ElementTableImpl.ROW_TREE_LEVEL]) {
                                        var level = row[ElementTableImpl.ROW_TREE_LEVEL];
                                        if (level > 1) {
                                            px = 18 * (level - 1);
                                        }
                                        if (row["isLeaf"] == true) {
                                            px += 18;
                                        }
                                    }
                                    return "padding-left: " + px + "px;";
                                }
                            }, this.customFilters)
                        });
                        //设置复选框、行号
                        var columns = [];
                        if (this.hasCheckbox) {
                            columns.push({
                                type: "selection",
                                width: "40",
                                align: "center",
                            });
                        }
                        if (this.showNumber) {
                            columns.push({
                                type: "index",
                                width: "60",
                                align: "center",
                                label: "序号",
                            });
                        }
                        this.tableVue.columns = columns;
                        /**
                         * 加载数据
                         */
                        this.reload();
                    };
                    /**
                     * 设置实体的key
                     * @param {Array<string>} keys
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setKeys = function (keys) {
                        this.keys = keys;
                        return this;
                    };
                    /**
                     * 设置父子管理
                     * @param {string} code
                     * @param {string} parentCode
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setTreeCode = function (code, parentCode) {
                        this.code = code;
                        this.parentCode = parentCode;
                        return this;
                    };
                    /**
                     * 设置是否分页
                     * @param {boolean} isPaging
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setIsPaging = function (isPaging) {
                        this.isPaging = isPaging;
                        return this;
                    };
                    /**
                     * 设置按钮带有权限
                     * @param {{[p: string]: string}} buttonsPermission
                     * @returns {com.clouddo.ui.table.ElementTableImpl}
                     */
                    ElementTableImpl.prototype.setButtonsWithPermission = function (buttonsPermission) {
                        for (var button in buttonsPermission) {
                            //验证按钮的权限
                            this.buttons[button] = this.checkPermission(buttonsPermission[button]);
                        }
                        return this;
                    };
                    /**
                     * 设置是否显示复选框
                     * @param {boolean} hasCheckbox
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setCheckbox = function (hasCheckbox) {
                        this.hasCheckbox = hasCheckbox;
                        return this;
                    };
                    /**
                     * 是否显示行号
                     * @param {boolean} showNumber
                     */
                    ElementTableImpl.prototype.setShowNumber = function (showNumber) {
                        return this;
                    };
                    /**
                     * 设置vue自定义方法
                     * @param {{[p: string]: Function}} customMethods
                     * @returns {com.clouddo.ui.table.ElementTableImpl}
                     */
                    ElementTableImpl.prototype.setCustomMethods = function (customMethods) {
                        this.customMethods = customMethods;
                        return this;
                    };
                    /**
                     * 设置vue自定义方法
                     * @param {{[p: string]: Function}} customMethods
                     * @returns {com.clouddo.ui.table.ElementTableImpl}
                     */
                    ElementTableImpl.prototype.setCustomFilters = function (customFilters) {
                        this.customFilters = customFilters;
                        return this;
                    };
                    /**
                     * 设置自定义数据
                     * @param {{[p: string]: Function}} customData
                     * @returns {com.clouddo.ui.table.ElementTableImpl}
                     */
                    ElementTableImpl.prototype.setCustomData = function (customData) {
                        this.customData = customData;
                        return this;
                    };
                    /**
                     * 获取选中行
                     * @returns {any}
                     */
                    ElementTableImpl.prototype.getSelectRows = function () {
                        return undefined;
                    };
                    /**
                     * 清空数据
                     */
                    ElementTableImpl.prototype.clearTableData = function () {
                        //清空table数据
                        this.setTableData([]);
                        //清空记录数
                        this.setTotal(0);
                    };
                    /**
                     * 重新加载数据
                     */
                    ElementTableImpl.prototype.reload = function () {
                        var _object = this;
                        var paramets = this.queryParameters;
                        //设置分页信息
                        if (this.isPaging) {
                            paramets = $.extend({}, this.queryParameters, this.tableVue.page);
                        }
                        this.loadData(success, paramets);
                        //加载数据成功执行函数
                        function success(data) {
                            //如果是树形table对数据进行格式化
                            var tableData;
                            if (_object.tableType == ElementTableImpl.TREE_TABLE) {
                                tableData = _object.formatTreeLevel(data[ElementTableImpl.TABLE_DATA], 0);
                            }
                            else {
                                tableData = data[ElementTableImpl.TABLE_DATA];
                            }
                            //设置数据
                            _object.setTableData(tableData);
                            //设置记录数
                            _object.setTotal(data[ElementTableImpl.TOTAL] ? data[ElementTableImpl.TOTAL] : (data[ElementTableImpl.TABLE_DATA] ? data[ElementTableImpl.TABLE_DATA].length : 0));
                        }
                    };
                    /**
                     * 格式化add url
                     * @param {Function} formatAddUrlFunction
                     */
                    ElementTableImpl.prototype.formatAddUrl = function (formatAddUrlFunction) {
                        this.formatAddUrlFunction = formatAddUrlFunction;
                        return this;
                    };
                    /**
                     * 格式化edit url
                     * @param {Function} formatAddUrlFunction
                     */
                    ElementTableImpl.prototype.formatEditUrl = function (formatEditUrlFunction) {
                        this.formatEditUrlFunction = formatEditUrlFunction;
                        return this;
                    };
                    ElementTableImpl.prototype.onTableComplate = function (_function) {
                    };
                    ElementTableImpl.prototype.setConfig = function (key, value) {
                        return undefined;
                    };
                    ElementTableImpl.prototype.setParameter = function (key, value) {
                        return undefined;
                    };
                    /**
                     * 设置发送到后台的参数
                     * @param parameters
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setParameters = function (parameters) {
                        this.queryParameters = parameters;
                        return this;
                    };
                    ElementTableImpl.prototype.onPostBody = function (onPostBodyFunction) {
                        return undefined;
                    };
                    ElementTableImpl.prototype.setCode = function (code) {
                        return undefined;
                    };
                    ElementTableImpl.prototype.setParentCode = function (parentCode) {
                        return undefined;
                    };
                    //-----------事件----
                    /**
                     * 数据加载完成后事件
                     * @param {Function} onDataLoadFunction
                     */
                    ElementTableImpl.prototype.onDataLoadComplate = function (onDataLoadFunction) {
                        this.onDataLoadFunction = onDataLoadFunction;
                        return this;
                    };
                    //--------------- 重写下列方法可修改功能
                    /**
                     * 获取表格的高度 52
                     * @returns {number}
                     */
                    ElementTableImpl.prototype.getTableHeight = function () {
                        var _height = 83;
                        if (this.isPaging) {
                            _height += 32;
                        }
                        return document.body.clientHeight - _height;
                    };
                    /**
                     * 在数据中添加level属性
                     * @param datas
                     * @param {number} parentLevel
                     */
                    ElementTableImpl.prototype.formatTreeLevel = function (datas, parentLevel) {
                        if (datas) {
                            for (var _i = 0, datas_1 = datas; _i < datas_1.length; _i++) {
                                var data = datas_1[_i];
                                data[ElementTableImpl.ROW_TREE_LEVEL] = parentLevel + 1;
                            }
                        }
                        return datas;
                    };
                    /**
                     * 分页实现
                     * @param page
                     */
                    ElementTableImpl.prototype.startPage = function (page) {
                        //计算开始记录数
                        page[ElementTableImpl.OFFSET_KEY] = (page["currentPage"] - 1) * page[ElementTableImpl.PAGE_SIZE_KEY];
                        this.reload();
                    };
                    /**
                     * 排序实现
                     * @param sortData
                     */
                    ElementTableImpl.prototype.sortChangeMethod = function (sortData) {
                        console.log(sortData);
                        if (sortData["prop"]) {
                            //发送到后台的参数
                            //1、排序列名
                            this.queryParameters[ElementTableImpl.SORT_NAME] = sortData["prop"];
                            //2、排序方向
                            if (sortData["order"] && sortData["order"] == "descending") {
                                this.queryParameters[ElementTableImpl.SORT_ORDER] = "desc";
                            }
                            else {
                                this.queryParameters[ElementTableImpl.SORT_ORDER] = "asc";
                            }
                        }
                        this.reload();
                    };
                    /**
                     * 展开节点操作
                     */
                    ElementTableImpl.prototype.openTree = function (index, row) {
                        var pageObject = this;
                        //判断当前节点是否已经加载数据
                        if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                            //数据已经加载，直接展开节点
                            this.appendChildren(row[ElementTableImpl.ROW_CHILDREN_DATA], index);
                        }
                        else {
                            //从后台获取数据，放入row中，并展开节点
                            //设置参数
                            var parameters = {};
                            parameters[this.parentCode] = row[this.code];
                            //加载数据，并展开下级
                            this.loadData(success, parameters, index, row);
                        }
                        //数据获取成功后将节点放入row中，并展开节点
                        function success(data, index, row) {
                            //将后台获取的下级数据添加等级后放入row中
                            row[ElementTableImpl.ROW_CHILDREN_DATA] = pageObject.formatTreeLevel(data[ElementTableImpl.TABLE_DATA], row[ElementTableImpl.ROW_TREE_LEVEL]);
                            //展开节点
                            pageObject.appendChildren(row[ElementTableImpl.ROW_CHILDREN_DATA], index);
                        }
                    };
                    /**
                     * 获取row所有展开的下级的key
                     * @param row
                     * @param {Array<string>} keys
                     */
                    ElementTableImpl.prototype.getOpenRowCode = function (row, keys) {
                        if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                            for (var _i = 0, _a = row[ElementTableImpl.ROW_CHILDREN_DATA]; _i < _a.length; _i++) {
                                var child = _a[_i];
                                keys.push(child[this.code]);
                                //判断当前节点是否展开，如果站点递归获取下级key
                                if (child[ElementTableImpl.ROW_TREE_CLASS] == "open") {
                                    this.getOpenRowCode(child, keys);
                                }
                            }
                        }
                    };
                    /**
                     * 关闭节点操作
                     */
                    ElementTableImpl.prototype.closeTree = function (index, row) {
                        //获取节点中的下级数据，遍历数据并删除
                        //1、从row中获取下级节点数据，并将key放入数组中
                        if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                            var keys = [];
                            //获取keys
                            this.getOpenRowCode(row, keys);
                            //2、遍历所有数据，如果该元素在keys中，删除该元素
                            var i = this.tableVue.tableData.length;
                            while (i--) {
                                var data = this.tableVue.tableData[i];
                                if ($.inArray(data[this.code], keys) > -1) {
                                    //移除该元素
                                    this.tableVue.tableData.splice(i, 1);
                                }
                            }
                        }
                    };
                    /**
                     * 添加方法
                     * @param index
                     * @param row
                     */
                    ElementTableImpl.prototype.addMethod = function (index, row) {
                        var url = contextpath + this.addPageUrl;
                        //调用url格式化方法
                        if (this.formatAddUrlFunction) {
                            url = this.formatAddUrlFunction(row, url);
                        }
                        layer.open({
                            type: 2,
                            title: "新增" + (this.name ? this.name : ""),
                            maxmin: true,
                            area: ['60%', '60%'],
                            content: url
                        });
                    };
                    /**
                     * 树形table数据处理函数
                     * todo 未做处理
                     * @param data
                     * @param parameterSet
                     */
                    ElementTableImpl.prototype.formatTreeData = function (data) {
                        var parameterSet = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            parameterSet[_i - 1] = arguments[_i];
                        }
                        if (data && data["rows"]) {
                        }
                        return data;
                    };
                    /**
                     * 删除方法
                     * @param index
                     * @param row
                     */
                    ElementTableImpl.prototype.deleteMethod = function (index, row) {
                        var _object = this;
                        //提示选中删除行
                        if (!row && (!this.tableVue.multipleSelection || this.tableVue.multipleSelection.length == 0)) {
                            layer.alert('请选择要删除的记录！', { icon: 0 });
                            return false;
                        }
                        //设置删除数据
                        var deleteData = [];
                        if (row) {
                            //点击行删除
                            deleteData.push(row);
                        }
                        else {
                            //点击批量删除
                            deleteData = this.tableVue.multipleSelection;
                        }
                        layer.confirm("你确定要删除【" + deleteData.length + "】条记录吗？", {
                            icon: 3,
                            title: '警告',
                            btn: ['确定', '取消']
                        }, function (index) {
                            //执行删除
                            _object.batchDelete(deleteData);
                            layer.close(index);
                        }, function (index) {
                            //取消操作
                            layer.close(index);
                        });
                    };
                    /**
                     * 编辑操作
                     * @param index
                     * @param row
                     */
                    ElementTableImpl.prototype.editMethod = function (index, row) {
                        var url = contextpath + this.addPageUrl;
                        if (row && this.keys && this.keys.length > 0) {
                            //将当前记录的key拼接到url
                            for (var _i = 0, _a = this.keys; _i < _a.length; _i++) {
                                var key = _a[_i];
                                if (row[key]) {
                                    if (url.indexOf("?") != -1) {
                                        url += "&" + key + "=" + row[key];
                                    }
                                    else {
                                        url += "?" + key + "=" + row[key];
                                    }
                                }
                            }
                        }
                        //调用格式化方法
                        if (this.formatEditUrlFunction) {
                            url = this.formatEditUrlFunction(row, url);
                        }
                        layer.open({
                            type: 2,
                            title: "编辑" + (this.name ? this.name : ""),
                            maxmin: true,
                            area: ['60%', '60%'],
                            content: url
                        });
                    };
                    //---------私有方法---------
                    /**
                     * 设置表格数据
                     * @param {Array<any>} data
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setTableData = function (data) {
                        this.tableVue.tableData = data;
                        return this;
                    };
                    /**
                     * 设置总记录数
                     * @param {number} total
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.setTotal = function (total) {
                        this.tableVue.page.total = total;
                        return this;
                    };
                    /**
                     * 批量删除
                     * @param {Array<any>} rows
                     * @returns {com.clouddo.ui.table.Table}
                     */
                    ElementTableImpl.prototype.batchDelete = function (rows) {
                        var tableObject = this;
                        //将数据中keys传到后台执行删除
                        var deleteDatas = [];
                        if (this.keys && this.keys.length > 0) {
                            for (var _i = 0, rows_1 = rows; _i < rows_1.length; _i++) {
                                var row = rows_1[_i];
                                var deleteData = {};
                                for (var _a = 0, _b = this.keys; _a < _b.length; _a++) {
                                    var key = _b[_a];
                                    deleteData[key] = row[key];
                                }
                                deleteDatas.push(deleteData);
                            }
                        }
                        else {
                            //如果没有key，将数据全部发送到后台，由后台处理
                            deleteDatas = rows;
                        }
                        //执行删除操作
                        RestUtil.postAjax(this.deleteUrl, deleteDatas, success, error);
                        function success(data) {
                            //重新加载数据
                            //TODO 精准移除指定数据，提升用户体验
                            tableObject.reload();
                        }
                        function error(data) {
                            layer.alert('删除时发生错误！', { icon: 0 });
                        }
                        return this;
                    };
                    /**
                     * 加载数据
                     * @param success
                     */
                    ElementTableImpl.prototype.loadData = function (success, queryParameters) {
                        var parameters = [];
                        for (var _i = 2; _i < arguments.length; _i++) {
                            parameters[_i - 2] = arguments[_i];
                        }
                        var $this = this;
                        $this.tableVue.loading = true;
                        AuthRestUtil.postAjax.apply(AuthRestUtil, [this.queryUrl, (queryParameters ? queryParameters : {}), defaultSuccess, null].concat(parameters));
                        //默认的数据加载完成后函数
                        function defaultSuccess(data) {
                            var parameterSet = [];
                            for (var _i = 1; _i < arguments.length; _i++) {
                                parameterSet[_i - 1] = arguments[_i];
                            }
                            $this.tableVue.loading = false;
                            //执行数据加载后事件
                            if ($this.onDataLoadFunction) {
                                $this.onDataLoadFunction.apply($this, [data].concat(parameterSet));
                            }
                            //处理树形table数据处理函数
                            // if (pageObject.tableType = ElementTableImpl.TREE_TABLE) {
                            //     data = pageObject.formatTreeData(data, ...parameterSet);
                            // }
                            //执行传入的success回调函数
                            success.apply(void 0, [data].concat(parameterSet));
                        }
                    };
                    //后台发送数据的key
                    ElementTableImpl.TABLE_DATA = "rows";
                    //后台发送数据条数的key
                    ElementTableImpl.TOTAL = "total";
                    //排序信息
                    ElementTableImpl.SORT_NAME = "sortName";
                    ElementTableImpl.SORT_ORDER = "sortOrder";
                    //向后台发送pagesize的key
                    ElementTableImpl.PAGE_SIZE_KEY = "limit";
                    //向后台发送起始记录数的key
                    ElementTableImpl.OFFSET_KEY = "offset";
                    //标识树形表格
                    ElementTableImpl.TREE_TABLE = "tree_table";
                    ElementTableImpl.NORMAL_TABLE = "normal_table";
                    //树形表格展开、关闭样式
                    ElementTableImpl.TREE_OPEN_CLASS = "el-tree-node__expand-icon el-icon-caret-right expanded";
                    ElementTableImpl.TREE_CLOSE_CLASS = "el-tree-node__expand-icon el-icon-caret-right";
                    //数据中存储的tree的样式
                    ElementTableImpl.ROW_TREE_CLASS = "treeClass";
                    //数据中存储树形结构下级数据的key
                    ElementTableImpl.ROW_CHILDREN_DATA = "tree_children_data";
                    //标识当前数据的级别的key
                    ElementTableImpl.ROW_TREE_LEVEL = "tree_level";
                    return ElementTableImpl;
                }(Auth));
                table.ElementTableImpl = ElementTableImpl;
            })(table = ui.table || (ui.table = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
