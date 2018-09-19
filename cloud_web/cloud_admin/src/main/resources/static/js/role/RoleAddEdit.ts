import AddEditPage = com.clouddo.ui.page.AddEditPage;
import RoleAddEdit = com.clouddo.ui.table.RoleAddEdit;


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
        .setCustomWatch({
            menuTreeData: (_new, old) => {
                console.log(_new, old)
            }
        })
        // vue挂在后函数
        .vueMounted(function () {
            //加载菜单数据
            pageModel.loadMenuList()
            // pageModel.loadCheckMenuIds()
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
                // 加载选中的节点
                $this.loadCheckMenuIds()
            }
        }

        /**
         * 获取角色拥有的菜单ID
         */
        public loadCheckMenuIds(): void {
            let $this = this
            let url = 'system/sys/role/listMenuId/' + roleId
            AuthRestUtil.postAjax(url, {roleId: roleId}, (result) => {
                // 受限于elementUI tree API，只能设置子节点
                // 遍历选中的叶子节点
                if (this.formVue.menuTreeData && this.formVue.menuTreeData.length > 0) {
                    let leafMenuIds: Array<string> = []
                    // 使用递归获取所有选中的叶子节点
                    chouseLeafMenu(this.formVue.menuTreeData, result, leafMenuIds)
                    $this.formVue.$refs.menuTree.setCheckedKeys(leafMenuIds)
                }

            }, null)

            function chouseLeafMenu(menuList: Array<any>, allChouseIds: Array<string>, leafMenuIds: Array<string>) {
                for (let menu of menuList) {
                    let children = menu['children']
                    if ((!children || children.length === 0) && allChouseIds.indexOf(menu['id']) > 0) {
                        leafMenuIds.push(menu['id'])
                    }
                    if (children && children.length > 0) {
                        chouseLeafMenu(children, allChouseIds, leafMenuIds)
                    }
                }
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

