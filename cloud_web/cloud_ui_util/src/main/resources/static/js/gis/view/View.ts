
namespace com.clouddo.ui.gis {
    import Validate = com.clouddo.ui.util.Validate;

    // 声明require
    declare let require: any

    /**
     * 视图类
     */
    export class View {
        //视图类型：3D视图标识
        public static VIEW_3D : string = "3d"
        //视图类型：2D视图标识
        public static VIEW_2D : string = "2d"

        // 事件名称
        public static EVENT_NAMES = {
            ONE_CREATE: 'one_create',
            ALL_CREATE: 'all_create'
        }

        // 事件信息
        private events: {[index: string]: Function} = {}


        /**
         * 是否为调试模式
         * @type {boolean} 默认开始调试
         */
        private debug: boolean = true

        // 标识是否已经加载
        private load: boolean = false
        // 基础图层，默认为地形图层
        private baseMap: string = 'satellite'


        //视图所在element
        private viewElement: string
        // 视图切换按钮ID
        private swtichElment: string

        // 视图容器
        private viewContainer: {[index: string]: any}
        //地图对象
        private map: any
        // 视图边界
        private extent: any
        // 是否同时包含2D 3D地图
        private with2D3D: boolean = false

        /**
         * 构造函数
         */
        constructor (viewElement : string) {
            this.viewElement = viewElement
            // 初始化视图容器
            this.viewContainer = {
                mapView: null,
                sceneView: null,
                activeView: null,
                container: this.viewElement
            }
        }

        /**
         * 获取map实例
         * @returns {any}
         */
        public getMap() : any {
            return this.map
        }

        /**
         * 弹窗
         * @param message
         * @param mapPoint
         * @returns {com.clouddo.ui.gis.View}
         */
        public openPopup (message: any, mapPoint: any): View {
            let popubTem = message
            popubTem.location = mapPoint
            this.getActiveView().popup.open(popubTem)
            return this
        }

        /**
         * 关闭弹窗
         * @returns {com.clouddo.ui.gis.View}
         */
        public closePopup (): View {
            this.getActiveView().popup.close()
            return this
        }

        /**
         * 添加图层
         * @param layer
         */
        public addLayer (layer: any): void {
            this.getMap().add(layer)
        }

        /**
         * 设置是否是2D3D混合地图
         * @param {boolean} with2D3D
         * @returns {com.clouddo.ui.gis.View}
         */
        public setWith2D3D (with2D3D: boolean, switchEl: string): View {
            this.with2D3D = with2D3D
            this.swtichElment = switchEl
            // 绑定切换按钮事件
            document.getElementById(switchEl).onclick = this.switchView
            return this
        }

        /**
         * 设置边界
         * @param extent
         * @returns {com.clouddo.ui.gis.View}
         */
        public setExtent (extent: any): View {
            this.extent = extent
            // 如果图层已经加载，则直接设置
            if (this.load) {
                this.getActiveView().extent = extent
            }
            return this
        }

        /**
         * 设置基础图层
         * @param {string} baseMap
         * @returns {com.clouddo.ui.gis.View}
         */
        public setBaseMap (baseMap: string): View {
            this.baseMap = baseMap
            if (this.load) {
                this.getMap().basemap = baseMap
            }
            return this
        }

        /**
         * 获取当前激活的图层
         * @returns {any}
         */
        public getActiveView (): any {
            return this.viewContainer.activeView
        }


        /**
         * 事件
         * @param {string} event 事件名
         * @param {Function} eventFunction 时间函数
         */
        public on (event: string, eventFunction: Function) {
            this.events[event] = eventFunction
        }

        /**
         * 创建视图
         * @param {String} viewType 创建的视图类型3D/2d，with3D为true该参数无效，如果未设置默认2d
         */
        public createView (viewType ? : String) {
            if (Validate.validateNull(viewType)) {
                viewType = View.VIEW_2D
            }
            // 创建地图
            require([
                'esri/Map'
            ], (Map) => {
                this.map = new Map({
                    basemap: this.baseMap,
                    ground: "world-elevation"
                })
                // 创建视图
                this.defaultcreateView(viewType)
            })
        }

        /**
         * 切换视图
         */
        public switchView = () => {
            // 获取当前视图类型
            let is3D = this.viewContainer.activeView.type === '3d'
            this.viewContainer.activeView.container = null
            if (is3D) {
                // 获取当前视图中心店
                this.viewContainer.mapView.viewpoint = this.viewContainer.activeView.viewpoint.clone()
                this.viewContainer.mapView.container = this.viewContainer.container
                // 设置激活视图
                this.viewContainer.activeView = this.viewContainer.mapView
                document.getElementById(this.swtichElment).value = '3d'
            } else {
                this.viewContainer.sceneView.viewpoint = this.viewContainer.activeView.viewpoint.clone()
                this.viewContainer.sceneView.container = this.viewContainer.container
                this.viewContainer.activeView = this.viewContainer.sceneView
                document.getElementById(this.swtichElment).value = '2d'

            }

        }

        /**
         * 创建视图函数
         * @param {String} viewType
         */
        protected defaultcreateView (viewType : String) {
            //视图初始化参数
            let initialViewParams = {
                map: this.map,
                extent : this.extent,
                container: this.viewContainer.container,
                constraints: {
                    rotationEnabled: false
                },
                viewType : viewType
            };
            if (this.with2D3D) {
                this.create2D3DView(initialViewParams);
            } else {
                this.createOneView(initialViewParams);
            }
        }

        /**
         * 添加默认的视图事件
         */
        protected addDefaultViewEvent (view: any): void {
            // 鼠标移动事件
            view.on('pointer-move', (event) => {
                if (event.x !== 0 && event.y !== 0) {
                    view.hitTest(event)
                        .then((response) => {
                            if (response['results'].length && response.results[0].graphic) {
                                let layer: BaseLayer = response.results[0].graphic.attributes.layer
                                if (layer) {
                                    // 调用图层鼠标移动相应方法
                                    layer.pointerMove(response)
                                }
                            } else {
                                this.closePopup()
                            }
                        })
                }
            })
            // 鼠标点击事件
            view.on('click', (event) => {
                view.hitTest(event)
                    .then((response) => {
                        if (response['results'].length && response.results[0].graphic) {
                            let layer: BaseLayer = response.results[0].graphic.attributes.layer
                            if (layer) {
                                // 调用图层鼠标移动相应方法
                                layer.clickGraphic(response)
                            }
                        } else {
                            this.closePopup()
                        }
                    })
            })
        }

        /**
         * 创建单一视图
         * @param initialViewParams
         */
        protected createOneView (initialViewParams: any): void {
            require([
                'esri/views/MapView',
                'esri/views/SceneView'
            ], (MapView, SceneView) => {
                let viewType = initialViewParams.viewType
                let view: any
                if (viewType === View.VIEW_3D) {
                    // 创建3d视图
                    view = new SceneView(initialViewParams)
                    this.viewContainer.sceneView = view
                } else {
                    view = new MapView(initialViewParams)
                    this.viewContainer.mapView = view
                }
                // 设置活动的view
                this.viewContainer.activeView = view
                // 一个视图创建完成回调
                if (!Validate.validateNull(this.events[View.EVENT_NAMES.ONE_CREATE])) {
                    this.events[View.EVENT_NAMES.ONE_CREATE](this)
                }
                // 所有视图创建完成回调
                if (!Validate.validateNull(this.events[View.EVENT_NAMES.ALL_CREATE])) {
                    this.events[View.EVENT_NAMES.ALL_CREATE](this)
                }
                // 添加默认的视图事件
                this.addDefaultViewEvent(view)
                this.load = true
            })
        }

        /**
         * 创建2D 3D混合视图
         * @param initialViewParams
         */
        protected create2D3DView (initialViewParams: any): void {
            let $this = this
            require([
                'esri/views/MapView',
                'esri/views/SceneView'
            ], (MapView, SceneView) => {
                // 创建2D视图，并设置激活的视图
                this.viewContainer.mapView = createView(initialViewParams, View.VIEW_2D)
                this.viewContainer.activeView = this.viewContainer.mapView

                // 创建3D视图
                initialViewParams.container = null
                this.viewContainer.sceneView = createView(initialViewParams, View.VIEW_3D)

                function createView (params, type) {
                    let view;
                    let is2D = type === View.VIEW_2D;
                    if (is2D) {
                        view = new MapView(params);
                    } else {
                        view = new SceneView(params);
                    }
                    // 一个视图创建完成函数
                    if (!Validate.validateNull($this.events[View.EVENT_NAMES.ONE_CREATE])) {
                        $this.events[View.EVENT_NAMES.ONE_CREATE]($this)
                    }
                    // 添加默认的视图事件
                    $this.addDefaultViewEvent(view)
                    return view;
                }
                // 所有视图创建完成回调
                if (!Validate.validateNull(this.events[View.EVENT_NAMES.ALL_CREATE])) {
                    this.events[View.EVENT_NAMES.ALL_CREATE](this)
                }
                this.load = true
            })
        }
    }
}
