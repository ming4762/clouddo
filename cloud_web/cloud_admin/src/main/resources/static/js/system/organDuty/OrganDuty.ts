import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;

let organDutyTable

$(document).ready(function () {
    organDutyTable = new ElementTableImpl('system/sys/organDuty/list', 'system/sys/organDuty/delete', 'web/system/organDutyAddEdit', ElementTableImpl.NORMAL_TABLE, ['organId'], '组织机构/职务')
    // 设置按钮权限
    organDutyTable.setButtonsWithPermission({
        editButton: '',
        deleteButton: '',
        addButton: ''
    }).createTable()
})