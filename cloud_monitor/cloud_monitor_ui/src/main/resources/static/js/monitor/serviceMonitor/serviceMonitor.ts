import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;
import ServiceMonitor = com.cloudo.monitor.ui.page.ServiceMonitor;

let tableObject
$(document).ready(function () {
    tableObject = new ServiceMonitor()
    // todo 权限未设定
    tableObject.setButtonsWithPermission({
        editButton: "",
        deleteButton: "",
        addButton: "",
    })
        .createTable()
})

namespace com.cloudo.monitor.ui.page {
    export class ServiceMonitor extends ElementTableImpl {
        constructor () {
            super('monitor/cloudServiceMonitor/list', 'monitor/cloudServiceMonitor/delete', 'monitor/web/serviceMonitorAddEdit', ElementTableImpl.NORMAL_TABLE, ['serviceId'], '定时任务')
        }
    }
}

