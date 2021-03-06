/**
 * 表格抽象
 */
namespace com.clouddo.ui.table {
    declare let $ : any;
    export enum TableEnum {
        //数据
        DATA_FIELD = "table-field",
        //表格宽度
        DATA_WIDTH = "table-width",
        //排序列
        DATA_SORTABLE = "table-sortable",
        //列格式化工具
        DATA_FORMAT = "table-format",

        //显示位置 center居中
        DATA_ALIGN = "table-align",
        //列事件
        DATA_ENENTS = "table-events"
    }

    export class TableUtil{
        /**
         * 解析列信息
         * @returns {Array<any>}
         */
        public static analysisColumns(elementId : string, hasCheckbox : boolean, showNumber : boolean, pageModel ? : any) : Array<any> {
            let columns = new Array();
            //获取dom信息
            let thList = $("#" + elementId + " tr th");

            //判断是否有复选框
            if(hasCheckbox) {
                columns.push({
                    checkbox : true,
                    width: "40px"
                })
            }
            //遍历th
            for(let i=0; i<thList.length; i++) {
                //列信息
                let columnModel = {};
                let $th = thList[i];
                //设置列标题
                columnModel["title"] = $th.innerHTML;
                //设置列数据
                if($($th).attr(TableEnum.DATA_FIELD)) {
                    columnModel["field"] = $($th).attr(TableEnum.DATA_FIELD);
                }
                //设置表格宽度
                if($($th).attr(TableEnum.DATA_WIDTH)) {
                    columnModel["width"] = $($th).attr(TableEnum.DATA_WIDTH);
                }
                //设置显示位置信息
                if($($th).attr(TableEnum.DATA_ALIGN)) {
                    columnModel["align"] = $($th).attr(TableEnum.DATA_ALIGN);
                }
                //获取排序信息
                let sortableStr = $($th).attr("data-sortable");
                let sortable = (sortableStr == "true");
                columnModel["sortable"] = sortable;
                //设置数据格式化
                if ($($th).attr(TableEnum.DATA_FORMAT)) {
                    let methodName = $($th).attr(TableEnum.DATA_FORMAT);
                    //先从页面获取元素
                    if (pageModel && pageModel[methodName]) {
                        columnModel["formatter"] =  pageModel[methodName];
                    } else {
                        columnModel["formatter"] = eval(methodName);
                    }
                }
                //设置列事件
                if ($($th).attr(TableEnum.DATA_ENENTS)) {
                    let eventsStr = $($th).attr(TableEnum.DATA_ENENTS);
                    columnModel["events"] = eventsStr;
                }
                columns.push(columnModel);
            }

            //判断是否显示行号
            if (showNumber && columns.length > 1) {
                let start : number = 0;
                if (hasCheckbox) {
                    start = 1;
                }
                columns.splice(start, 0, {
                    title: '序号',
                    field: columns[1]["field"],
                    width: "30px",
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                });
            }
            return columns;
        }
    }

    export interface Table {

        /**
         *  创建表格
         */
        createTable() : void;

        /**
         * 设置是否分页
         * @param {boolean} isPaging
         * @returns {com.clouddo.ui.table.Table}
         */
        setIsPaging(isPaging : boolean) : Table;

        /**
         * 格式化add URL
         * @param {Function} formatAddUrlFunction
         * @returns {com.clouddo.ui.table.Table}
         */
        formatAddUrl(formatAddUrlFunction : Function) : Table;

        /**
         * 格式化edit url
         * @param {Function} formatEditUrlFunction
         * @returns {com.clouddo.ui.table.Table}
         */
        formatEditUrl(formatEditUrlFunction : Function) : Table;

        /**
         * 设置 table的key
         * @param {Array<string>} keys
         * @returns {com.clouddo.ui.table.Table}
         */
        setKeys(keys : Array<string>) : Table;

        /**
         * 添加操作
         * @param index
         * @param row
         */
        addMethod(index, row);

        /**
         * 删除操作
         * @param index
         * @param row
         */
        deleteMethod(index, row);

        /**
         * 编辑操作
         * @param index
         * @param row
         */
        editMethod(index, row);

        /**
         * 设置是否显示付现款
         * @param {boolean} hasCheckbox
         */
        setCheckbox(hasCheckbox : boolean) : Table;

        /**
         * 是否显示行号
         * @param {boolean} showNumber
         */
        setShowNumber(showNumber : boolean) : Table;

        setCode(code : string) : Table;

        setParentCode(parentCode : string) : Table;

        /**
         * 获取选中行
         * @returns {any}
         */
        getSelectRows() : any;

        /**
         * 数据加载完成后事件
         * @param {Function} onDataLoadFunction
         */
        onDataLoadComplate(onDataLoadFunction : Function) : Table;

        /**
         * 清空表格数据
         */
        clearTableData() : void;

        /**
         * 重新加载数据
         */
        reload() : void;

        /**
         * 表格加载完成后执行
         * @param {Function} _function
         */
        onTableComplate(_function : Function) : void;

        /**
         * 设置配置
         * @param {string} key
         * @param value
         */
        setConfig(key: string, value : any) : Table;

        /**
         * 设置参数
         * @param {string} key
         * @param value
         */
        setParameter(key: string, value : any) : Table;

        /**
         * 设置参数
         * @param parameters
         */
        setParameters(parameters : any) : Table;

        /**
         * 在表格 body 渲染完成后触发
         * @param {Function} onPostBodyFunction
         */
        onPostBody(onPostBodyFunction : Function) : Table;
    }
}