var ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;
var userTable;
$(document).ready(function () {
    userTable = new ElementTableImpl("system/sys/user/list", null, null, ElementTableImpl.NORMAL_TABLE, ["userId"], "人员");
    //设置按钮权限
    userTable.setButtonsWithPermission({
        editButton: "sys:user:edit",
        deleteButton: "sys:user:batchDelete",
        addButton: "sys:user:add",
        resetPwdButton: "sys:user:resetPwd"
    })
        //设置vue自定义过滤器
        .setCustomFilters({
        //格式化状态样式
        formatStatusClass: function (row) {
            if (row["status"] == "0") {
                return "label label-danger";
            }
            else if (row["status"] == "1") {
                return "label label-primary";
            }
        },
        //格式化状态值
        formatStatusValue: function (row) {
            if (row["status"] == "0") {
                return "禁用";
            }
            else if (row["status"] == "1") {
                return "正常";
            }
        }
    })
        .setCustomMethods({
        //重置密码方法
        handleResetPwd: function (index, row) {
            console.log(row);
        }
    })
        .createTable();
});
