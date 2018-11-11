var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var util;
            (function (util) {
                /**
                 * 验证工具类
                 */
                var Validate = /** @class */ (function () {
                    function Validate() {
                    }
                    /**
                     * 验证邮箱
                     * @param {string} email 邮箱
                     * @returns {boolean} 验证结果
                     */
                    Validate.validateEmail = function (email) {
                        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                        return re.test(email);
                    };
                    /**
                     * 验证是否为小写字母
                     * @param {string} value
                     * @returns {boolean}
                     */
                    Validate.validateLowerCase = function (value) {
                        var reg = /^[a-z]+$/;
                        return reg.test(value);
                    };
                    /**
                     * 验证是否为大写字母
                     * @param {string} value
                     * @returns {boolean}
                     */
                    Validate.validateUpperCase = function (value) {
                        var reg = /^[A-Z]+$/;
                        return reg.test(value);
                    };
                    /**
                     * 验证是否为null
                     * @param value 验证的值
                     * @returns {boolean} 结果
                     */
                    Validate.validateNull = function (value) {
                        if (typeof value == 'boolean') {
                            return false;
                        }
                        if (value instanceof Array) {
                            if (value.length == 0)
                                return true;
                        }
                        else if (value instanceof Object) {
                            if (JSON.stringify(value) === '{}')
                                return true;
                        }
                        else {
                            if (value == 'null' || value == null || value == 'undefined' || value == undefined || value == '')
                                return true;
                            return false;
                        }
                        return false;
                    };
                    /**
                     * 验证URL
                     * @param {string} url url
                     * @returns {boolean} 验证结果
                     */
                    Validate.validateURL = function (url) {
                        var urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
                        return urlregex.test(url);
                    };
                    /**
                     * 验证是否为手机号码
                     * @param {string} mobile
                     * @returns {boolean}
                     */
                    Validate.validateMobile = function (mobile) {
                        var list = [];
                        var result = true;
                        var msg = '';
                        var isPhone = /^0\d{2,3}-?\d{7,8}$/;
                        //增加134 减少|1349[0-9]{7}，增加181,增加145，增加17[678]
                        var isMob = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[012356789][0-9]{8}|18[012356789][0-9]{8}|14[57][0-9]{8}|17[3678][0-9]{8})$/;
                        if (!this.validateNull(mobile)) {
                            if (mobile.length == 11) {
                                if (isPhone.test(mobile)) {
                                    msg = '手机号码格式不正确';
                                }
                                else {
                                    result = false;
                                }
                            }
                            else {
                                msg = '手机号码长度不为11位';
                            }
                        }
                        else {
                            msg = '手机号码不能为空';
                        }
                        var returnData = {};
                        returnData['result'] = result;
                        returnData['message'] = msg;
                        return returnData;
                    };
                    /**
                     * 验证是否为正整数
                     * @param value
                     * @returns {boolean}
                     */
                    Validate.validatePositiveInteger = function (value) {
                        var re = /^[1-9]+[0-9]*]*$/;
                        if (re.test(value)) {
                            return true;
                        }
                        return false;
                    };
                    /**
                     * 验证是否为数字
                     * @param value
                     * @returns {boolean}
                     */
                    Validate.validateNumber = function (value) {
                        if (value === null || value === '') {
                            return false;
                        }
                        if (!isNaN(value)) {
                            return true;
                        }
                        return false;
                    };
                    /**
                     * 验证身份证号
                     * @param code 身份证号码
                     * @returns {{[p: string]: any}} 验证结果
                     */
                    Validate.cardid = function (code) {
                        var list = [];
                        var result = true;
                        var msg = '';
                        var city = {
                            11: "北京",
                            12: "天津",
                            13: "河北",
                            14: "山西",
                            15: "内蒙古",
                            21: "辽宁",
                            22: "吉林",
                            23: "黑龙江 ",
                            31: "上海",
                            32: "江苏",
                            33: "浙江",
                            34: "安徽",
                            35: "福建",
                            36: "江西",
                            37: "山东",
                            41: "河南",
                            42: "湖北 ",
                            43: "湖南",
                            44: "广东",
                            45: "广西",
                            46: "海南",
                            50: "重庆",
                            51: "四川",
                            52: "贵州",
                            53: "云南",
                            54: "西藏 ",
                            61: "陕西",
                            62: "甘肃",
                            63: "青海",
                            64: "宁夏",
                            65: "新疆",
                            71: "台湾",
                            81: "香港",
                            82: "澳门",
                            91: "国外 "
                        };
                        if (!Validate.validateNull(code)) {
                            if (code.length == 18) {
                                if (!code || !/(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(code)) {
                                    msg = "证件号码格式错误";
                                }
                                else if (!city[code.substr(0, 2)]) {
                                    msg = "地址编码错误";
                                }
                                else {
                                    //18位身份证需要验证最后一位校验位
                                    code = code.split('');
                                    //∑(ai×Wi)(mod 11)
                                    //加权因子
                                    var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                                    //校验位
                                    var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2, 'x'];
                                    var sum = 0;
                                    var ai = 0;
                                    var wi = 0;
                                    for (var i = 0; i < 17; i++) {
                                        ai = code[i];
                                        wi = factor[i];
                                        sum += ai * wi;
                                    }
                                    var last = parity[sum % 11];
                                    if (parity[sum % 11] != code[17]) {
                                        msg = "证件号码校验位错误";
                                    }
                                    else {
                                        result = false;
                                    }
                                }
                            }
                            else {
                                msg = "证件号码长度不为18位";
                            }
                        }
                        else {
                            msg = "证件号码不能为空";
                        }
                        var returnData = {};
                        returnData['result'] = result;
                        returnData['message'] = msg;
                        return returnData;
                    };
                    /**
                     * 验证cron表达式
                     * @param {string} cronExpression
                     * @returns {any}
                     */
                    Validate.cronValidate = function (cronExpression) {
                        //返回错误信息用
                        var message = '';
                        //先将cron表达式进行分割
                        var cronParams = cronExpression.split(" ");
                        //判断cron表达式是否具有该具有的属性长度，没有年份的长度为6，带年份的长度为7，其他情况都是错误的
                        if (cronParams.length < 6 || cronParams.length > 7) {
                            return "cron表达式需要输入6-7位参数，请重新输入";
                        }
                        else {
                            //日和周必须有一个为?，或者全为*
                            if ((cronParams[3] == "?" && cronParams[5] != "?") || (cronParams[5] == "?" && cronParams[3] != "?") || (cronParams[3] == "*" && cronParams[5] == "*")) {
                                //检查第一位的秒是否正确
                                message = checkSecondsField(cronParams[0]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第二位的分是否正确
                                message = checkMinutesField(cronParams[1]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第三位的时是否正确
                                message = checkHoursField(cronParams[2]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第四位的日是否正确
                                message = checkDayOfMonthField(cronParams[3]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第五位的月是否正确
                                message = checkMonthsField(cronParams[4]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第6位的周是否正确
                                message = checkDayOfWeekField(cronParams[5]);
                                if (message != true) {
                                    return message;
                                }
                                //检查第七位的年是否正确
                                if (cronParams.length > 6) {
                                    message = checkYearField(cronParams[6]);
                                    if (message != true) {
                                        return message;
                                    }
                                }
                                return true;
                            }
                            else {
                                return "指定日时周必须设为不指定(?),指定周时日必须设为不指定(?)";
                            }
                        }
                        //检查秒的函数方法
                        function checkSecondsField(secondsField) {
                            return checkField(secondsField, 0, 59, "秒");
                        }
                        //检查分的函数方法
                        function checkMinutesField(minutesField) {
                            return checkField(minutesField, 0, 59, "分");
                        }
                        //检查小时的函数方法
                        function checkHoursField(hoursField) {
                            return checkField(hoursField, 0, 23, "时");
                        }
                        //检查日期的函数方法
                        function checkDayOfMonthField(dayOfMonthField) {
                            if (dayOfMonthField == "?") {
                                return true;
                            }
                            if (dayOfMonthField.indexOf("L") >= 0) {
                                return checkFieldWithLetter(dayOfMonthField, "L", 1, 7, "日");
                            }
                            else if (dayOfMonthField.indexOf("W") >= 0) {
                                return checkFieldWithLetter(dayOfMonthField, "W", 1, 31, "日");
                            }
                            else if (dayOfMonthField.indexOf("C") >= 0) {
                                return checkFieldWithLetter(dayOfMonthField, "C", 1, 31, "日");
                            }
                            return checkField(dayOfMonthField, 1, 31, "日");
                        }
                        //检查月份的函数方法
                        function checkMonthsField(monthsField) {
                            //月份简写处理
                            if (monthsField != "*") {
                                monthsField = monthsField.replace("JAN", "1");
                                monthsField = monthsField.replace("FEB", "2");
                                monthsField = monthsField.replace("MAR", "3");
                                monthsField = monthsField.replace("APR", "4");
                                monthsField = monthsField.replace("MAY", "5");
                                monthsField = monthsField.replace("JUN", "6");
                                monthsField = monthsField.replace("JUL", "7");
                                monthsField = monthsField.replace("AUG", "8");
                                monthsField = monthsField.replace("SEP", "9");
                                monthsField = monthsField.replace("OCT", "10");
                                monthsField = monthsField.replace("NOV", "11");
                                monthsField = monthsField.replace("DEC", "12");
                                return checkField(monthsField, 1, 12, "月份");
                            }
                            else {
                                return true;
                            }
                        }
                        //星期验证
                        function checkDayOfWeekField(dayOfWeekField) {
                            dayOfWeekField = dayOfWeekField.replace("SUN", "1");
                            dayOfWeekField = dayOfWeekField.replace("MON", "2");
                            dayOfWeekField = dayOfWeekField.replace("TUE", "3");
                            dayOfWeekField = dayOfWeekField.replace("WED", "4");
                            dayOfWeekField = dayOfWeekField.replace("THU", "5");
                            dayOfWeekField = dayOfWeekField.replace("FRI", "6");
                            dayOfWeekField = dayOfWeekField.replace("SAT", "7");
                            if (dayOfWeekField == "?") {
                                return true;
                            }
                            if (dayOfWeekField.indexOf("L") >= 0) {
                                return checkFieldWithLetterWeek(dayOfWeekField, "L", 1, 7, "星期");
                            }
                            else if (dayOfWeekField.indexOf("C") >= 0) {
                                return checkFieldWithLetterWeek(dayOfWeekField, "C", 1, 7, "星期");
                            }
                            else if (dayOfWeekField.indexOf("#") >= 0) {
                                return checkFieldWithLetterWeek(dayOfWeekField, "#", 1, 7, "星期");
                            }
                            else {
                                return checkField(dayOfWeekField, 1, 7, "星期");
                            }
                        }
                        //检查年份的函数方法
                        function checkYearField(yearField) {
                            return checkField(yearField, 1970, 2099, "年的");
                        }
                        //通用的检查值的大小范围的方法( - , / *)
                        function checkField(value, minimal, maximal, attribute) {
                            //校验值中是否有“-”，如果有“-”的话，下标会>0
                            if (value.indexOf("-") > -1) {
                                return checkRangeAndCycle(value, minimal, maximal, attribute);
                            }
                            //校验值中是否有“，”，如果有“,”的话，下标会>0
                            else if (value.indexOf(",") > -1) {
                                return checkListField(value, minimal, maximal, attribute);
                            }
                            //校验值中是否有“/”，如果有“/”的话，下标会>0
                            else if (value.indexOf("/") > -1) {
                                return checkIncrementField(value, minimal, maximal, attribute);
                            }
                            //校验值是否为“*”
                            else if (value == "*") {
                                return true;
                            }
                            //校验单独的数字，英文字母，以及各种神奇的符号等...
                            else {
                                return checkIntValue(value, minimal, maximal, true, attribute);
                            }
                        }
                        //检测是否是整数以及是否在范围内,参数：检测的值，下限，上限，是否检查端点，检查的属性
                        function checkIntValue(value, minimal, maximal, checkExtremity, attribute) {
                            try {
                                //用10进制犯法来进行整数转换
                                var val = parseInt(value, 10);
                                if (value == val) {
                                    if (checkExtremity) {
                                        if (val < minimal || val > maximal) {
                                            return (attribute + "的参数取值范围必须在" + minimal + "-" + maximal + "之间");
                                        }
                                        return true;
                                    }
                                    return true;
                                }
                                return (attribute + "的参数存在非法字符，必须为整数或允许的大写英文");
                            }
                            catch (e) {
                                return (attribute + "的参数有非法字符，必须是整数~");
                            }
                        }
                        //检验枚举类型的参数是否正确
                        function checkListField(value, minimal, maximal, attribute) {
                            var st = value.split(",");
                            var values = new Array(st.length);
                            //计算枚举的数字在数组中中出现的次数，出现一次为没有重复的。
                            var count = 0;
                            for (var j = 0; j < st.length; j++) {
                                values[j] = st[j];
                            }
                            //判断枚举类型的值是否重复
                            for (var i = 0; i < values.length; i++) {
                                //判断枚举的值是否在范围内
                                message = checkIntValue(values[i], minimal, maximal, true, attribute);
                                if (message != true) {
                                    return message;
                                }
                                count = 0;
                                for (var j = 0; j < values.length; j++) {
                                    if (values[i] == values[j]) {
                                        count++;
                                    }
                                    if (count > 1) {
                                        return (attribute + "中的参数重复");
                                    }
                                }
                            }
                            var previousValue = -1;
                            //判断枚举的值是否排序正确
                            for (var i = 0; i < values.length; i++) {
                                var currentValue = values[i];
                                try {
                                    var val = parseInt(currentValue, 10);
                                    if (val < previousValue) {
                                        return (attribute + "的参数应该从小到大");
                                    }
                                    else {
                                        previousValue = val;
                                    }
                                }
                                catch (e) {
                                    //前面验证过了，这边的代码不可能跑到
                                    return ("这段提示用不到");
                                }
                            }
                            return true;
                        }
                        //检验循环
                        function checkIncrementField(value, minimal, maximal, attribute) {
                            if (value.split("/").length > 2) {
                                return (attribute + "中的参数只能有一个'/'");
                            }
                            var start = value.substring(0, value.indexOf("/"));
                            var increment = value.substring(value.indexOf("/") + 1);
                            if (start != "*") {
                                //检验前值是否正确
                                message = checkIntValue(start, minimal, maximal, true, attribute);
                                if (message != true) {
                                    return message;
                                }
                                //检验后值是否正确
                                message = checkIntValue(increment, minimal, maximal, true, attribute);
                                if (message != true) {
                                    return message;
                                }
                                return true;
                            }
                            else {
                                //检验后值是否正确
                                return checkIntValue(increment, minimal, maximal, false, attribute);
                            }
                        }
                        //检验范围
                        function checkRangeAndCycle(params, minimal, maximal, attribute) {
                            //校验“-”符号是否只有一个
                            if (params.split("-").length > 2) {
                                return (attribute + "中的参数只能有一个'-'");
                            }
                            var value = null;
                            var cycle = null;
                            //检验范围内是否有嵌套周期
                            if (params.indexOf("/") > -1) {
                                //校验“/”符号是否只有一个
                                if (params.split("/").length > 2) {
                                    return (attribute + "中的参数只能有一个'/'");
                                }
                                value = params.split("/")[0];
                                cycle = params.split("/")[1];
                                //判断循环的参数是否正确
                                message = checkIntValue(cycle, minimal, maximal, true, attribute);
                                if (message != true) {
                                    return message;
                                }
                            }
                            else {
                                value = params;
                            }
                            var startValue = value.substring(0, value.indexOf("-"));
                            var endValue = value.substring(value.indexOf("-") + 1);
                            //判断参数范围的第一个值是否正确
                            message = checkIntValue(startValue, minimal, maximal, true, attribute);
                            if (message != true) {
                                return message;
                            }
                            //判断参数范围的第二个值是否正确
                            message = checkIntValue(endValue, minimal, maximal, true, attribute);
                            if (message != true) {
                                return message;
                            }
                            //判断参数的范围前值是否小于后值
                            try {
                                var startVal = parseInt(startValue, 10);
                                var endVal = parseInt(endValue, 10);
                                if (endVal < startVal) {
                                    return (attribute + "的取值范围错误，前值必须小于后值");
                                }
                                if ((endVal - startVal) < parseInt(cycle, 10)) {
                                    return (attribute + "的取值范围内的循环无意义");
                                }
                                return true;
                            }
                            catch (e) {
                                //用不到这行代码的
                                return (attribute + "的参数有非法字符，必须是整数");
                            }
                        }
                        //检查日中的特殊字符
                        function checkFieldWithLetter(value, letter, minimalBefore, maximalBefore, attribute) {
                            //判断是否只有一个字母
                            for (var i = 0; i < value.length; i++) {
                                var count = 0;
                                if (value.charAt(i) == letter) {
                                    count++;
                                }
                                if (count > 1) {
                                    return (attribute + "的值的" + letter + "字母只能有一个");
                                }
                            }
                            //校验L
                            if (letter == "L") {
                                if (value == "LW") {
                                    return true;
                                }
                                if (value == "L") {
                                    return true;
                                }
                                if (value.endsWith("LW") && value.length > 2) {
                                    return (attribute + "中的参数，最后的LW前面不能有任何字母参数");
                                }
                                if (!value.endsWith("L")) {
                                    return (attribute + "中的参数，L字母后面不能有W以外的字符、数字等");
                                }
                                else {
                                    var num = value.substring(0, value.indexOf(letter));
                                    return checkIntValue(num, minimalBefore, maximalBefore, true, attribute);
                                }
                            }
                            //校验W
                            if (letter == "W") {
                                if (!value.endsWith("W")) {
                                    return (attribute + "中的参数的W必须作为结尾");
                                }
                                else {
                                    if (value == "W") {
                                        return (attribute + "中的参数的W前面必须有数字");
                                    }
                                    var num = value.substring(0, value.indexOf(letter));
                                    return checkIntValue(num, minimalBefore, maximalBefore, true, attribute);
                                }
                            }
                            if (letter == "C") {
                                if (!value.endsWith("C")) {
                                    return (attribute + "中的参数的C必须作为结尾");
                                }
                                else {
                                    if (value == "C") {
                                        return (attribute + "中的参数的C前面必须有数字");
                                    }
                                    var num = value.substring(0, value.indexOf(letter));
                                    return checkIntValue(num, minimalBefore, maximalBefore, true, attribute);
                                }
                            }
                        }
                        //检查星期中的特殊字符
                        function checkFieldWithLetterWeek(value, letter, minimalBefore, maximalBefore, attribute) {
                            //判断是否只有一个字母
                            for (var i = 0; i < value.length; i++) {
                                var count = 0;
                                if (value.charAt(i) == letter) {
                                    count++;
                                }
                                if (count > 1) {
                                    return (attribute + "的值的" + letter + "字母只能有一个");
                                }
                            }
                            //校验L
                            if (letter == "L") {
                                if (value == "L") {
                                    return true;
                                }
                                if (!value.endsWith("L")) {
                                    return (attribute + "中的参数，L字母必须是最后一位");
                                }
                                else {
                                    var num = value.substring(0, value.indexOf(letter));
                                    return checkIntValue(num, minimalBefore, maximalBefore, true, attribute);
                                }
                            }
                            if (letter == "C") {
                                if (!value.endsWith("C")) {
                                    return (attribute + "中的参数的C必须作为结尾");
                                }
                                else {
                                    if (value == "C") {
                                        return (attribute + "中的参数的C前面必须有数字");
                                    }
                                    var num = value.substring(0, value.indexOf(letter));
                                    return checkIntValue(num, minimalBefore, maximalBefore, true, attribute);
                                }
                            }
                            if (letter == "#") {
                                if (value == "#") {
                                    return (attribute + "中的#前后必须有整数");
                                }
                                if (value.charAt(0) == letter) {
                                    return (attribute + "中的#前面必须有整数");
                                }
                                if (value.endsWith("#")) {
                                    return (attribute + "中的#后面必须有整数");
                                }
                                var num1 = value.substring(0, value.indexOf(letter));
                                var num2 = value.substring(value.indexOf(letter) + 1, value.length);
                                message = checkIntValue(num1, 1, 4, true, (attribute + "的#前面"));
                                if (message != true) {
                                    return message;
                                }
                                message = checkIntValue(num2, minimalBefore, maximalBefore, true, (attribute + "的#后面"));
                                if (message != true) {
                                    return message;
                                }
                                return true;
                            }
                        }
                    };
                    return Validate;
                }());
                util.Validate = Validate;
            })(util = ui.util || (ui.util = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
