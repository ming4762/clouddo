/**
 * 坐标系工具类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var gis;
            (function (gis) {
                var util;
                (function (util) {
                    var CoordinateUtil = /** @class */ (function () {
                        function CoordinateUtil() {
                        }
                        /**
                         * wgs84转墨卡托
                         * @param lng 经度
                         * @param lat 维度
                         */
                        CoordinateUtil.wgs842Mercator = function (lng, lat) {
                            var earthRad = 6378137.0;
                            var mx = lng * Math.PI / 180 * earthRad;
                            var a = lat * Math.PI / 180;
                            var my = earthRad / 2 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
                            return [mx, my];
                        };
                        /**
                         * 墨卡托转wgs84
                         * @param x
                         * @param y
                         */
                        CoordinateUtil.mercator2Wgs84 = function (x, y) {
                            var lng = x / 20037508.34 * 180;
                            var lat = y / 20037508.34 * 180;
                            lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180)) - Math.PI / 2);
                            return [lng, lat];
                        };
                        return CoordinateUtil;
                    }());
                    util.CoordinateUtil = CoordinateUtil;
                })(util = gis.util || (gis.util = {}));
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
