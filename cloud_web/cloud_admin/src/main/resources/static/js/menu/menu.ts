
let pageModel : any;
$(document).ready(function () {
    pageModel = new MenuPage();
    pageModel.init()
        .getTable()
        .setConfig("expandColumn", 1)
        .setParameter("sort", "order_num");
    pageModel.createTable();
});

namespace com.clouddo.ui.system {
    import BootstrapTreeTableImpl = com.clouddo.ui.table.BootstrapTreeTableImpl;
    import DefaultPage = com.clouddo.ui.page.DefaultPage;
    import Page = com.clouddo.ui.page.Page;

    //声明layer
    declare let layer: any;

    export class MenuPage extends DefaultPage{

        constructor() {
            super("system/sys/menu/list", "system/sys/menu/saveUpdate", null, DefaultPage.TREE_TABLE_TYPE);
            //设置key
            this.keys = ["menuId"];
            this.saveUpdateVueMethods = {
                //显示图标
                showIcons : function () {
                    layer.open({
                        type: 2,
                        title:'图标列表',
                        content: '/FontIcoList.html',
                        area: ['480px', '90%'],
                        success: function(layero, index){

                        }
                    });
                }
            }
        }

        public setIcon(icon : string) {
            this.saveUpdateVue.saveUpdateModel.icon = icon;
        }

        /**
         * 重写添加前的数据准备，处理上级目录
         * @param parameters
         */
        protected beforAdd(...parameters) {
            super.beforAdd(...parameters);
            this.saveUpdateVue.showModel.parentName = parameters[1];
            this.saveUpdateVue.saveUpdateModel.parentId = parameters[0];
        }

        //图标格式化方法
        public formatIcon(value : any, item : any, index : number) : any {
            return item.icon == null ? ''
                : '<i class="' + item.icon
                + ' fa-lg"></i>';
        }
        //类型格式化方法
        public formatType(value : any, item : any, index : number) : any {
            if (item.type === 0) {
                return '<span class="label label-primary">目录</span>';
            }
            if (item.type === 1) {
                return '<span class="label label-success">菜单</span>';
            }
            if (item.type === 2) {
                return '<span class="label label-warning">按钮</span>';
            }
        }

        //格式化操作
        public formatOperation = (value : any, item : any, index : number) => {

            var e = '<a class="btn btn-primary btn-sm '
                + (this.checkPermission("sys:menu:add") ? '' : 'hidden')
                + '" href="#" mce_href="#" title="编辑" v-on:click="updateMethod(\''
                + item.menuId
                + '\')"><i class="fa fa-edit"></i></a> ';
            var p = '<a class="btn btn-primary btn-sm '
                + (this.checkPermission("sys:user:edit") ? '' : 'hidden')
                + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                + item.menuId
                + '\')"><i class="fa fa-plus"></i></a> ';
            var d = '<a class="btn btn-warning btn-sm '
                + (this.checkPermission("sys:user:remove") ? '' : 'hidden')
                + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                + item.menuId
                + '\')"><i class="fa fa-remove"></i></a> ';
            return e + d + p;
        }
    }
}

import MenuPage = com.clouddo.ui.system.MenuPage;
import DefaultPage = com.clouddo.ui.page.DefaultPage;