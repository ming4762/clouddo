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
    }
}