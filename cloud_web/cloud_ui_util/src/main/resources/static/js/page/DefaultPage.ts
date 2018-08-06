namespace com.clouddo.ui.page {
    //声明vue
    import RestUtil = com.clouddo.ui.util.RestUtil;
    declare let Vue : any;
    //声明弹出层工具
    declare let layer : any;
    //声明jquery
    declare let $ : any;

    import Auth = com.clouddo.ui.auth.Auth;
    import BootstrapTableImpl = com.clouddo.ui.table.BootstrapTableImpl;
    import Table = com.clouddo.ui.table.Table;
    import BootstrapTreeTableImpl = com.clouddo.ui.table.BootstrapTreeTableImpl;

    /**
     * 默认页面实体
     */
    export class DefaultPage extends Auth implements Page{
        public static TREE_TABLE_TYPE = "tree";

        public static SAVE_IDENT = "save";

        public static UPDATE_IDENT = "update";

        //请求数据url
        private getUrl : string;
        //更新URL
        private saveUpdateUrl : string;
        //删除URL
        private deleteUrl : string;
        //表格对象
        private table : Table;
        //需要权限验证domIds
        private permissionDomIds : Array<string> = ['operationDiv'];

        //搜索vue
        protected searchVue : any;
        //添加更新vue
        protected saveUpdateVue : any;
        //vue方法，有些页面需要传入自定义方法
        protected saveUpdateVueMethods : any;
        //操作vue
        protected operationVue : any;
        //页面实体的key
        protected keys : Array<string>;

        private tableType : string = "normal";

        constructor(getUrl : string, saveUpdateUrl : string, deleteUrl : string, tableType ? : string) {
            super();
            if (tableType) {
                this.tableType = tableType;
            }
            this.getUrl = getUrl;
            this.deleteUrl = deleteUrl;
            this.saveUpdateUrl = saveUpdateUrl;
        }

        /**
         * 初始化
         * @returns {com.clouddo.ui.page.Page}
         */
        public init() : Page {
            //验证按钮权限
            this.authPermission(this.permissionDomIds);
            //初始化vue
            this.initVue();
            //创建表格
            if(this.tableType == DefaultPage.TREE_TABLE_TYPE) {
                this.table = new BootstrapTreeTableImpl(this.getUrl, this);
            } else {
                this.table = new BootstrapTableImpl(this.getUrl, this);
            }
            return this;
        }

        /**
         * 设置需要验证的domId集合
         * @param permissionDomIds
         */
        public setPermissionDomIds(... permissionDomIds) : Page {
            this.permissionDomIds = permissionDomIds;
            return this;
        }

        /**
         * 创建表格方法
         */
        public createTable() : Page {
            this.table.createTable();
            return this;
        }

        /**
         * 获取表格实体
         * @returns {com.clouddo.ui.table.Table}
         */
        public getTable() : Table {
            return this.table;
        }

        /**
         * 查询方法
         */
        protected query() : void {
            this.getTable()
                .setParameters(this.searchVue.searchModel)
                .reload();

        }

        //添加修改弹窗前操作
        protected beforAdd(...parameters) {
            //清空数据
            // this.saveUpdateVue.saveUpdateModel = {};
            //显示窗口
            this.showSaveUpdateWindow();
        }

        /**
         * 添加前操作
         * @param keys
         */
        protected beforUpdate(...keys) {
            //设置参数key
            let parameters = {};
            if (this.keys) {
                for(let i=0; i<this.keys.length; i++) {
                    parameters[this.keys[i]] = keys[i];
                }
            }
            //查询数据
            RestUtil.postAjax(this.getUrl, parameters, this.beforUpdateSuccess, null);
        }

        /**
         * 更新操作获取数据成功
         * @param data
         */
        protected beforUpdateSuccess(data) {
            if (data && data.length > 0) {
                this.saveUpdateVue.saveUpdateModel = data[0];
                //显示窗口
                this.showSaveUpdateWindow();
            }
        }

        /**
         * 保存添加操作
         */
        protected saveUpdate(index) : void {
            let pageModel = this;
            RestUtil.postAjax(this.saveUpdateUrl, this.saveUpdateVue.saveUpdateModel, success, error);

            function success(data) {
                layer.close(index);
                //重新加载表格
                pageModel.table.reload();
            }

            function error(data) {
                layer.alert('添加修改发生错误', {icon: 2});
            }
        }

        /**
         * 显示添加修改窗口
         */
        protected showSaveUpdateWindow() {
            let pageModel = this;
            layer.open({
                type: 1,
                content: $("#saveUpdateDiv"),
                closeBtn: 0, //不显示关闭按钮
                shadeClose: false, //开启遮罩关闭、
                area: ['50%', "60%"],
                btn: ['确认', '取消']
                ,yes: function(index, layero){
                    pageModel.saveUpdate(index);
                    // saveUpdate(index, _this);
                }
                ,btn2: function(index, layero){
                    //按钮【按钮二】的回调
                    // _this.closeLayer(index, "addUpdateDiv");
                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        }

        //初始化vue
        private initVue() : void {
            let pageModel = this;
            this.searchVue = new Vue({
                el: '#searchDiv',
                data: {
                    searchModel: {}
                },
                methods : {
                    //查询方法
                    query : function (event) {
                        pageModel.query();
                    }
                }
            });

            //插入语更新VUE
            this.saveUpdateVue = new Vue({
                el: "#saveUpdateDiv",
                data : {
                    //实体类
                    saveUpdateModel: {

                    },
                    //显示实体
                    showModel: {}
                },
                methods: $.extend({}, {

                }, this.saveUpdateVueMethods),
                watch: {
                    saveUpdateModel: function (newValue, oldValue, a) {
                        console.log(newValue);
                        console.log(oldValue);
                    }
                }
            });
            //操作vue
            this.operationVue = new Vue({
                el: "#operationDiv",
                data: {

                },
                methods: {
                    //添加操作
                    addMethod: function (...data) {
                        pageModel.beforAdd(...data);
                    },
                    //更新操作
                    updateMethod: function (...keys) {
                        pageModel.beforUpdate(...keys);
                    }
                }
            });
        }
    }
}