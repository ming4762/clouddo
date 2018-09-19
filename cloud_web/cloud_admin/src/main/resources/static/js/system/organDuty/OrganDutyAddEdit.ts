import AddEditPage = com.clouddo.ui.page.AddEditPage;
import OrganDutyAddEdit = com.clouddo.ui.table.OrganDutyAddEdit;

let pageModel
declare let organId

$(document).ready(function () {
    pageModel = new OrganDutyAddEdit()
    pageModel.setAttributeConfigs([
        {
            name: "organName",
            required: true,
            message: "请输入名称",
            trigger: "blur"
        },
        {
            name: "shortName",
            required: false,
            message: "请输入短名称",
            trigger: "blur"
        },
        {
            name: "organType",
            required: true,
            message: "请输入类型",
            trigger: "change"
        },
        {
            name: "organLevel",
            required: true,
            message: "请输入级别",
            trigger: "change"
        },
        {
            name: "seq",
            required: true,
            trigger: "blur",
            validator: pageModel.validNumber
        },
        {
            name: "inUse",
            required: false,
            message: "启用",
            trigger: "blur"
        },
        {
            name: "icon",
            required: false,
            message: "图标",
            trigger: "blur"
        },
        {
            name: "duty",
            required: false,
            message: "职务",
            trigger: "blur"
        },
        {
            name: "remark",
            required: false,
            message: "备注",
            trigger: "blur"
        }
    ])
    if (organId) {
        pageModel.setIdent(AddEditPage.EDIT_IDENT)
            .setKeyData({organId: organId});
    }
    pageModel.init();
})

namespace com.clouddo.ui.table {
    import Validate = com.clouddo.ui.util.Validate;

    export class OrganDutyAddEdit extends AddEditPage {
        constructor () {
            super('system/sys/organDuty/update', "system/sys/organDuty/get")
        }

        /**
         * 验证数字
         * @param rule
         * @param value
         * @param callback
         */
        private validNumber (rule, value, callback) {
            if (value === null || value.trim() === '') {
                callback()
            } else if (!Validate.validatePositiveInteger(value)) {
                callback(new Error('请输入正整数'))
            } else {
                callback()
            }
        }
    }
}

