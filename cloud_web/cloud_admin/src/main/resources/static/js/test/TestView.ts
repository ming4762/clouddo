import View = com.clouddo.ui.gis.View;

let view
let layer
$('document').ready(function () {
    view = new View('viewElement')
    view.setWith2D3D(true, 'switch-btn')
    view.on(View.EVENT_NAMES.ONE_CREATE, (viewObject) => {
        console.log('one', viewObject)
    })
    view.on(View.EVENT_NAMES.ALL_CREATE, (viewObject) => {
        console.log('all', viewObject)
        // layer = new TestPoint(view.getMap())
        // layer.create()
        // layer.load({})
    })
    view.createView(View.VIEW_3D)

})

namespace com.clouddo.ui.test {
    import PointBaseLayerImpl = com.clouddo.ui.gis.PointBaseLayerImpl;
    import BaseLayer = com.clouddo.ui.gis.BaseLayer;

    export class TestPoint extends PointBaseLayerImpl implements BaseLayer{
        constructor (map: any) {
            super('testLayer', '测试图层', map, '')
            this.legeng = ''
        }
    }
}

