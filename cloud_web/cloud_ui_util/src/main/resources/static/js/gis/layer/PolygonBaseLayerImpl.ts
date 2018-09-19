/**
 * 面图层实现类
 */
namespace com.clouddo.ui.gis {
    import DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;

    declare let require: any

    export class PolygonBaseLayerImpl extends DefaultBaseLayerImpl implements BaseLayer {
        private static LAYER_TYPE: string = 'polygon'

        constructor (id: string, name : string, map: any, url : string) {
           super(id, name, PolygonBaseLayerImpl.LAYER_TYPE, map, url)
        }

        /**
         * 重写描画方法
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        draw(): com.clouddo.ui.gis.BaseLayer {
            let dataList = this.data
            if (dataList) {
                require([
                    'esri/Graphic'
                ], (Graphic) => {
                    // TODO 待完善
                })
            }
        }
    }
}