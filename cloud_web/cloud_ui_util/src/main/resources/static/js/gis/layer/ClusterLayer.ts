///<reference path="../types/ClusterLayerProperties.ts"/>
///<reference path="../view/View.ts"/>
///<reference path="../layer/DefaultBaseLayerImpl.ts"/>
///<reference path="../util/CoordinateUtil.ts"/>
///<reference path="../../util/SortUtil.ts"/>

/**
 * 点聚合图层
 */
namespace com.clouddo.ui.gis {
    import ClusterLayerProperties = com.clouddo.ui.gis.types.ClusterLayerProperties
    import View = com.clouddo.ui.gis.View
    import DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl
    import CoordinateUtil = com.clouddo.ui.gis.util.CoordinateUtil
    import SortUtil = com.clouddo.ui.util.SortUtil

    declare let require: any

    export class ClusterLayer extends DefaultBaseLayerImpl implements BaseLayer{

        // 图层参数
        private properties: ClusterLayerProperties

        // 样式分组是否自动的
        private autoSymbolGroup: boolean

        // 空间参考
        private spatialReference: any

        /**
         * 样式分组、集合数分组值
         */
        private symbolArray: any[] = []
        private countLimit: number[] = []

        // 点集群包含的单个点
        private singles: any[] = []

        // 分辨率
        private resolution: number

        // 聚合点
        private clusters: any[]


        constructor (view: View, properties: ClusterLayerProperties) {
            super(properties.id, properties.name, ClusterLayer.LAYER_TYPE.CLUSTER, view, properties.loadDataUrl)
            this.view = view
            this.clusters = []
            this.initProperties(properties)
        }

        /**
         * 设置数据
         * @param data
         */
        public setData (data: any[]): void {
            this.data = data
        }

        /**
         * 重写create方法
         * @param index
         */
        create(index?: number): com.clouddo.ui.gis.BaseLayer {
            super.create(index)

            // 监控缩放
            this.view.getActiveView().watch('zoom', () => {
                this.draw()
            })
            // this.spatialReference = this.view.getActiveView().spatialReference
            return this
        }

        /**
         * 重写clear方法
         * @author zhongming
         * 1、清空图层
         * 2、清空数据
         */
        clear(): com.clouddo.ui.gis.BaseLayer {
            super.clear()
            this.clusters = []
            this.countLimit = []
            this.symbolArray = []
            this.singles = []
            return this
        }

        /**
         * 重写鼠标点击事件
         * @author zhongming
         * @param response
         */
        clickGraphic(response: any): void {
            console.log(response)
        }

        /**
         * 重写鼠标移动事件
         * @author zhongming
         * @param response
         */
        pointerMove(response: any): void {
            let graphics = response.results[0].graphic
            this.addSingles(graphics)
        }

        /**
         * 重写draw方法
         */
        draw(): com.clouddo.ui.gis.BaseLayer {
            // 清空图层
            this.clear()
            // 计算分辨率
            this.calculateResolution()
            // 创建集群
            this.clusterGraphics()
            // 描画集群
            if (this.clusters) {
                // 如果 样式自动分组，则重新分组
                // if (this.autoSymbolGroup) {
                //     this.groupData()
                // }
                // 对样式进行分组
                this.groupSymbol()
                require([
                    'esri/Graphic'
                ], (Graphic) => {
                    let graphicList: any[] = []
                    this.clusters.forEach(cluster => {
                        // 获取集合点数
                        let count = cluster.attributes.data.clusterCount
                        let symbol = this.getSymbol(count)
                        let graphic = new Graphic({
                            geometry: {
                                type: 'point',
                                longitude: cluster.x,
                                latitude: cluster.y
                            },
                            symbol: symbol,
                            attributes: cluster.attributes
                        })
                        // TODO 字体样式可配置
                        let textGraphic = new Graphic({
                            geometry: {
                                type: 'point',
                                longitude: cluster.x,
                                latitude: cluster.y
                            },
                            symbol: {
                                type: "text",  // autocasts as new TextSymbol()
                                color: "white",
                                haloColor: "black",
                                haloSize: "1px",
                                text: cluster.attributes.data.clusterCount,
                                xoffset: 3,
                                yoffset: 3,
                                font: {  // autocast as new Font()
                                    size: 12,
                                    family: "sans-serif",
                                    weight: "bold"
                                }
                            },
                            attributes: cluster.attributes
                        })
                        graphicList.push(graphic)
                        graphicList.push(textGraphic)
                    })
                    // 将图层添加到图层
                    this.layer.addMany(graphicList)
                })
            }
            return this
        }

// ---------- 私有方法 ----------------

        /**
         * 获取聚合点的样式
         * @author zhongming
         * @param count 集合数
         */
        private getSymbol (count: number): any {
            let symbol: any = {}
            for (let i=0; i<this.countLimit.length; i++) {
                let limit = this.countLimit[i]
                if (count <= limit) {
                    symbol = this.symbolArray[i]
                    break
                }
            }
            return symbol
        }

        /**
         * 获取样式分组
         * 按照集合数分组
         * @author zhongming
         */
        private groupData (): void {
            // 最大组 集群数
            let max = this.clusters[0].attributes.data.clusterCount
            // 最小组 集群数
            let min = this.clusters[0].attributes.data.clusterCount
            this.clusters.forEach(cluster => {
                let count = cluster.attributes.data.clusterCount
                if (count > max) max = count 
                if (count < min) min = count
            })
            let keys = Object.keys(this.properties.symbolLimit)
            let dValue = (max - min) / keys.length
            let symbolLimit = {}
            symbolLimit[min + dValue] = this.properties.symbolLimit[keys[0]]
            symbolLimit[max - dValue] = this.properties.symbolLimit[keys[1]]
            symbolLimit[max + 1] = this.properties.symbolLimit[keys[2]]
            this.properties.symbolLimit = symbolLimit
        }

        /**
         * 对symbol进行分组
         */
        private groupSymbol (): void {
            // 获取分组限值，并排序
            let keys: number[] = Object.keys(this.properties.symbolLimit).map(Number)
            keys = SortUtil.sort(keys)
            this.countLimit = keys
            // 获取symbolArray
            for (let key of keys) {
                this.symbolArray.push(this.properties.symbolLimit[key])
            }
        }

        /**
         * 初始化参数
         * @author zhongming
         * @param properties 参数
         */
        private initProperties (properties: ClusterLayerProperties): void {
            this.properties = {
                id: 'clusterLayer',
                name: '点聚合图层',
                distance: 50,
                maxSingles: 1000,
                labelColor: '#FFF',
                showSingles:  true,
                singleSymbol: {
                    type: 'simple-marker',
                    size: '3px',
                    style: 'circle',
                    color: '#808080',
                    outline: {
                        width: 0
                    }
                },
            }
            // 替换默认选项
            if (properties) {
                for (let key in properties) {
                    this.properties[key] = properties[key]
                }
            }
            // 设置默认的聚合样式
            if (!this.properties.symbolLimit) {
                let redSymbol: any = {
                    type: 'simple-marker',
                    size: '30px',
                    style: 'circle',
                    color: '#FF0000',
                    outline: {
                        width: 0
                    }
                }
                let blueSymbol: any = {
                    type: 'simple-marker',
                    size: '20px',
                    style: 'circle',
                    color: '#0000FF',
                    outline: {
                        width: 0
                    }
                }
                let greenSymbol: any = {
                    type: 'simple-marker',
                    size: '15px',
                    style: 'circle',
                    color: '#008000',
                    outline: {
                        width: 0
                    }
                }
                this.properties.symbolLimit = {
                    5: greenSymbol,
                    20: blueSymbol,
                    1000: redSymbol
                }
            }
        }

        /**
         * 计算分辨率
         * @author zhongming
         */
        private calculateResolution (): void {
            // 获取视图边界
            let extent = this.view.getActiveView().extent
            let xmax = extent.xmax
            let xmin = extent.xmin
            // 如果坐标系是wgs84，则转换为墨卡托
            if (!this.view.getActiveView().spatialReference.isWebMercator && this.view.getActiveView().spatialReference.isWGS84) {
                xmax = CoordinateUtil.wgs842Mercator(extent.xmax, extent.ymax)
                xmin = CoordinateUtil.wgs842Mercator(extent.xmin, extent.ymin)
            }
            this.resolution = (xmax - xmin) / this.view.getActiveView().width
        }

        /**
         * 初始化方法
         * @author zhongming
         */
        private init (): void {
        }

        /**
         * 移除单点
         */
        private removeSingles (): void {
            if (this.singles) {
                this.layer.removeMany(this.singles)
                this.singles = []
            }
        }

        /**
         * 显示单点
         * @param graphic 集群点
         */
        private addSingles (graphic: any): void {
            // 移除单点
            this.removeSingles()
            // 获取单个点信息
            let singles = graphic.attributes.data.pointList
            if (singles) {
                require([
                    'esri/Graphic'
                ], (Graphic) => {
                    singles.forEach(single => {
                        let singlesGraphic = new Graphic({
                            geometry: {
                                type: 'point',
                                longitude: single.x,
                                latitude: single.y
                            },
                            symbol: this.properties.singleSymbol,
                            attributes: graphic.attributes
                        })
                        this.singles.push(singlesGraphic)
                    })
                    this.layer.addMany(this.singles)
                })
            }
        }

        /**
         * 创建聚合点
         * @author zhongming
         */
        private clusterGraphics (): void {
            if (this.data) {
                this.data.forEach(point => {
                    // 标识当前点是否在已经聚合的点内
                    let clustered = false
                    // 判断当前点是否在已经聚合的点内
                    for (let i=0; i<this.clusters.length; i++) {
                        let cluster = this.clusters[i]
                        // 判断是否在同一集群
                        if (this.estimateInCluster(point, cluster)) {
                            clustered = true
                            // 将点添加集群内
                            this.clusterAddPoint(point, cluster)
                            break
                        }
                    }
                    // 不再已经聚合的点内，创建新的聚合点
                    if (!clustered) {
                        this.createClusteredPoint(point)
                    }
                })
            }
        }

        /**
         * 将点添加到集群内
         * @param point
         * @param cluster
         */
        private clusterAddPoint (point: any, cluster: any): void {
            let count: number = cluster.attributes.data.clusterCount
            let x:number, y: number
            // 重新计算聚合点位置
            x = (point.x + (cluster.x * count)) / (count + 1)
            y = (point.y + (cluster.y * count)) / (count + 1)

            cluster.x = x
            cluster.y = y

            // 创建集群的范围
            if (point.x < cluster.attributes.extent[0]) {
                cluster.attributes.extent[0] = point.x;
            } else if (point.x > cluster.attributes.extent[2]) {
                cluster.attributes.extent[2] = point.x;
            }
            if (point.y < cluster.attributes.extent[1]) {
                cluster.attributes.extent[1] = point.y;
            }
            else if (point.y > cluster.attributes.extent[3]) {
                cluster.attributes.extent[3] = point.y;
            }
            // 设置集群的点个数
            cluster.attributes.data.clusterCount ++
            // 添加点
            cluster.attributes.data.pointList.push(point)
        }

        /**
         * 创建聚合点
         * @author zhongming
         * @param point 点数据
         */
        private createClusteredPoint (point: any): void {
            let attributes = {
                data: {
                    clusterId: this.clusters.length + 1,
                    clusterCount: 1,
                    pointList: [point]
                },
                extent: [point.x, point.y, point.x, point.y],
                layer: this
            }
            let cluster: any = {
                x: point.x,
                y: point.y,
                attributes: attributes
            }
            this.clusters.push(cluster)
        }

        /**
         * 判定点是否在聚合点内
         * @author zhongming
         * @param point 点
         * @param cluster 聚合点
         */
        private estimateInCluster (point: any, cluster: any): boolean {
            // 将点的经纬度转为墨卡托
            let pointXY: number[] = CoordinateUtil.wgs842Mercator(point.x, point.y)
            let clusterXY: number[] = CoordinateUtil.wgs842Mercator(cluster.x, cluster.y)
            // 计算距离
            let distance: number = Math.sqrt(
                Math.pow((clusterXY[0] - pointXY[0]), 2) + Math.pow((clusterXY[1] - pointXY[1]), 2)
            ) / this.resolution
            return distance <= this.properties.distance
        }
    }
}



