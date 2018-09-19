
namespace com.clouddo.ui.gis {

    import Validate = com.clouddo.ui.util.Validate;
    import DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
    declare let require: any
    // 声明jquery
    declare let $: any
    /**
     * 点图层的默认实现
     */
    export class PointBaseLayerImpl extends DefaultBaseLayerImpl implements BaseLayer {

        private static LAYER_TYPE: string = 'point'
        protected legeng : string;

        constructor (id : string, name : string, view: any, url : string) {
            super(id, name, PointBaseLayerImpl.LAYER_TYPE, view, url)
        }

        /**
         * 重写描画方法
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        draw(): com.clouddo.ui.gis.BaseLayer {
            require([
                'esri/Graphic',
                'esri/geometry/Point',
                'esri/symbols/PictureMarkerSymbol'
            ], (Graphic, Point, PictureMarkerSymbol) => {
                let datas = this.data
                if (datas) {
                    $.each(datas, (key, data) => {
                        // 创建点
                        let point = new Point({
                            x: data['lng'],
                            y: data['lat']
                        })
                        // 创建样式
                        // 1、从数据中获取样式
                        // 2、如果不存在，从数据中获取legeng
                        // 3、如果不存在，从图层中获取legeng
                        // 4、如果不存在，使用默认的样式
                        let symbol = data.symbol
                        if (Validate.validateNull(symbol)) {
                            let legeng = data.legeng
                            if (!Validate.validateNull(legeng)) {
                                symbol = new PictureMarkerSymbol({
                                    url :  legeng,
                                    height : 24,
                                    width : 24
                                })
                            }
                        }
                        if (Validate.validateNull(symbol)) {
                            let legeng = this.legeng
                            if (!Validate.validateNull(legeng)) {
                                symbol = new PictureMarkerSymbol({
                                    url :  legeng,
                                    height : 24,
                                    width : 24
                                })
                            }
                        }
                        if (Validate.validateNull(symbol)) {
                            symbol = {
                                type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
                                color: [226, 119, 40],
                                outline: { // autocasts as new SimpleLineSymbol()
                                    color: [255, 255, 255],
                                    width: 2
                                }
                            }
                        }
                        // 设置属性
                        let attributes = {
                            data: data,
                            layer: this
                        }
                        // 创建元素
                        let graphic = new Graphic({
                            attributes : attributes,
                            geometry : point,
                            symbol : symbol,
                            id : data.id
                        })
                        // 添加元素
                        this.layer.add(graphic)
                    })
                }
                // 描画完成后事件
                if (!Validate.validateNull(this.events[PointBaseLayerImpl.EVENT_NAMES.DRAWED])) {
                    this.events[PointBaseLayerImpl.EVENT_NAMES.DRAWED](this)
                }
            })
            return this;
        }
    }
}