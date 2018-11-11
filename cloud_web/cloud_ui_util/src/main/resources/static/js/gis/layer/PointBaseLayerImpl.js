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
                var Validate = com.clouddo.ui.util.Validate;
                var DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
                /**
                 * 点图层的默认实现
                 */
                var PointBaseLayerImpl = /** @class */ (function (_super) {
                    __extends(PointBaseLayerImpl, _super);
                    function PointBaseLayerImpl(id, name, view, url) {
                        return _super.call(this, id, name, PointBaseLayerImpl.LAYER_TYPE, view, url) || this;
                    }
                    /**
                     * 重写描画方法
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    PointBaseLayerImpl.prototype.draw = function () {
                        var _this = this;
                        require([
                            'esri/Graphic',
                            'esri/geometry/Point',
                            'esri/symbols/PictureMarkerSymbol'
                        ], function (Graphic, Point, PictureMarkerSymbol) {
                            var datas = _this.data;
                            if (datas) {
                                $.each(datas, function (key, data) {
                                    // 创建点
                                    var point = new Point({
                                        x: data['lng'],
                                        y: data['lat']
                                    });
                                    // 创建样式
                                    // 1、从数据中获取样式
                                    // 2、如果不存在，从数据中获取legeng
                                    // 3、如果不存在，从图层中获取legeng
                                    // 4、如果不存在，使用默认的样式
                                    var symbol = data.symbol;
                                    if (Validate.validateNull(symbol)) {
                                        var legeng = data.legeng;
                                        if (!Validate.validateNull(legeng)) {
                                            symbol = new PictureMarkerSymbol({
                                                url: legeng,
                                                height: 24,
                                                width: 24
                                            });
                                        }
                                    }
                                    if (Validate.validateNull(symbol)) {
                                        var legeng = _this.legeng;
                                        if (!Validate.validateNull(legeng)) {
                                            symbol = new PictureMarkerSymbol({
                                                url: legeng,
                                                height: 24,
                                                width: 24
                                            });
                                        }
                                    }
                                    if (Validate.validateNull(symbol)) {
                                        symbol = {
                                            type: "simple-marker",
                                            color: [226, 119, 40],
                                            outline: {
                                                color: [255, 255, 255],
                                                width: 2
                                            }
                                        };
                                    }
                                    // 设置属性
                                    var attributes = {
                                        data: data,
                                        layer: _this
                                    };
                                    // 创建元素
                                    var graphic = new Graphic({
                                        attributes: attributes,
                                        geometry: point,
                                        symbol: symbol,
                                        id: data.id
                                    });
                                    // 添加元素
                                    _this.layer.add(graphic);
                                });
                            }
                            // 描画完成后事件
                            if (!Validate.validateNull(_this.events[PointBaseLayerImpl.EVENT_NAMES.DRAWED])) {
                                _this.events[PointBaseLayerImpl.EVENT_NAMES.DRAWED](_this);
                            }
                        });
                        return this;
                    };
                    PointBaseLayerImpl.LAYER_TYPE = 'point';
                    return PointBaseLayerImpl;
                }(DefaultBaseLayerImpl));
                gis.PointBaseLayerImpl = PointBaseLayerImpl;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
