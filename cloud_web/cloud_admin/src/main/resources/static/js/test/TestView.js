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
var View = com.clouddo.ui.gis.View;
var view;
var layer;
$('document').ready(function () {
    view = new View('viewElement');
    view.setWith2D3D(true, 'switch-btn');
    view.on(View.EVENT_NAMES.ONE_CREATE, function (viewObject) {
        console.log('one', viewObject);
    });
    view.on(View.EVENT_NAMES.ALL_CREATE, function (viewObject) {
        console.log('all', viewObject);
        // layer = new TestPoint(view.getMap())
        // layer.create()
        // layer.load({})
    });
    view.createView(View.VIEW_3D);
});
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var test;
            (function (test) {
                var PointBaseLayerImpl = com.clouddo.ui.gis.PointBaseLayerImpl;
                var TestPoint = /** @class */ (function (_super) {
                    __extends(TestPoint, _super);
                    function TestPoint(map) {
                        var _this = _super.call(this, 'testLayer', '测试图层', map, '') || this;
                        _this.legeng = '';
                        return _this;
                    }
                    return TestPoint;
                }(PointBaseLayerImpl));
                test.TestPoint = TestPoint;
            })(test = ui.test || (ui.test = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
