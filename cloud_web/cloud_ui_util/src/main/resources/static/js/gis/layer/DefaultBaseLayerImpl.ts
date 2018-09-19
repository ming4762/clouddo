
namespace com.clouddo.ui.gis {
    // 声明require
    import Validate = com.clouddo.ui.util.Validate;
    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
    declare let require: any
    // 默认的图层实现类
    export class DefaultBaseLayerImpl implements BaseLayer {

        /**
         * 事件名称
         * @type {{CREATED: string}}
         */
        public static EVENT_NAMES: any = {
            // 图层创建完成
            CREATED: 'created',
            // 图层加载完成
            DATA_LOADED: 'data_Loaded',
            // 图层描画完成后事件
            DRAWED: 'drawed'
        }

        id: string
        layer: any
        /**
         * 事件信息
         * @type {{}}
         */
        protected events: {[index: string]: Function} = {}

        // 向后台请求数据的参数
        private dataPrameters: {[index: string]: any}

        // 图层名称
        protected name: string
        // 图层所在视图
        protected view: any
        // 加载数据的url
        protected loadDataUrl: string
        // 图层数据
        protected data: any
        // 类型
        type: string;

        /**
         * 构造函数
         * @param {string} id 图层ID
         * @param {string} name 图层名称
         * @param map 图层所在map
         * @param {string} loadDataUrl 加载数据的URL
         */
        constructor (id: string, name: string, type: string,  view: any, loadDataUrl: string) {
            this.id = id
            this.name = name
            this.view = view
            this.loadDataUrl = loadDataUrl
            this.type = type
        }

        /**
         * 事件
         * @param {string} eventName 事件名称
         * @param {Function} eventFunction 事件方法
         */
        on(eventName: string, eventFunction: Function): com.clouddo.ui.gis.BaseLayer {
            this.events[eventName] = eventFunction
            return this
        }

        /**
         * 创建图层
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        create(): com.clouddo.ui.gis.BaseLayer {
            require([
                'esri/layers/GraphicsLayer'
            ], (GraphicsLayer) => {
                this.layer = new GraphicsLayer({
                    id: this.id
                })
                this.view.getMap().add(this.layer)
                // 执行创建完成事件
                if (!Validate.validateNull(this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED])) {
                    this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED](this)
                }
            })
            return this;
        }

        /**
         * 清除图层
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        clear(): com.clouddo.ui.gis.BaseLayer {
            this.layer.removeAll()
            return this;
        }

        /**
         * 图层点击回调
         * @param response
         */
        clickGraphic(response: any): void {
            console.log(response)
        }

        /**
         * 鼠标移动回调
         * @param response
         */
        pointerMove(response: any): void {
            console.log(response)
        }

        /**
         * 显示隐藏图层
         * @param {boolean} showHide
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        showHide(showHide: boolean): com.clouddo.ui.gis.BaseLayer {
            this.layer.visible = showHide
            return this;
        }

        /**
         * 加载数据
         * @param {{[p: string]: any}} parameters
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        load(parameters: { [p: string]: any }): com.clouddo.ui.gis.BaseLayer {
            this.dataPrameters = parameters
            if (this.layer) {
                return this.doLoad()
            } else {
                if (this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED]) {
                    this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED] = () => {
                        this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED]()
                        this.doLoad()
                    }
                } else {
                    this.events[DefaultBaseLayerImpl.EVENT_NAMES.CREATED] = this.doLoad
                }
            }
        }

        // 执行数据加载方法
        private doLoad (): BaseLayer {
            AuthRestUtil.postAjax(this.loadDataUrl, this.dataPrameters, (result) => {
                this.data = result
                // 执行数据加载完成后回调
                if (!Validate.validateNull(this.events[DefaultBaseLayerImpl.EVENT_NAMES.DATA_LOADED])) {
                    this.events[DefaultBaseLayerImpl.EVENT_NAMES.DATA_LOADED](this)
                }
                // 执行描画函数
                this.draw()
            }, (error) => {
                console.log(error)
            })
            return this;
        }

        /**
         * 描画函数
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        draw(): com.clouddo.ui.gis.BaseLayer {
            return this;
        }

    }
}