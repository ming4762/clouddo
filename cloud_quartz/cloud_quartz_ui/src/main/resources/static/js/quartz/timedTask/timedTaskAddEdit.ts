import AddEditPage = com.clouddo.ui.page.AddEditPage;
import CronValidate = com.cloudo.quartz.ui.util.CronValidate;
import TimedTaskAddEdit = com.cloudo.quartz.ui.page.TimedTaskAddEdit;

let tableObject
declare let taskId;
$(document).ready(function () {
    tableObject = new TimedTaskAddEdit()
    tableObject.setAttributeConfigs([
        {
            name: "taskName",
            required: true,
            message: "请输入任务名称",
            trigger: "blur"
        },
        {
            name: "clazz",
            required: true,
            message: "请输入任务类",
            trigger: "blur"
        },
        {
            name: "cron",
            required: true,
            validator: tableObject.validateCron,
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
            message: "请输入备注",
            trigger: "blur"
        }
    ])
        .setCustomData({

        })
    // 判断页面是否为编辑页面
    if (taskId) {
        tableObject.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({taskId: taskId})
    }
    // 初始化页面
    tableObject.init()
})

namespace com.cloudo.quartz.ui.page {
    export class TimedTaskAddEdit extends AddEditPage {
        constructor() {
            super('monitor/cloudTimedTask/saveOrUpdate', 'monitor/cloudTimedTask/get')
        }

        /**
         * 验证cron表达式
         * @param rule
         * @param value
         * @param callback
         */
        public validateCron (rule, value, callback) {
            if (value === null || value.trim() === '') {
                callback(new Error('请输入cron表达式'))
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

