/**
 * 排序工具类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var util;
            (function (util) {
                var SortUtil = /** @class */ (function () {
                    function SortUtil() {
                    }
                    /**
                     * 排序算法
                     * @author zhongming
                     * TODO 待完善 降序
                     * TODO 待完善 其他排序算法
                     * @param array 要排序的数组
                     * @param isDesc 是否是降序
                     * @param arithmetic 排序算法
                     */
                    SortUtil.sort = function (array, isDesc, arithmetic) {
                        var result = [];
                        switch (arithmetic) {
                            case '':
                                break;
                            default:
                                result = this.shellSort(array);
                        }
                        return result;
                    };
                    /**
                     * 希尔排序
                     * @author zhongming
                     * @param array 要排序的数组
                     */
                    SortUtil.shellSort = function (array) {
                        var gap = Math.floor(array.length / 2);
                        while (gap >= 1) {
                            for (var i = gap; i < array.length; i++) {
                                var temp = array[i];
                                var j = void 0;
                                for (j = i - gap; j >= 0 && temp < array[j]; j -= gap) {
                                    array[j + gap] = array[j];
                                }
                                array[j + gap] = temp;
                            }
                            gap = Math.floor(gap / 2);
                        }
                        return array;
                    };
                    SortUtil.SORT_ARITHMETIC = {
                        SHELL: 'shell'
                    };
                    return SortUtil;
                }());
                util.SortUtil = SortUtil;
            })(util = ui.util || (ui.util = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
