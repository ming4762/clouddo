var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * 面图层实现类
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var gis;
            (function (gis) {
                var DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
                var PolygonBaseLayerImpl = /** @class */ (function (_super) {
                    __extends(PolygonBaseLayerImpl, _super);
                    function PolygonBaseLayerImpl(id, name, map, url) {
                        return _super.call(this, id, name, PolygonBaseLayerImpl.LAYER_TYPE, map, url) || this;
                    }
                    /**
                     * 重写描画方法
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    PolygonBaseLayerImpl.prototype.draw = function () {
                        var dataList = this.data;
                        if (dataList) {
                            require([
                                'esri/Graphic'
                            ], function (Graphic) {
                                // TODO 待完善
                            });
                        }
                    };
                    PolygonBaseLayerImpl.LAYER_TYPE = 'polygon';
                    return PolygonBaseLayerImpl;
                }(DefaultBaseLayerImpl));
                gis.PolygonBaseLayerImpl = PolygonBaseLayerImpl;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
