import AddEditPage = com.clouddo.ui.page.AddEditPage;

let pageModel;

declare let menuId;
declare let layer;
$(document).ready(function () {
    pageModel = new MenuAddEdit()
        .setAttributeConfigs([
            {
                name: "type",
                required: true,
                message: "请选择类型",
                trigger: "change"
            },
            {
                name: "name",
                required: true,
                message: "请输入菜单名称",
                trigger: "blur"
            },
            {
                name: "url",
                required: false,
                message: "请输入链接地址",
                trigger: "blur"
            },
            {
                name: "perms",
                required: false,
                message: "请输入权限标识",
                trigger: "blur"
            },
            {
                name: "orderNum",
                required: true,
                message: "请输入序号",
                trigger: "blur"
            },
            {
                name: "icon",
                required: false,
                message: "序号",
                trigger: "blur"
            }
        ])
        //设置vue自定义数据
        .setCustomData({
            //用户根目录显示
            showData : {
                parentName : ""
            }
        })
        //这是自定义方法
        .setCustomMethods({
            //显示图标
            showIcons: function () {
                layer.open({
                    type: 2,
                    title:'图标列表',
                    content: '/FontIcoList.html',
                    area: ['480px', '90%'],
                    success: function(layero, index){

                    }
                });
            }
        });
    //判断是否为编辑页面
    if (menuId) {
        pageModel.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({menuId: menuId});
    }
    //初始化页面
    pageModel.init();
    //加载父节点数据
    pageModel.loadParentData();
});


namespace com.clouddo.ui.page {

    import RestUtil = com.clouddo.ui.util.RestUtil;

    //声明parentId，在页面设置
    declare let parentId : string;

    export class MenuAddEdit extends AddEditPage {
        //构造函数
        constructor() {
            super("system/sys/menu/saveUpdate", "system/sys/menu/get");
        }

        /**
         * 设置i图标
         * @param {string} icon
         */
        public setIcon(icon : string) {
            this.formVue.formData.icon = icon;
        }

        /**
         * 加载父节点数据
         */
        public loadParentData() {
            let _object = this;
            this.formVue.formData.parentId = parentId;
            if (parentId == "0") {
                this.formVue.showData.parentName = "根目录";
            } else {
                RestUtil.postAjax("system/sys/menu/get", {menuId : parentId}, success, null)
            }

            function success(data) {
                _object.formVue.showData.parentName = data.name;
            }
        }
    }
}

import MenuAddEdit = com.clouddo.ui.page.MenuAddEdit;