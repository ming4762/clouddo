namespace com.clouddo.ui.page {
    import Table = com.clouddo.ui.table.Table;

    export interface Page{
        /**
         * 初始化方法
         * @returns {com.clouddo.ui.page.Page}
         */
        init() : Page;

        /**
         * 获取表格对象
         * @returns {Table}
         */
        getTable() : Table;

        createTable() : Page;

        /**
         * 设置需要验证的domId集合
         * @param permissionDomIds
         */
        setPermissionDomIds(... permissionDomIds) : Page
    }
}