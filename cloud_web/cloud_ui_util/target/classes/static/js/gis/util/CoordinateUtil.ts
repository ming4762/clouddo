/**
 * 坐标系工具类
 */
namespace com.clouddo.ui.gis.util {
    export class CoordinateUtil {

        /**
         * wgs84转墨卡托
         * @param lng 经度
         * @param lat 维度
         */
        public static wgs842Mercator (lng: number, lat: number): number[] {
            let earthRad: number = 6378137.0
            let mx: number = lng * Math.PI / 180 * earthRad
            let a = lat * Math.PI / 180
            let my: number = earthRad / 2 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)))
            return [mx, my]
        }

        /**
         * 墨卡托转wgs84
         * @param x
         * @param y
         */
        public static mercator2Wgs84 (x: number, y: number): number[] {
            let lng: number = x/20037508.34*180
            let lat: number = y/20037508.34*180
            lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180)) - Math.PI / 2)
            return [lng, lat]
        }
    }
}