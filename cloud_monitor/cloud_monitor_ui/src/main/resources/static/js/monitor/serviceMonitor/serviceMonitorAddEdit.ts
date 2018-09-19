import AddEditPage = com.clouddo.ui.page.AddEditPage;
import CronValidate = com.cloudo.quartz.ui.util.CronValidate;
import ServiceMonitorAddEdit = com.cloudo.monitor.ui.page.ServiceMonitorAddEdit;

let serviceMonitroAddEdit
declare let serviceId
$(document).ready(function () {
    serviceMonitroAddEdit = new ServiceMonitorAddEdit()
    serviceMonitroAddEdit.setAttributeConfigs([
        {
            name: "serviceName",
            required: true,
            message: "请输入服务名称",
            trigger: "blur"
        },
        {
            name: "type",
            required: true,
            message: "请输入服务类型",
            trigger: "change"
        },
        {
            name: "address",
            required: false,
            trigger: "blur"
        },
        {
            name: "cron",
            required: false,
            validator: serviceMonitroAddEdit.validateCron,
            trigger: "blur"
        },
        {
            name: "enable",
            required: true,
            message: "请选择是否启用",
            trigger: "change"
        },
        {
            name: "remark",
            required: false,
            trigger: "blur"
        }
    ]).setCustomData({
        // 服务类型
        serviceMonitorType: [
            {value: 'SpringCloud', label: 'SpringCloud'},
            {value: 'SpringBoot', label: 'SpringBoot'},
            {value: 'interface', label: '实现接口'},
            {value: 'normal', label: '普通服务'}
        ]
    })

    if (serviceId) {
        serviceMonitroAddEdit.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({serviceId: serviceId})
    }
    serviceMonitroAddEdit.init()
})

namespace com.cloudo.monitor.ui.page {
    export class ServiceMonitorAddEdit extends AddEditPage {
        constructor() {
            super('monitor/cloudServiceMonitor/saveOrUpdate', 'monitor/cloudServiceMonitor/get')
        }

        /**
         * 验证cron表达式
         * @param rule
         * @param value
         * @param callback
         */
        public validateCron (rule, value, callback) {
            if (value === null || value.trim() === '') {
                callback()
            } else {
                let message = CronValidate.cronValidate(value.trim())
                if (message === true) {
                    callback()
                } else {
                    callback(new Error(message))
                }
            }
        }
    }
}

