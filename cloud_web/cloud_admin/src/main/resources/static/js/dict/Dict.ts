import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;

let dictTable
$(document).ready(function () {
    dictTable = new ElementTableImpl('system/dict/list', 'system/dict/delete', null,  ElementTableImpl.NORMAL_TABLE, ["dictCode"], "字典")
    dictTable.setButtonsWithPermission({
        editButton: "sys:dict:saveOrUpdate",
        deleteButton: "sys:dict:delete",
        addButton: "sys:dict:saveOrUpdate"
    })
        .createTable()

});