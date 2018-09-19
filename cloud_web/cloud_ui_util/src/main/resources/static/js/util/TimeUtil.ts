/**
 * 时间工具类
 */
namespace com.clouddo.ui.util {

    export class TimeUtil{

        /**
         *
         * @param {number} contrastTime 需要比对的时间
         * @param {number} beContrastTime 被比对的时间
         * @returns {string} 2分钟前、刚刚、1天前、一天后
         */
        public static contrastTime(contrastTime : number, beContrastTime? : number) : string {
            let result = "";
            if (!beContrastTime) {
                beContrastTime = new Date().getTime();
            }

            //标识比对时间是否在被比对之前
            let isBefore: boolean = contrastTime < beContrastTime;
            let str : string = isBefore ? "前" : "后";
            //获取时间差值
            let difference: number = Math.abs(beContrastTime - contrastTime);
            if(difference < 60 * 1000) {
                //如果时间小于1分钟
                if (isBefore) {
                    result = "刚刚";
                } else {
                    result = "马上";
                }
            } else if (difference < 60 * 60 * 1000) {
                //不超过1小时
                result = parseInt((difference % (1000 * 60 * 60)) / (1000 * 60) + "") + "分钟" + str;
            } else if (difference < 24 * 60 * 60 * 1000) {
                //不超过一天
                result = parseInt((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60) + "") + "小时" + str;
            } else if (difference < 30 * 24 * 60 * 60 * 1000) {
                //不超过一个月
                result = parseInt(difference / (1000 * 60 * 60 * 24) + "") + "天" + str;
            } else if (difference < 365 * 24 * 60 * 60 * 1000) {
                //不超过1年
                result = parseInt(difference / (1000 * 60 * 60 * 24 * 30) + "") + "月" + str;
            } else {
                result = parseInt(difference / (1000 * 60 * 60 * 24 * 365) + "") + "年" + str;
            }
            return result;
        }

        /**
         * 将时间差转为时间
         * @param {number} timeDif
         * @returns {string}
         */
        public static formatTimeDif (timeDif: number): string {
            // 计算年数
            let year = Math.floor(timeDif / (365*24*60*60*1000))
            // 计算月数
            let monthTimeDif = timeDif % (365*24*60*60*1000)
            let month = Math.floor(monthTimeDif / (30*24*3600*100))
            // 计算天数
            let dayTimeDif = monthTimeDif % (30*24*3600*1000)
            let day = Math.floor(dayTimeDif / (24*3600*1000))
            // 计算小时数
            let hourTimeDif = dayTimeDif % (24*3600*1000)
            let hour = Math.floor(hourTimeDif / (3600*1000))
            // 计算分钟数
            let miniteTimeDif = hourTimeDif % (3600*1000)
            let minite = Math.floor(miniteTimeDif / (60 * 1000))
            // 计算秒数
            let secondTimeDif = miniteTimeDif % (60 * 1000)
            let second = Math.floor(secondTimeDif / 1000)

            let result = ''
            if (year > 0) {
                result += (year + '年')
            }
            if (month > 0) {
                result += (month + '月')
            }
            if (day > 0) {
                result += (day + '天')
            }
            if (hour > 0) {
                result += (hour + '小时')
            }
            if (minite > 0) {
                result += (minite + '分钟')
            }
            if (second > 0) {
                result += (second + '秒')
            }
            return result
        }


        /**
         * 格式化时间
         * @param {number} time
         */
        public static formatTime (time: number): string {
            if (Validate.validateNull(time)) {
                return ""
            }
            let date = new Date(time);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let hour = date.getHours();
            let minute = date.getMinutes();
            let second = date.getSeconds()
            return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day) + " " + (hour < 10 ? ("0" + hour) : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
        }

        /**
         * 格式化日期
         * @param {number} time
         * @returns {string}
         */
        public static formatDate (time: number): string {
            if (Validate.validateNull(time)) {
                return ""
            }
            let date = new Date(time);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
        }
    }
}