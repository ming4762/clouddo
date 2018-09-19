
namespace com.clouddo.ui.gis {
    /**
     * 图层接口
     */
    export interface BaseLayer {

        // 图层ID
        id: string
        // arcgis图层实体
        layer: any
        // 类型
        type: string

        /**
         * 事件
         * @param {string} eventName 事件名称
         * @param {Function} eventFunction 事件方法
         */
        on (eventName: string, eventFunction: Function): BaseLayer

        /**
         * 清除图层
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        clear (): BaseLayer

        /**
         * 显示隐藏图层
         * @param {boolean} showHide
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        showHide (showHide: boolean): BaseLayer

        /**
         * 创建图层
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        create (): BaseLayer

        /**
         * 加载数据
         * @param {{[p: string]: any}} parameters
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        load (parameters: {[index: string]: any}): BaseLayer

        /**
         * 描画函数
         * @returns {com.clouddo.ui.gis.BaseLayer}
         */
        draw (): BaseLayer

        /**
         * 鼠标点击事件
         * @param response
         */
        clickGraphic (response: any): void

        /**
         * 鼠标移动事件
         * @param response
         */
        pointerMove (response: any): void

    }
}