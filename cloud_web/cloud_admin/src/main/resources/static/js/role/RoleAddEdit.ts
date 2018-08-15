import AddEditPage = com.clouddo.ui.page.AddEditPage;

let pageModel;
declare let roleId;
$(document).ready(function() {
    pageModel = new AddEditPage(null, "system/sys/role/get")
        .setAttributeConfigs([
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
        ]);
    if(roleId) {
        pageModel.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({roleId: roleId});
    }
    pageModel.init();
});

namespace com.clouddo.ui.table {


}
