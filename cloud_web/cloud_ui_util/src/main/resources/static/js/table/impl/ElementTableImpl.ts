/**
 * 基于ElementUI封装的table实现
 * 支持普通表格  树形表格
 * @author zhongming
 * @since 3.0
 */
namespace com.clouddo.ui.table {
    //声明vue
    import RestUtil = com.clouddo.ui.util.RestUtil;
    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
    import Auth = com.clouddo.ui.auth.Auth;
    declare let Vue : any;
    //声明layer
    declare let layer: any;
    //声明jquery
    declare let $ : any;
    //声明项目路径
    declare let contextpath : string;
    //声明table接口
    declare let Table : any;
    export class ElementTableImpl extends Auth implements Table {


        //后台发送数据的key
        public static TABLE_DATA = "rows";
        //后台发送数据条数的key
        public static TOTAL = "total";
        //排序信息
        public static SORT_NAME : string = "sortName";
        public static SORT_ORDER : string = "sortOrder";


        //分页信息
        //是否分页
        public isPaging : boolean = true;
        //向后台发送pagesize的key
        public static PAGE_SIZE_KEY : string = "limit";
        //向后台发送起始记录数的key
        public static OFFSET_KEY : string = "offset";

        //标识树形表格
        public static TREE_TABLE = "tree_table";
        public static NORMAL_TABLE = "normal_table";

        //树形表格展开、关闭样式
        public static TREE_OPEN_CLASS = "el-tree-node__expand-icon el-icon-caret-right expanded";
        public static TREE_CLOSE_CLASS = "el-tree-node__expand-icon el-icon-caret-right";
        //数据中存储的tree的样式
        public static ROW_TREE_CLASS = "treeClass";
        //数据中存储树形结构下级数据的key
        public static ROW_CHILDREN_DATA = "tree_children_data";
        //标识当前数据的级别的key
        public static ROW_TREE_LEVEL = "tree_level";


        /**
         * 绑定的dom
         * @type {string}
         */
        private elementId : string = "tableVue"
        //tableVue实体
        private tableVue : any;

        //是否显示行号
        private showNumber: boolean = true;
        //是否有复选框
        private hasCheckbox : boolean = true;

        //查询接口url
        private queryUrl : string;
        //删除接口url
        private deleteUrl: string;
        //添加页面URL
        private addPageUrl : string;

        //发送到后台的参数数据
        private queryParameters : any = {};
        //按钮权限
        private buttons : {[index:string]: boolean} = {};

        //table实体的key
        private keys : Array<string>;

        //table的名称
        private name: string;

        //用于设置父子关系
        private parentCode : string;
        private code : string;

        //表格类型默认普通
        private tableType : string = ElementTableImpl.NORMAL_TABLE;

        //数据加载完成后执行
        private onDataLoadFunction : Function;

        //自定义方法
        private customMethods : {[index:string]: Function} = {};
        //自定义过滤器
        private customFilters : {[index:string]: Function} = {};

        //格式化添加/修改URL方法
        private formatAddUrlFunction : Function;
        private formatEditUrlFunction : Function;

        /**
         * 构造函数
         * @param {string} queryUrl 查询接口地址
         * @param {string} deleteUrl 删除接口地址
         * @param keys 页面对应实体的key
         */
        constructor(queryUrl: string, deleteUrl: string, addPageUrl : string, tableType : string, keys : Array<string>, name ? :string) {
            super();
            if (name) {
                this.name = name;
            }
            this.keys = keys;
            this.tableType = tableType;
            this.queryUrl = queryUrl;
            this.deleteUrl = deleteUrl;
            this.addPageUrl = addPageUrl;
        }

        /**
         * 创建表格
         */
        public createTable(): void {
            let _object = this;
            //创建表格
            this.tableVue = new Vue({
                el: "#" + this.elementId,
                data: {
                    //表格数据
                    tableData : [],
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
                    buttons : this.buttons,
                    //多选结果
                    multipleSelection: [],
                    tableHeight: _object.getTableHeight()
                },
                methods: $.extend({}, {
                    //复选框选中事件
                    handleSelectionChange: function (value) {
                        this.multipleSelection = value;
                    },
                    //改变每页记录数
                    handleSizeChange(val) {
                        this.page.limit = val;
                        _object.startPage(this.page);
                    },
                    //排序事件
                    handleSortChange: function (sortData) {
                        _object.sortChangeMethod(sortData);
                    },
                    //页数改变
                    handleCurrentChange(val) {
                        this.page.currentPage = val;
                        _object.startPage(this.page);
                        // console.log(`当前页: ${val}`);
                    },
                    //显示下级操作
                    handleShowHideChildren: function (index, row) {
                        //改变样式
                        //如果不存在样式信息，则默认是未展开的状态，点击后展开
                        if(!row[ElementTableImpl.ROW_TREE_CLASS] || row[ElementTableImpl.ROW_TREE_CLASS] == "close") {
                            //展开节点
                            row[ElementTableImpl.ROW_TREE_CLASS] = "open";
                            //展开节点
                            _object.openTree(index, row);
                        } else {
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
                        } else {
                            return ElementTableImpl.TREE_CLOSE_CLASS;
                        }
                    },
                    //格式化树形节点的style（根据级别缩进） padding-left:
                    formatTreeStyle: function (row, number) {
                        let px = 0;
                        if (row && row[ElementTableImpl.ROW_TREE_LEVEL]) {
                            let level = row[ElementTableImpl.ROW_TREE_LEVEL];
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
            let columns : Array<any> = [];
            if(this.hasCheckbox) {
                columns.push({
                    type: "selection",
                    width: "40",
                    align: "center",
                });
            }
            if(this.showNumber) {
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
        }

        /**
         * 设置实体的key
         * @param {Array<string>} keys
         * @returns {com.clouddo.ui.table.Table}
         */
        public setKeys(keys: Array<string>): com.clouddo.ui.table.Table {
            this.keys = keys;
            return this;
        }

        /**
         * 设置父子管理
         * @param {string} code
         * @param {string} parentCode
         * @returns {com.clouddo.ui.table.Table}
         */
        public setTreeCode(code : string, parentCode : string) : com.clouddo.ui.table.Table {
            this.code = code;
            this.parentCode = parentCode;
            return this;
        }

        /**
         * 设置是否分页
         * @param {boolean} isPaging
         * @returns {com.clouddo.ui.table.Table}
         */
        setIsPaging(isPaging: boolean): com.clouddo.ui.table.Table {
            this.isPaging = isPaging;
            return this;
        }

        /**
         * 设置按钮带有权限
         * @param {{[p: string]: string}} buttonsPermission
         * @returns {com.clouddo.ui.table.ElementTableImpl}
         */
        public setButtonsWithPermission(buttonsPermission : {[index:string]: string}) : ElementTableImpl {
            for (let button in buttonsPermission) {
                //验证按钮的权限
                this.buttons[button] = this.checkPermission(buttonsPermission[button]);
            }
            return this;
        }


        /**
         * 设置是否显示复选框
         * @param {boolean} hasCheckbox
         * @returns {com.clouddo.ui.table.Table}
         */
        public setCheckbox(hasCheckbox: boolean): com.clouddo.ui.table.Table {
            this.hasCheckbox = hasCheckbox;
            return this;
        }

        /**
         * 是否显示行号
         * @param {boolean} showNumber
         */
        public setShowNumber(showNumber: boolean): com.clouddo.ui.table.Table {
            return this;
        }

        /**
         * 设置vue自定义方法
         * @param {{[p: string]: Function}} customMethods
         * @returns {com.clouddo.ui.table.ElementTableImpl}
         */
        public setCustomMethods(customMethods : {[index:string]: Function}) : ElementTableImpl {
            this.customMethods = customMethods;
            return this;
        }


        /**
         * 设置vue自定义方法
         * @param {{[p: string]: Function}} customMethods
         * @returns {com.clouddo.ui.table.ElementTableImpl}
         */
        public setCustomFilters(customFilters : {[index:string]: Function}) : ElementTableImpl {
            this.customFilters = customFilters;
            return this;
        }



        /**
         * 获取选中行
         * @returns {any}
         */
        public getSelectRows(): any {
            return undefined;
        }

        /**
         * 清空数据
         */
        public clearTableData(): void {
            //清空table数据
            this.setTableData([]);
            //清空记录数
            this.setTotal(0);
        }

        /**
         * 重新加载数据
         */
        public reload(): void {
            let _object = this;

            let paramets = this.queryParameters;
            //设置分页信息
            if (this.isPaging) {
                paramets = $.extend({}, this.queryParameters, this.tableVue.page);
            }
            this.loadData(success, paramets);

            //加载数据成功执行函数
            function success(data) {
                //如果是树形table对数据进行格式化
                var tableData : any;
                if (_object.tableType == ElementTableImpl.TREE_TABLE) {
                    tableData = _object.formatTreeLevel(data[ElementTableImpl.TABLE_DATA], 0);
                } else {
                    tableData = data[ElementTableImpl.TABLE_DATA];
                }
                //设置数据
                _object.setTableData(tableData);
                //设置记录数
                _object.setTotal(data[ElementTableImpl.TOTAL] ? data[ElementTableImpl.TOTAL] : (data[ElementTableImpl.TABLE_DATA] ? data[ElementTableImpl.TABLE_DATA].length : 0));
            }
        }


        /**
         * 格式化add url
         * @param {Function} formatAddUrlFunction
         */
        public formatAddUrl(formatAddUrlFunction : Function) : com.clouddo.ui.table.Table {
            this.formatAddUrlFunction = formatAddUrlFunction;
            return this;
        }

        /**
         * 格式化edit url
         * @param {Function} formatAddUrlFunction
         */
        public formatEditUrl(formatEditUrlFunction: Function): com.clouddo.ui.table.Table {
            this.formatEditUrlFunction = formatEditUrlFunction;
            return this;
        }


        onTableComplate(_function: Function): void {
        }

        setConfig(key: string, value: any): com.clouddo.ui.table.Table {
            return undefined;
        }

        setParameter(key: string, value: any): com.clouddo.ui.table.Table {
            return undefined;
        }

        /**
         * 设置发送到后台的参数
         * @param parameters
         * @returns {com.clouddo.ui.table.Table}
         */
        public setParameters(parameters: any): com.clouddo.ui.table.Table {
            this.queryParameters = parameters;
            return this;
        }

        onPostBody(onPostBodyFunction: Function): com.clouddo.ui.table.Table {
            return undefined;
        }

        setCode(code: string): com.clouddo.ui.table.Table {
            return undefined;
        }

        setParentCode(parentCode: string): com.clouddo.ui.table.Table {
            return undefined;
        }


        //-----------事件----

        /**
         * 数据加载完成后事件
         * @param {Function} onDataLoadFunction
         */
        public onDataLoadComplate(onDataLoadFunction: Function) : com.clouddo.ui.table.Table {
            this.onDataLoadFunction = onDataLoadFunction;
            return this;
        }

        //--------------- 重写下列方法可修改功能


        /**
         * 获取表格的高度 52
         * @returns {number}
         */
        public getTableHeight() : number {
            let _height = 91;
            if (this.isPaging) {
                _height += 52;
            }
            return document.body.clientHeight - _height;
        }

        /**
         * 在数据中添加level属性
         * @param datas
         * @param {number} parentLevel
         */
        formatTreeLevel(datas : any, parentLevel : number) {
            if (datas) {
                for(var data of datas) {
                    data[ElementTableImpl.ROW_TREE_LEVEL] = parentLevel + 1;
                }
            }
            return datas;
        }

        /**
         * 分页实现
         * @param page
         */
        startPage(page : any) : void {
            //计算开始记录数
            page[ElementTableImpl.OFFSET_KEY] = (page["currentPage"] - 1) * page[ElementTableImpl.PAGE_SIZE_KEY];
            this.reload();
        }

        /**
         * 排序实现
         * @param sortData
         */
        sortChangeMethod(sortData : any) {
            console.log(sortData);
            if (sortData["prop"]) {
                //发送到后台的参数
                //1、排序列名
                this.queryParameters[ElementTableImpl.SORT_NAME] = sortData["prop"];
                //2、排序方向
                if (sortData["order"] && sortData["order"] == "descending") {
                    this.queryParameters[ElementTableImpl.SORT_ORDER] = "desc";
                } else {
                    this.queryParameters[ElementTableImpl.SORT_ORDER] = "asc";
                }
            }
            this.reload();
        }

        /**
         * 展开节点操作
         */
        openTree(index : number, row : any) : void {
            let pageObject = this;
            //判断当前节点是否已经加载数据
            if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                //数据已经加载，直接展开节点
                this.appendChildren(row[ElementTableImpl.ROW_CHILDREN_DATA], index);
            } else {
                //从后台获取数据，放入row中，并展开节点
                //设置参数
                let parameters = {};
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
        }

        /**
         * 获取row所有展开的下级的key
         * @param row
         * @param {Array<string>} keys
         */
        getOpenRowCode(row: any, keys : Array<string>) {
            if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                for (let child of row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                    keys.push(child[this.code]);
                    //判断当前节点是否展开，如果站点递归获取下级key
                    if (child[ElementTableImpl.ROW_TREE_CLASS] == "open") {
                        this.getOpenRowCode(child, keys);
                    }
                }
            }
        }

        /**
         * 关闭节点操作
         */
        closeTree(index, row) : void {
            //获取节点中的下级数据，遍历数据并删除
            //1、从row中获取下级节点数据，并将key放入数组中
            if (row[ElementTableImpl.ROW_CHILDREN_DATA]) {
                let keys : Array<any> = [];
                //获取keys
                this.getOpenRowCode(row, keys);
                //2、遍历所有数据，如果该元素在keys中，删除该元素
                let i = this.tableVue.tableData.length;
                while (i --) {
                    let data = this.tableVue.tableData[i];
                    if ($.inArray(data[this.code], keys) > -1) {
                        //移除该元素
                        this.tableVue.tableData.splice(i, 1);
                    }
                }
            }
        }

        /**
         * 添加方法
         * @param index
         * @param row
         */
        addMethod(index, row) {
            let url = contextpath + this.addPageUrl;
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
        }

        /**
         * 树形table数据处理函数
         * todo 未做处理
         * @param data
         * @param parameterSet
         */
        formatTreeData(data, ...parameterSet) {
            if(data && data["rows"]) {

            }
            return data;
        }

        /**
         * 删除方法
         * @param index
         * @param row
         */
        deleteMethod(index, row) {
            let _object = this;
            //提示选中删除行
            if (!row && (!this.tableVue.multipleSelection || this.tableVue.multipleSelection.length == 0)) {
                layer.alert('请选择要删除的记录！', {icon: 0});
                return false;
            }
            //设置删除数据
            let deleteData = [];
            if (row) {
                //点击行删除
                deleteData.push(row);
            } else {
                //点击批量删除
                deleteData = this.tableVue.multipleSelection;
            }
            layer.confirm("你确定要删除【"+ deleteData.length +"】条记录吗？", {
                icon: 3,
                title:'警告',
                btn: ['确定','取消']
            }, function (index) {
                //执行删除
                _object.batchDelete(deleteData);
                layer.close(index);

            }, function (index) {
                //取消操作
                layer.close(index);
            });
        }

        /**
         * 编辑操作
         * @param index
         * @param row
         */
        editMethod(index, row) {
            let url = contextpath + this.addPageUrl;
            if (row && this.keys && this.keys.length > 0) {
                //将当前记录的key拼接到url
                for (let key of this.keys) {
                    if (row[key]) {
                        if (url.indexOf("?") != -1) {
                            url += "&" + key + "=" + row[key];
                        } else {
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
        }

        /**
         * 展开节点
         * @param data
         * @param index 点击节点的位置
         */
        protected appendChildren = (data, index) => {
            if (data) {
                for (let i = 0; i<data.length; i++) {
                    //在指定位置添加数据
                    this.tableVue.tableData.splice(index + i + 1, 0, data[i]);
                }
            }
        }

        //---------私有方法---------

        /**
         * 设置表格数据
         * @param {Array<any>} data
         * @returns {com.clouddo.ui.table.Table}
         */
        private setTableData(data: Array<any>) : com.clouddo.ui.table.Table{
            this.tableVue.tableData = data;
            return this;
        }

        /**
         * 设置总记录数
         * @param {number} total
         * @returns {com.clouddo.ui.table.Table}
         */
        private setTotal(total: number) : com.clouddo.ui.table.Table {
            this.tableVue.page.total = total;
            return this;
        }

        /**
         * 批量删除
         * @param {Array<any>} rows
         * @returns {com.clouddo.ui.table.Table}
         */
        private batchDelete(rows : Array<any>) : com.clouddo.ui.table.Table {
            let tableObject = this;
            //将数据中keys传到后台执行删除
            let deleteDatas : Array<any> = [];
            if(this.keys && this.keys.length > 0) {
                for (let row of rows) {
                    let deleteData = {};
                    for(let key of this.keys) {
                        deleteData[key] = row[key];
                    }
                    deleteDatas.push(deleteData);
                }
            } else {
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
                layer.alert('删除时发生错误！', {icon: 0});
            }
            return this;
        }

        /**
         * 加载数据
         * @param success
         */
        private loadData(success : Function, queryParameters, ...parameters : Array<any>) : void {
            let $this =  this;
            $this.tableVue.loading = true
            AuthRestUtil.postAjax(this.queryUrl, (queryParameters ? queryParameters : {}), defaultSuccess, null, ...parameters);
            //默认的数据加载完成后函数
            function defaultSuccess(data, ...parameterSet) {
                $this.tableVue.loading = false
                //执行数据加载后事件
                if ($this.onDataLoadFunction) {
                    $this.onDataLoadFunction(data, ...parameterSet);
                }
                //处理树形table数据处理函数
                // if (pageObject.tableType = ElementTableImpl.TREE_TABLE) {
                //     data = pageObject.formatTreeData(data, ...parameterSet);
                // }
                //执行传入的success回调函数
                success(data, ...parameterSet);
            }
        }

    }
}