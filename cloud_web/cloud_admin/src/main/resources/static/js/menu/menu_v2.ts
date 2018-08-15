import ElementTableImpl = com.clouddo.ui.table.ElementTableImpl;

let tableObject;
$(document).ready(function() {

    tableObject = new ElementTableImpl("system/sys/menu/list", "system/sys/menu/batchDelete", "web/system/menuAddEdit", ElementTableImpl.TREE_TABLE, ["menuId"],"菜单");
    tableObject.setTreeCode("menuId", "parentId")
        .setButtonsWithPermission({
            editButton: "sys:user:edit",
            deleteButton: "sys:user:remove",
            addButton: "sys:menu:add"
         })
        //设置初始化参数0
        .setParameters({
            parentId: "0"
        })
        //设置VUE自定义方法
        .setCustomMethods({
        })
        //树形结构不分页
        .setIsPaging(false)
        //设置edit URL格式化方法
        .formatEditUrl(function (row, defaultUrl) {
            if (row) {
                return defaultUrl + "&parentId=" + row["parentId"];
            } else {
                return defaultUrl;
            }
        })
        //设置add URL格式化方法
        .formatAddUrl(function (row, defaultUrl) {
            if(row) {
                return defaultUrl + "?parentId=" + row["menuId"];
            } else {
                return defaultUrl + "?parentId=0";
            }
        })
        //设置vue自定义filter
        .setCustomFilters({
            //格式化图标
            formatIcon : function (row) {
                if (row["icon"]) {
                    return row["icon"] + " fa-lg";
                } else {
                    return "";
                }
            },
            //格式化类型
            formatTypeClass: function (type) {
                if (type == 0) {
                    return "label label-primary";
                } else if (type == 1) {
                    return "label label-success"
                } else if (type == 2) {
                    return "label label-warning";
                }
            },
            formatTypeName: function (type) {
                if (type == 0) {
                    return "目录";
                } else if (type == 1) {
                    return "菜单"
                } else if (type == 2) {
                    return "按钮";
                }
            }
        })
        //创建表格
        .createTable();

});