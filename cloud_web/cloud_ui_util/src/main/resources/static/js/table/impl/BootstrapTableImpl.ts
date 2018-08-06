/**
 * BootstrapTable实现
 */

namespace com.clouddo.ui.table {

    import RestUtil = com.clouddo.ui.util.RestUtil;
    import TableUtil = com.clouddo.ui.table.TableUtil;
    declare let $ : any;
    export class BootstrapTableImpl implements Table {

        private tableConfig : any = {};
        private elementId : any = "grid-table";
        //是否有复选框，默认有
        private hasCheckbox : boolean = true;
        //是否显示序号
        private showNumber : boolean = true;
        //请求数据的url
        private url : string;

        //引用该table的页面对象，主要用于回调列format方法
        private pageModel : any;

        /**
         * 发送到后台的数据
         * @type {{}}
         */
        private queryParameters : any = {};
        //构造方法
        constructor(url: string, pageModel : any, gridElementId ? : string) {
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
        public setCheckbox(hasCheckbox: boolean) : Table {
            this.hasCheckbox = hasCheckbox;
            return this;
        }

        /**
         * 是否显示行号
         * @param {boolean} showNumber
         */
        public setShowNumber(showNumber: boolean) {
            this.showNumber = showNumber;
            return this;
        }

        /**
         *  创建表格
         */
        public createTable(): void {
            //初始化columns
            this.initColumns();
            $("#" + this.elementId).bootstrapTable(this.tableConfig);
        }

        public setParameters(parameters: any): com.clouddo.ui.table.Table {
            this.queryParameters = $.extend({}, this.queryParameters, parameters);
            return this;
        }
        public setCode(code: string): com.clouddo.ui.table.Table {
            return this;
        }

        public setParentCode(parentCode: string): com.clouddo.ui.table.Table {
            return this;
        }

        public getSelectRows(): any {
            return undefined;
        }

        public clearTableData(): void {
        }

        /**
         * 重新加载数据
         */
        public reload(): void {
            $('#' + this.elementId).bootstrapTable('refresh', this.queryParameters);
        }

        public onTableComplate(_function: Function): void {
        }

        /**
         * 设置配置信息
         * @param {string} key 配置的key
         * @param value 配置的值
         */
        public setConfig(key: string, value: any) : com.clouddo.ui.table.Table {
            this.tableConfig[key] = value;
            return this;
        }

        /**
         * 设置参数
         * @param {string} key
         * @param value
         */
        public setParameter(key: string, value: any) : com.clouddo.ui.table.Table {
            this.queryParameters[key] = value;
            return this;
        }


        //初始化表格
        private initTable() : void {
            //设置默认参数
            this.setDefaultConfig();
            //设置后台地址
            this.setConfig("url", this.url);
        }
        //初始化列信息
        private initColumns() {
            let columns = TableUtil.analysisColumns(this.elementId, this.hasCheckbox, this.showNumber, this.pageModel);
            this.setConfig("columns", columns);
        }
        //设置默认配置
        private setDefaultConfig() : void {
            //设置请求头
            let headers = {};
            headers[RestUtil.TOKEN_KEY] = RestUtil.getToken();
            this.tableConfig = {
                //请求方式
                method : "post",
                //显示分页条
                pagination : true,
                //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置服务器数据地址（url）或者重写ajax方法。
                sidePagination : "server",
                //如果设置了分页，页面数据条数
                pageSize : 20,
                ajaxOptions : {
                    //设置请求头
                    headers: headers
                },
                //设置发送到后台的参数
                queryParams : function (params) {
                    return $.extend({}, this.queryParameters, params);
                },
                // height: 300,
                //隔行变色
                striped : true,
                //显示刷新按钮
                showRefresh : false,
                //解析数据
                responseHandler : function (result) {
                    console.log(result);
                    let data : any = {};
                    data["total"] = result.data.length;
                    data["rows"] = result.data;
                    return data;
                }
            }
        }

    }
}