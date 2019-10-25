var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var gis;
            (function (gis) {
                var Validate = com.clouddo.ui.util.Validate;
                /**
                 * 视图类
                 */
                var View = /** @class */ (function () {
                    /**
                     * 构造函数
                     */
                    function View(viewElement) {
                        var _this = this;
                        // 事件信息
                        this.events = {};
                        /**
                         * 是否为调试模式
                         * @type {boolean} 默认开始调试
                         */
                        this.debug = true;
                        // 标识是否已经加载
                        this.load = false;
                        // 基础图层，默认为地形图层
                        this.baseMap = 'satellite';
                        // 是否同时包含2D 3D地图
                        this.with2D3D = false;
                        /**
                         * 切换视图
                         */
                        this.switchView = function () {
                            // 获取当前视图类型
                            var is3D = _this.viewContainer.activeView.type === '3d';
                            _this.viewContainer.activeView.i;
                            if (is3D) {
                                // 获取当前视图中心店
                                _this.viewContainer.mapView.viewpoint = _this.viewContainer.activeView.viewpoint.clone();
                                _this.viewContainer.mapView.container = _this.viewContainer.container;
                                // 设置激活视图
                                _this.viewContainer.activeView = _this.viewContainer.mapView;
                                document.getElementById(_this.swtichElment).value = '3d';
                            }
                            else {
                                _this.viewContainer.sceneView.viewpoint = _this.viewContainer.activeView.viewpoint.clone();
                                _this.viewContainer.sceneView.container = _this.viewContainer.container;
                                _this.viewContainer.activeView = _this.viewContainer.sceneView;
                                document.getElementById(_this.swtichElment).value = '2d';
                            }
                        };
                        this.viewElement = viewElement;
                        // 初始化视图容器
                        this.viewContainer = {
                            mapView: null,
                            sceneView: null,
                            activeView: null,
                            container: this.viewElement
                        };
                    }
                    /**
                     * 获取map实例
                     * @returns {any}
                     */
                    View.prototype.getMap = function () {
                        return this.map;
                    };
                    /**
                     * 弹窗
                     * @param message
                     * @param mapPoint
                     * @returns {com.clouddo.ui.gis.View}
                     */
                    View.prototype.openPopup = function (message, mapPoint) {
                        var popubTem = message;
                        popubTem.location = mapPoint;
                        this.getActiveView().popup.open(popubTem);
                        return this;
                    };
                    /**
                     * 关闭弹窗
                     * @returns {com.clouddo.ui.gis.View}
                     */
                    View.prototype.closePopup = function () {
                        this.getActiveView().popup.close();
                        return this;
                    };
                    /**
                     * 添加图层
                     * @param layer
                     */
                    View.prototype.addLayer = function (layer) {
                        this.getMap().add(layer);
                    };
                    /**
                     * 设置是否是2D3D混合地图
                     * @param {boolean} with2D3D
                     * @returns {com.clouddo.ui.gis.View}
                     */
                    View.prototype.setWith2D3D = function (with2D3D, switchEl) {
                        this.with2D3D = with2D3D;
                        this.swtichElment = switchEl;
                        // 绑定切换按钮事件
                        document.getElementById(switchEl).onclick = this.switchView;
                        return this;
                    };
                    /**
                     * 设置边界
                     * @param extent
                     * @returns {com.clouddo.ui.gis.View}
                     */
                    View.prototype.setExtent = function (extent) {
                        this.extent = extent;
                        // 如果图层已经加载，则直接设置
                        if (this.load) {
                            this.getActiveView().extent = extent;
                        }
                        return this;
                    };
                    /**
                     * 设置基础图层
                     * @param {string} baseMap
                     * @returns {com.clouddo.ui.gis.View}
                     */
                    View.prototype.setBaseMap = function (baseMap) {
                        this.baseMap = baseMap;
                        if (this.load) {
                            this.getMap().basemap = baseMap;
                        }
                        return this;
                    };
                    /**
                     * 获取当前激活的图层
                     * @returns {any}
                     */
                    View.prototype.getActiveView = function () {
                        return this.viewContainer.activeView;
                    };
                    /**
                     * 事件
                     * @param {string} event 事件名
                     * @param {Function} eventFunction 时间函数
                     */
                    View.prototype.on = function (event, eventFunction) {
                        this.events[event] = eventFunction;
                    };
                    /**
                     * 创建视图
                     * @param {String} viewType 创建的视图类型3D/2d，with3D为true该参数无效，如果未设置默认2d
                     */
                    View.prototype.createView = function (viewType) {
                        var _this = this;
                        if (Validate.validateNull(viewType)) {
                            viewType = View.VIEW_2D;
                        }
                        // 创建地图
                        require([
                            'esri/Map'
                        ], function (Map) {
                            _this.map = new Map({
                                basemap: _this.baseMap,
                                ground: "world-elevation"
                            });
                            // 创建视图
                            _this.defaultcreateView(viewType);
                        });
                    };
                    /**
                     * 创建视图函数
                     * @param {String} viewType
                     */
                    View.prototype.defaultcreateView = function (viewType) {
                        //视图初始化参数
                        var initialViewParams = {
                            map: this.map,
                            extent: this.extent,
                            container: this.viewContainer.container,
                            constraints: {
                                rotationEnabled: false
                            },
                            viewType: viewType
                        };
                        if (this.with2D3D) {
                            this.create2D3DView(initialViewParams);
                        }
                        else {
                            this.createOneView(initialViewParams);
                        }
                    };
                    /**
                     * 添加默认的视图事件
                     */
                    View.prototype.addDefaultViewEvent = function (view) {
                        var _this = this;
                        // 鼠标移动事件
                        view.on('pointer-move', function (event) {
                            if (event.x !== 0 && event.y !== 0) {
                                view.hitTest(event)
                                    .then(function (response) {
                                    if (response['results'].length && response.results[0].graphic) {
                                        var layer = response.results[0].graphic.attributes.layer;
                                        if (layer) {
                                            // 调用图层鼠标移动相应方法
                                            layer.pointerMove(response);
                                        }
                                    }
                                    else {
                                        _this.closePopup();
                                    }
                                });
                            }
                        });
                        // 鼠标点击事件
                        view.on('click', function (event) {
                            view.hitTest(event)
                                .then(function (response) {
                                if (response['results'].length && response.results[0].graphic) {
                                    var layer = response.results[0].graphic.attributes.layer;
                                    if (layer) {
                                        // 调用图层鼠标移动相应方法
                                        layer.clickGraphic(response);
                                    }
                                }
                                else {
                                    _this.closePopup();
                                }
                            });
                        });
                    };
                    /**
                     * 创建单一视图
                     * @param initialViewParams
                     */
                    View.prototype.createOneView = function (initialViewParams) {
                        var _this = this;
                        require([
                            'esri/views/MapView',
                            'esri/views/SceneView'
                        ], function (MapView, SceneView) {
                            var viewType = initialViewParams.viewType;
                            var view;
                            if (viewType === View.VIEW_3D) {
                                // 创建3d视图
                                view = new SceneView(initialViewParams);
                                _this.viewContainer.sceneView = view;
                            }
                            else {
                                view = new MapView(initialViewParams);
                                _this.viewContainer.mapView = view;
                            }
                            // 设置活动的view
                            _this.viewContainer.activeView = view;
                            // 一个视图创建完成回调
                            if (!Validate.validateNull(_this.events[View.EVENT_NAMES.ONE_CREATE])) {
                                _this.events[View.EVENT_NAMES.ONE_CREATE](_this);
                            }
                            // 所有视图创建完成回调
                            if (!Validate.validateNull(_this.events[View.EVENT_NAMES.ALL_CREATE])) {
                                _this.events[View.EVENT_NAMES.ALL_CREATE](_this);
                            }
                            // 添加默认的视图事件
                            _this.addDefaultViewEvent(view);
                            _this.load = true;
                        });
                    };
                    /**
                     * 创建2D 3D混合视图
                     * @param initialViewParams
                     */
                    View.prototype.create2D3DView = function (initialViewParams) {
                        var _this = this;
                        var $this = this;
                        require([
                            'esri/views/MapView',
                            'esri/views/SceneView'
                        ], function (MapView, SceneView) {
                            // 创建2D视图，并设置激活的视图
                            _this.viewContainer.mapView = createView(initialViewParams, View.VIEW_2D);
                            _this.viewContainer.activeView = _this.viewContainer.mapView;
                            // 创建3D视图
                            initialViewParams.container = null;
                            _this.viewContainer.sceneView = createView(initialViewParams, View.VIEW_3D);
                            function createView(params, type) {
                                var view;
                                var is2D = type === View.VIEW_2D;
                                if (is2D) {
                                    view = new MapView(params);
                                }
                                else {
                                    view = new SceneView(params);
                                }
                                // 一个视图创建完成函数
                                if (!Validate.validateNull($this.events[View.EVENT_NAMES.ONE_CREATE])) {
                                    $this.events[View.EVENT_NAMES.ONE_CREATE]($this);
                                }
                                // 添加默认的视图事件
                                $this.addDefaultViewEvent(view);
                                return view;
                            }
                            // 所有视图创建完成回调
                            if (!Validate.validateNull(_this.events[View.EVENT_NAMES.ALL_CREATE])) {
                                _this.events[View.EVENT_NAMES.ALL_CREATE](_this);
                            }
                            _this.load = true;
                        });
                    };
                    //视图类型：3D视图标识
                    View.VIEW_3D = "3d";
                    //视图类型：2D视图标识
                    View.VIEW_2D = "2d";
                    // 事件名称
                    View.EVENT_NAMES = {
                        ONE_CREATE: 'one_create',
                        ALL_CREATE: 'all_create'
                    };
                    return View;
                }());
                gis.View = View;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
