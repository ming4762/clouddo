/**
 * 表格抽象
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var table;
            (function (table) {
                var TableEnum;
                (function (TableEnum) {
                    //数据
                    TableEnum["DATA_FIELD"] = "table-field";
                    //表格宽度
                    TableEnum["DATA_WIDTH"] = "table-width";
                    //排序列
                    TableEnum["DATA_SORTABLE"] = "table-sortable";
                    //列格式化工具
                    TableEnum["DATA_FORMAT"] = "table-format";
                    //显示位置 center居中
                    TableEnum["DATA_ALIGN"] = "table-align";
                    //列事件
                    TableEnum["DATA_ENENTS"] = "table-events";
                })(TableEnum = table.TableEnum || (table.TableEnum = {}));
                var TableUtil = /** @class */ (function () {
                    function TableUtil() {
                    }
                    /**
                     * 解析列信息
                     * @returns {Array<any>}
                     */
                    TableUtil.analysisColumns = function (elementId, hasCheckbox, showNumber, pageModel) {
                        var columns = new Array();
                        //获取dom信息
                        var thList = $("#" + elementId + " tr th");
                        //判断是否有复选框
                        if (hasCheckbox) {
                            columns.push({
                                checkbox: true,
                                width: "40px"
                            });
                        }
                        //遍历th
                        for (var i = 0; i < thList.length; i++) {
                            //列信息
                            var columnModel = {};
                            var $th = thList[i];
                            //设置列标题
                            columnModel["title"] = $th.innerHTML;
                            //设置列数据
                            if ($($th).attr(TableEnum.DATA_FIELD)) {
                                columnModel["field"] = $($th).attr(TableEnum.DATA_FIELD);
                            }
                            //设置表格宽度
                            if ($($th).attr(TableEnum.DATA_WIDTH)) {
                                columnModel["width"] = $($th).attr(TableEnum.DATA_WIDTH);
                            }
                            //设置显示位置信息
                            if ($($th).attr(TableEnum.DATA_ALIGN)) {
                                columnModel["align"] = $($th).attr(TableEnum.DATA_ALIGN);
                            }
                            //获取排序信息
                            var sortableStr = $($th).attr("data-sortable");
                            var sortable = (sortableStr == "true");
                            columnModel["sortable"] = sortable;
                            //设置数据格式化
                            if ($($th).attr(TableEnum.DATA_FORMAT)) {
                                var methodName = $($th).attr(TableEnum.DATA_FORMAT);
                                //先从页面获取元素
                                if (pageModel && pageModel[methodName]) {
                                    columnModel["formatter"] = pageModel[methodName];
                                }
                                else {
                                    columnModel["formatter"] = eval(methodName);
                                }
                            }
                            //设置列事件
                            if ($($th).attr(TableEnum.DATA_ENENTS)) {
                                var eventsStr = $($th).attr(TableEnum.DATA_ENENTS);
                                columnModel["events"] = eventsStr;
                            }
                            columns.push(columnModel);
                        }
                        //判断是否显示行号
                        if (showNumber && columns.length > 1) {
                            var start = 0;
                            if (hasCheckbox) {
                                start = 1;
                            }
                            columns.splice(start, 0, {
                                title: '序号',
                                field: columns[1]["field"],
                                width: "30px",
                                formatter: function (value, row, index) {
                                    return index + 1;
                                }
                            });
                        }
                        return columns;
                    };
                    return TableUtil;
                }());
                table.TableUtil = TableUtil;
            })(table = ui.table || (ui.table = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
