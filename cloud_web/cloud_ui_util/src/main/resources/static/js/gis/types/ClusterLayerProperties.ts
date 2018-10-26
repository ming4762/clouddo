namespace com.clouddo.ui.gis.types {
    export interface ClusterLayerProperties {
        // 图层ID
        id: string,
        // 图层名称
        name: string,
        // x坐标对应的字段名
        xField?: string,
        // y坐标对应字段名
        yField?: string,
        // 距离
        distance?: number,
        // 单个集群最大数
        maxSingles?: number,
        // 标注颜色
        labelColor?: string,
        // 是否显示单个对象
        showSingles?: boolean,
        // 请求数据的url
        loadDataUrl?: string,
        // 聚合样式
        symbolLimit?: {[index: number]: any},
        // 单个点样式
        singleSymbol?: any
    }
}