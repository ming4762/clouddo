import AddEditPage = com.clouddo.ui.page.AddEditPage;


let pageModel;
declare let roleId;
$(document).ready(function() {
    pageModel = new RoleAddEdit()
    pageModel.setAttributeConfigs([
            {
                name: "roleName",
                required: true,
                message: "请输入角色名",
                trigger: "blur"
            },
            {
                name: "remark",
                required: false,
                message: "请输入备注",
                trigger: "blur"
            },
            {
                name: "pers",
                required: false,
                message: "请选择角色",
                trigger: "blur"
            }
        ])
        .addCustomData({
            // 设置菜单树形结构props
            menuTreeProps: {
                label: 'text',

            },
            // 菜单树形结构数据
            menuTreeData: [],
            //默认选中的key
            checkMenuKeys: [],
            defaultExpandedKeys: ['-1']
        })
        //添加自定义方法
        .addCustomMethods({
        })
        // vue挂在后函数
        .vueMounted(function () {
            //加载菜单数据
            pageModel.loadMenuList()
            pageModel.loadCheckMenuIds()
        });
    if(roleId) {
        pageModel.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({roleId: roleId});
    }
    pageModel.init();
});

namespace com.clouddo.ui.table {

    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;

    export class RoleAddEdit extends AddEditPage {
        //构造函数
        constructor () {
            super('system/sys/role/update', "system/sys/role/get")
        }

        /**
         * 加载下级菜单
         * @param node
         * @param resolve
         */
        public loadMenuList(): void {
            let $this = this
            AuthRestUtil.postAjax('system/sys/menu/tree', {}, success, null)
            function success(result) {
                $this.formVue.menuTreeData = [result]
            }
        }

        /**
         * 获取角色拥有的菜单ID
         */
        public loadCheckMenuIds(): void {
            let $this = this
            let url = 'system/sys/role/listMenuId/' + roleId
            AuthRestUtil.postAjax(url, {roleId: roleId}, success, null)
            function success(result) {
                $this.formVue.checkMenuKeys = result
            }
        }

        /**
         * 获取选中的菜单
         */
        public getCheckMenuIds(): Array<string> {
            let menuList: Array<any> = this.formVue.$refs.menuTree.getCheckedNodes(false, true);
            let menuIdList: Array<string> = []
            for (let menu of menuList) {
                if (menu['id'] === '-1') {
                    continue
                }
                menuIdList.push(menu['id'])
            }
            return menuIdList;
        }

        /**
         * 提交数据
         * @param data
         */
        public submitData(data) {
            console.log(data)
            // 获取选中的节点的Id
            data['menuIds'] = this.getCheckMenuIds();
            super.submitData(data)
        }
    }
}

import RoleAddEdit = com.clouddo.ui.table.RoleAddEdit;
