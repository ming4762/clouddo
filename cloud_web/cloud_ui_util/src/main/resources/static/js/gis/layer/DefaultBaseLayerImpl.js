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
                var AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
                // 默认的图层实现类
                var DefaultBaseLayerImpl = /** @class */ (function () {
                    /**
                     * 构造函数
                     * @param {string} id 图层ID
                     * @param {string} name 图层名称
                     * @param map 图层所在map
                     * @param {string} loadDataUrl 加载数据的URL
                     */
                    function DefaultBaseLayerImpl(id, name, type, view, loadDataUrl) {
                        /**
                         * 事件信息
                         * @type {{}}
                         */
                        this.events = {};
                        this.id = id;
                        this.name = name;
                        this.view = view;
                        this.loadDataUrl = loadDataUrl;
                        this.type = type;
                    }
                    /**
                     * 事件
                     * @param {string} eventName 事件名称
                     * @param {Function} eventFunction 事件方法
                     */
                    DefaultBaseLayerImpl.prototype.on = function (eventName, eventFunction) {
                        this.events[eventName] = eventFunction;
                        return this;
                    };
                    /**
                     * 创建图层
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    DefaultBaseLayerImpl.prototype.create = function () {
                        var _this = this;
                        require([
                            'esri/layers/GraphicsLayer'
                        ], function (GraphicsLayer) {
                            _this.layer = new GraphicsLayer({
                                id: _this.id
                            });
                            _this.view.getMap().add(_this.layer);
                            // 执行创建完成事件
                            if (!Validate.validateNull(_this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED])) {
                                _this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED](_this);
                            }
                        });
                        return this;
                    };
                    /**
                     * 清除图层
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    DefaultBaseLayerImpl.prototype.clear = function () {
                        this.layer.removeAll();
                        return this;
                    };
                    /**
                     * 图层点击回调
                     * @param response
                     */
                    DefaultBaseLayerImpl.prototype.clickGraphic = function (response) {
                        console.log(response);
                    };
                    /**
                     * 鼠标移动回调
                     * @param response
                     */
                    DefaultBaseLayerImpl.prototype.pointerMove = function (response) {
                        console.log(response);
                    };
                    /**
                     * 显示隐藏图层
                     * @param {boolean} showHide
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    DefaultBaseLayerImpl.prototype.showHide = function (showHide) {
                        this.layer.visible = showHide;
                        return this;
                    };
                    /**
                     * 加载数据
                     * @param {{[p: string]: any}} parameters
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    DefaultBaseLayerImpl.prototype.load = function (parameters) {
                        var _this = this;
                        this.dataPrameters = parameters;
                        if (this.layer) {
                            return this.doLoad();
                        }
                        else {
                            if (this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED]) {
                                this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED] = function () {
                                    _this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED]();
                                    _this.doLoad();
                                };
                            }
                            else {
                                this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED] = this.doLoad;
                            }
                        }
                    };
                    // 执行数据加载方法
                    DefaultBaseLayerImpl.prototype.doLoad = function () {
                        var _this = this;
                        AuthRestUtil.postAjax(this.loadDataUrl, this.dataPrameters, function (result) {
                            _this.data = result;
                            // 执行数据加载完成后回调
                            if (!Validate.validateNull(_this.events[DefaultBaseLayerImpl.EVENT_NAMES.DATA_LOADED])) {
                                _this.events[DefaultBaseLayerImpl.EVENT_NAMES.DATA_LOADED](_this);
                            }
                            // 执行描画函数
                            _this.draw();
                        }, function (error) {
                            console.log(error);
                        });
                        return this;
                    };
                    /**
                     * 描画函数
                     * @returns {com.clouddo.ui.gis.BaseLayer}
                     */
                    DefaultBaseLayerImpl.prototype.draw = function () {
                        return this;
                    };
                    /**
                     * 事件名称
                     * @type {{CREATED: string}}
                     */
                    DefaultBaseLayerImpl.EVENT_NAMES = {
                        // 图层创建完成
                        CREATED: 'created',
                        // 图层加载完成
                        DATA_LOADED: 'data_Loaded',
                        // 图层描画完成后事件
                        DRAWED: 'drawed'
                    };
                    return DefaultBaseLayerImpl;
                }());
                gis.DefaultBaseLayerImpl = DefaultBaseLayerImpl;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
