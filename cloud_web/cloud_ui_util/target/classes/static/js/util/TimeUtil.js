///<reference path="Validate.ts"/>
/**
 * 时间工具类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var util;
            (function (util) {
                var Validate = com.clouddo.ui.util.Validate;
                var TimeUtil = /** @class */ (function () {
                    function TimeUtil() {
                    }
                    /**
                     *
                     * @param {number} contrastTime 需要比对的时间
                     * @param {number} beContrastTime 被比对的时间
                     * @returns {string} 2分钟前、刚刚、1天前、一天后
                     */
                    TimeUtil.contrastTime = function (contrastTime, beContrastTime) {
                        var result = "";
                        if (!beContrastTime) {
                            beContrastTime = new Date().getTime();
                        }
                        //标识比对时间是否在被比对之前
                        var isBefore = contrastTime < beContrastTime;
                        var str = isBefore ? "前" : "后";
                        //获取时间差值
                        var difference = Math.abs(beContrastTime - contrastTime);
                        if (difference < 60 * 1000) {
                            //如果时间小于1分钟
                            if (isBefore) {
                                result = "刚刚";
                            }
                            else {
                                result = "马上";
                            }
                        }
                        else if (difference < 60 * 60 * 1000) {
                            //不超过1小时
                            result = parseInt((difference % (1000 * 60 * 60)) / (1000 * 60) + "") + "分钟" + str;
                        }
                        else if (difference < 24 * 60 * 60 * 1000) {
                            //不超过一天
                            result = parseInt((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + "") + "小时" + str;
                        }
                        else if (difference < 30 * 24 * 60 * 60 * 1000) {
                            //不超过一个月
                            result = parseInt(difference / (1000 * 60 * 60 * 24) + "") + "天" + str;
                        }
                        else if (difference < 365 * 24 * 60 * 60 * 1000) {
                            //不超过1年
                            result = parseInt(difference / (1000 * 60 * 60 * 24 * 30) + "") + "月" + str;
                        }
                        else {
                            result = parseInt(difference / (1000 * 60 * 60 * 24 * 365) + "") + "年" + str;
                        }
                        return result;
                    };
                    /**
                     * 将时间差转为时间
                     * @param {number} timeDif
                     * @returns {string}
                     */
                    TimeUtil.formatTimeDif = function (timeDif) {
                        // 计算年数
                        var year = Math.floor(timeDif / (365 * 24 * 60 * 60 * 1000));
                        // 计算月数
                        var monthTimeDif = timeDif % (365 * 24 * 60 * 60 * 1000);
                        var month = Math.floor(monthTimeDif / (30 * 24 * 3600 * 100));
                        // 计算天数
                        var dayTimeDif = monthTimeDif % (30 * 24 * 3600 * 1000);
                        var day = Math.floor(dayTimeDif / (24 * 3600 * 1000));
                        // 计算小时数
                        var hourTimeDif = dayTimeDif % (24 * 3600 * 1000);
                        var hour = Math.floor(hourTimeDif / (3600 * 1000));
                        // 计算分钟数
                        var miniteTimeDif = hourTimeDif % (3600 * 1000);
                        var minite = Math.floor(miniteTimeDif / (60 * 1000));
                        // 计算秒数
                        var secondTimeDif = miniteTimeDif % (60 * 1000);
                        var second = Math.floor(secondTimeDif / 1000);
                        var result = '';
                        if (year > 0) {
                            result += (year + '年');
                        }
                        if (month > 0) {
                            result += (month + '月');
                        }
                        if (day > 0) {
                            result += (day + '天');
                        }
                        if (hour > 0) {
                            result += (hour + '小时');
                        }
                        if (minite > 0) {
                            result += (minite + '分钟');
                        }
                        if (second > 0) {
                            result += (second + '秒');
                        }
                        return result;
                    };
                    /**
                     * 格式化时间
                     * @param {number} time
                     */
                    TimeUtil.formatTime = function (time) {
                        if (Validate.validateNull(time)) {
                            return "";
                        }
                        var date = new Date(time);
                        var year = date.getFullYear();
                        var month = date.getMonth() + 1;
                        var day = date.getDate();
                        var hour = date.getHours();
                        var minute = date.getMinutes();
                        var second = date.getSeconds();
                        return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day) + " " + (hour < 10 ? ("0" + hour) : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
                    };
                    /**
                     * 格式化日期
                     * @param {number} time
                     * @returns {string}
                     */
                    TimeUtil.formatDate = function (time) {
                        if (Validate.validateNull(time)) {
                            return "";
                        }
                        var date = new Date(time);
                        var year = date.getFullYear();
                        var month = date.getMonth() + 1;
                        var day = date.getDate();
                        return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
                    };
                    return TimeUtil;
                }());
                util.TimeUtil = TimeUtil;
            })(util = ui.util || (ui.util = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
