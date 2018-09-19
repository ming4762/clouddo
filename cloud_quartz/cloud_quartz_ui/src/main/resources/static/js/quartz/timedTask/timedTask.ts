import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;
import TimedTask = com.cloudo.quartz.ui.page.TimedTask;

let timedTaskTable;
$(document).ready(function () {
    timedTaskTable = new TimedTask()
    // todo 权限未设定
    timedTaskTable.setButtonsWithPermission({
        editButton: "",
        deleteButton: "",
        addButton: "",
    })
        .setCustomMethods({
            // 打开关闭定时任务
            openCloseTask: function (row) {
                timedTaskTable.openCloseTask(row)
            }
        })
        .setCustomFilters({
            // 格式化是否启用
            formatEnableClass: function (value) {
                if (value) {
                    return "label label-success"
                } else {
                    return "label label-warning";
                }
            }
        })
        .createTable();
});

namespace com.cloudo.quartz.ui.page {
    declare let layer: any
    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;

    export class TimedTask extends ElementTableImpl{
        constructor() {
            super("monitor/cloudTimedTask/list", 'monitor/cloudTimedTask/delete', "quartz/web/timedTaskAddEdit", ElementTableImpl.NORMAL_TABLE, ['taskId'], '定时任务')
        }

        /**
         * 打开或关闭定时任务
         * @param row
         */
        public openCloseTask(row): void {
            let enable: boolean = row['enable']
            if (enable) {
                layer.confirm('您确定要激活该定时任务吗？', {
                    btn: ['确定','取消']
                },
                    function (index) {
                        restOpenClose(row)
                        layer.close(index)
                    },
                    function () {
                        row['enable'] = !enable
                    })
            } else {
                layer.confirm('您确定要暂停该定时任务吗？', {
                    btn: ['确定','取消']
                },
                    function (index) {
                        restOpenClose(row)
                        layer.close(index)
                    },
                    function () {
                        row['enable'] = !enable
                    })
            }

            function restOpenClose(row: any) {
                AuthRestUtil.postAjax('monitor/cloudTimedTask/openClose', row, null, null)
            }
        }
    }
}

