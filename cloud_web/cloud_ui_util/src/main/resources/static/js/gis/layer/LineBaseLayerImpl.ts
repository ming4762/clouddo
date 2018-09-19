
namespace com.clouddo.ui.gis {

    // 声明require
    import Validate = com.clouddo.ui.util.Validate;
    import DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
    declare let require: any
    // 声明jquery
    declare let $: any
    /**
     * 线图层实现类
     */
    export class LineBaseLayerImpl extends DefaultBaseLayerImpl implements BaseLayer {

        private static LAYER_TYPE: string = 'line'

        constructor (id : string, name : string, view: any, url : string) {
            super(id, name, LineBaseLayerImpl.LAYER_TYPE, view, url)
        }

        /**
         * 重写描画方法
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        draw(): com.clouddo.ui.gis.BaseLayer {
            require([
                'esri/Graphic',
                'esri/geometry/Polyline'
            ], (Graphic, Polyline) => {
                let datas = this.data
                if (datas) {
                    $.each(datas, (key, data) => {
                        // 从数据中获取样式
                        let symbol = data['symbol']
                        // 如果不存在，获取图层的默认样式
                        if (Validate.validateNull(symbol)) {
                            symbol = this['symbol']
                        }
                        // 如果不存在，使用默认的样式
                        if (Validate.validateNull(symbol)) {
                            symbol = {
                                type : "simple-line",
                                color : "#1e9fff",
                                width : 4
                            }
                        }
                        //创建线
                        let polyline = new Polyline({
                            paths: data.paths,
                        });
                        // 创建元素
                        let graphic2D = new Graphic({
                            geometry: polyline,
                            symbol: symbol,
                            attributes : {data : data, layer : this}
                        });
                        this.layer.add(graphic2D)
                    })
                }
                // 描画完成后事件
                if (!Validate.validateNull(this.events[LineBaseLayerImpl.EVENT_NAMES.DRAWED])) {
                    this.events[LineBaseLayerImpl.EVENT_NAMES.DRAWED](this)
                }
            })
            return this;
        }
    }
}