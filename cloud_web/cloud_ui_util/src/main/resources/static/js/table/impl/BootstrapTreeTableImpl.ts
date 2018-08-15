

namespace com.clouddo.ui.table {

    import RestUtil = com.clouddo.ui.util.RestUtil;
    import TableUtil = com.clouddo.ui.table.TableUtil;
    declare let $ : any;
    export class BootstrapTreeTableImpl implements TreeTable {

        private tableConfig : any = {};
        private elementId : any = "grid-table";

        //是否有复选框，默认有
        private hasCheckbox : boolean = false;
        //是否显示序号
        private showNumber : boolean = false;
        //请求数据的url
        private url : string;

        //引用该table的页面对象，主要用于回调列format方法
        private pageModel : any;

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
            this.initTable();
        }

        /**
         * 设置code
         * @param {string} code
         * @returns {com.clouddo.ui.table.Table}
         */
        public setCode(code : string) : Table {
            this.tableConfig["code"] = code;
            return this;
        }

        public setParentCode(parentCode : string) : Table {
            this.tableConfig["parentCode"] = parentCode;
            return this;
        }


        /**
         *  创建表格
         */
        public createTable(): void {
            //初始化columns
            this.initColumns();
            $("#" + this.elementId).bootstrapTreeTable(this.tableConfig);
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
        public setShowNumber(showNumber: boolean) : Table {
            this.showNumber = showNumber;
            return this;
        }

        /**
         * 设置配置
         * @param {string} key
         * @param value
         */
        public setConfig(key: string, value: any): Table {
            this.tableConfig[key] = value;
            return this;
        }

        /**
         * 设置参数
         * @param {string} key
         * @param value
         */
        public setParameter(key: string, value: any): Table {
            this.tableConfig.ajaxParams[key] = value;
            return this;
        }
        /**
         * 在表格 body 渲染完成后触发
         * @param {Function} onPostBodyFunction
         * @returns {com.clouddo.ui.table.Table}
         */
        public onPostBody(onPostBodyFunction: Function): com.clouddo.ui.table.Table {
            return this;
        }

        /**
         * 设置参数
         * @param parameters
         * @returns {com.clouddo.ui.table.Table}
         */
        public setParameters(parameters: any) : Table {
            this.tableConfig.ajaxParams = $.extend({}, this.tableConfig.ajaxParams, parameters);
            return this;
        }

        getSelectRows(): any {
            return undefined;
        }

        clearTableData(): void {
        }

        reload(): void {
        }

        onTableComplate(_function: Function): void {
        }



        //表格初始化
        private initTable() : void {
            //设置默认参数
            this.setDefaultConfig();
            //设置后台地址
            this.setConfig("url", this.url);
        }

        //设置默认配置
        private setDefaultConfig() : void {
            //设置请求头
            let headers = {};
            headers[RestUtil.TOKEN_KEY] = RestUtil.getToken();
            this.tableConfig = {
                //请求方式
                type : "POST",
                contentType : 'application/json; charset=UTF-8',
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                ajaxParams : {}, //传送到后台的数据
                headers : headers, //设置请求头
                dataFormat : function (data) {
                    //待完善
                    return data.data;
                }
            }
        }

        //初始化列信息
        private initColumns() {
            //获取dom信息
            this.setConfig("columns", TableUtil.analysisColumns(this.elementId, this.hasCheckbox, this.showNumber, this.pageModel));
        }
    }
}