import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;

let table;

$(document).ready(function() {

    table = new ElementTableImpl("system/sys/role/list", null, "web/system/roleAddEdit", ElementTableImpl.NORMAL_TABLE, ["roleId"], "角色");
    table.setButtonsWithPermission({
        editButton: "sys:role:edit",
        deleteButton: "sys:role:remove",
        addButton: "sys:role:add"
    })
        //创建表格
        .createTable();

});