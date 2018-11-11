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
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var gis;
            (function (gis) {
                // 声明require
                var Validate = com.clouddo.ui.util.Validate;
                var DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
                /**
                 * 线图层实现类
                 */
                var LineBaseLayerImpl = /** @class */ (function (_super) {
                    __extends(LineBaseLayerImpl, _super);
                    function LineBaseLayerImpl(id, name, view, url) {
                        return _super.call(this, id, name, LineBaseLayerImpl.LAYER_TYPE, view, url) || this;
                    }
                    /**
                     * 重写描画方法
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    LineBaseLayerImpl.prototype.draw = function () {
                        var _this = this;
                        require([
                            'esri/Graphic',
                            'esri/geometry/Polyline'
                        ], function (Graphic, Polyline) {
                            var datas = _this.data;
                            if (datas) {
                                $.each(datas, function (key, data) {
                                    // 从数据中获取样式
                                    var symbol = data['symbol'];
                                    // 如果不存在，获取图层的默认样式
                                    if (Validate.validateNull(symbol)) {
                                        symbol = _this['symbol'];
                                    }
                                    // 如果不存在，使用默认的样式
                                    if (Validate.validateNull(symbol)) {
                                        symbol = {
                                            type: "simple-line",
                                            color: "#1e9fff",
                                            width: 4
                                        };
                                    }
                                    //创建线
                                    var polyline = new Polyline({
                                        paths: data.paths,
                                    });
                                    // 创建元素
                                    var graphic2D = new Graphic({
                                        geometry: polyline,
                                        symbol: symbol,
                                        attributes: { data: data, layer: _this }
                                    });
                                    _this.layer.add(graphic2D);
                                });
                            }
                            // 描画完成后事件
                            if (!Validate.validateNull(_this.events[LineBaseLayerImpl.EVENT_NAMES.DRAWED])) {
                                _this.events[LineBaseLayerImpl.EVENT_NAMES.DRAWED](_this);
                            }
                        });
                        return this;
                    };
                    LineBaseLayerImpl.LAYER_TYPE = 'line';
                    return LineBaseLayerImpl;
                }(DefaultBaseLayerImpl));
                gis.LineBaseLayerImpl = LineBaseLayerImpl;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
