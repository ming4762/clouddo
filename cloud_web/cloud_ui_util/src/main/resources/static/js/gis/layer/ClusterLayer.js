///<reference path="../types/ClusterLayerProperties.ts"/>
///<reference path="../view/View.ts"/>
///<reference path="../layer/DefaultBaseLayerImpl.ts"/>
///<reference path="../util/CoordinateUtil.ts"/>
///<reference path="../../util/SortUtil.ts"/>
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
/**
 * 点聚合图层
 */
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var gis;
            (function (gis) {
                var DefaultBaseLayerImpl = com.clouddo.ui.gis.DefaultBaseLayerImpl;
                var CoordinateUtil = com.clouddo.ui.gis.util.CoordinateUtil;
                var SortUtil = com.clouddo.ui.util.SortUtil;
                var ClusterLayer = /** @class */ (function (_super) {
                    __extends(ClusterLayer, _super);
                    function ClusterLayer(view, properties) {
                        var _this = _super.call(this, properties.id, properties.name, ClusterLayer.LAYER_TYPE.CLUSTER, view, properties.loadDataUrl) || this;
                        /**
                         * 样式分组、集合数分组值
                         */
                        _this.symbolArray = [];
                        _this.countLimit = [];
                        // 点集群包含的单个点
                        _this.singles = [];
                        _this.view = view;
                        _this.clusters = [];
                        _this.initProperties(properties);
                        return _this;
                    }
                    /**
                     * 设置数据
                     * @param data
                     */
                    ClusterLayer.prototype.setData = function (data) {
                        this.data = data;
                    };
                    /**
                     * 重写create方法
                     * @param index
                     */
                    ClusterLayer.prototype.create = function (index) {
                        var _this = this;
                        _super.prototype.create.call(this, index);
                        // 监控缩放
                        this.view.getActiveView().watch('zoom', function () {
                            _this.draw();
                        });
                        // this.spatialReference = this.view.getActiveView().spatialReference
                        return this;
                    };
                    /**
                     * 重写clear方法
                     * @author zhongming
                     * 1、清空图层
                     * 2、清空数据
                     */
                    ClusterLayer.prototype.clear = function () {
                        _super.prototype.clear.call(this);
                        this.clusters = [];
                        this.countLimit = [];
                        this.symbolArray = [];
                        this.singles = [];
                        return this;
                    };
                    /**
                     * 重写鼠标点击事件
                     * @author zhongming
                     * @param response
                     */
                    ClusterLayer.prototype.clickGraphic = function (response) {
                        console.log(response);
                    };
                    /**
                     * 重写鼠标移动事件
                     * @author zhongming
                     * @param response
                     */
                    ClusterLayer.prototype.pointerMove = function (response) {
                        var graphics = response.results[0].graphic;
                        this.addSingles(graphics);
                    };
                    /**
                     * 重写draw方法
                     */
                    ClusterLayer.prototype.draw = function () {
                        var _this = this;
                        // 清空图层
                        this.clear();
                        // 计算分辨率
                        this.calculateResolution();
                        // 创建集群
                        this.clusterGraphics();
                        // 描画集群
                        if (this.clusters) {
                            // 如果 样式自动分组，则重新分组
                            // if (this.autoSymbolGroup) {
                            //     this.groupData()
                            // }
                            // 对样式进行分组
                            this.groupSymbol();
                            require([
                                'esri/Graphic'
                            ], function (Graphic) {
                                var graphicList = [];
                                _this.clusters.forEach(function (cluster) {
                                    // 获取集合点数
                                    var count = cluster.attributes.data.clusterCount;
                                    var symbol = _this.getSymbol(count);
                                    var graphic = new Graphic({
                                        geometry: {
                                            type: 'point',
                                            longitude: cluster.x,
                                            latitude: cluster.y
                                        },
                                        symbol: symbol,
                                        attributes: cluster.attributes
                                    });
                                    // TODO 字体样式可配置
                                    var textGraphic = new Graphic({
                                        geometry: {
                                            type: 'point',
                                            longitude: cluster.x,
                                            latitude: cluster.y
                                        },
                                        symbol: {
                                            type: "text",
                                            color: "white",
                                            haloColor: "black",
                                            haloSize: "1px",
                                            text: cluster.attributes.data.clusterCount,
                                            xoffset: 3,
                                            yoffset: 3,
                                            font: {
                                                size: 12,
                                                family: "sans-serif",
                                                weight: "bold"
                                            }
                                        },
                                        attributes: cluster.attributes
                                    });
                                    graphicList.push(graphic);
                                    graphicList.push(textGraphic);
                                });
                                // 将图层添加到图层
                                _this.layer.addMany(graphicList);
                            });
                        }
                        return this;
                    };
                    // ---------- 私有方法 ----------------
                    /**
                     * 获取聚合点的样式
                     * @author zhongming
                     * @param count 集合数
                     */
                    ClusterLayer.prototype.getSymbol = function (count) {
                        var symbol = {};
                        for (var i = 0; i < this.countLimit.length; i++) {
                            var limit = this.countLimit[i];
                            if (count <= limit) {
                                symbol = this.symbolArray[i];
                                break;
                            }
                        }
                        return symbol;
                    };
                    /**
                     * 获取样式分组
                     * 按照集合数分组
                     * @author zhongming
                     */
                    ClusterLayer.prototype.groupData = function () {
                        // 最大组 集群数
                        var max = this.clusters[0].attributes.data.clusterCount;
                        // 最小组 集群数
                        var min = this.clusters[0].attributes.data.clusterCount;
                        this.clusters.forEach(function (cluster) {
                            var count = cluster.attributes.data.clusterCount;
                            if (count > max)
                                max = count;
                            if (count < min)
                                min = count;
                        });
                        var keys = Object.keys(this.properties.symbolLimit);
                        var dValue = (max - min) / keys.length;
                        var symbolLimit = {};
                        symbolLimit[min + dValue] = this.properties.symbolLimit[keys[0]];
                        symbolLimit[max - dValue] = this.properties.symbolLimit[keys[1]];
                        symbolLimit[max + 1] = this.properties.symbolLimit[keys[2]];
                        this.properties.symbolLimit = symbolLimit;
                    };
                    /**
                     * 对symbol进行分组
                     */
                    ClusterLayer.prototype.groupSymbol = function () {
                        // 获取分组限值，并排序
                        var keys = Object.keys(this.properties.symbolLimit).map(Number);
                        keys = SortUtil.sort(keys);
                        this.countLimit = keys;
                        // 获取symbolArray
                        for (var _i = 0, keys_1 = keys; _i < keys_1.length; _i++) {
                            var key = keys_1[_i];
                            this.symbolArray.push(this.properties.symbolLimit[key]);
                        }
                    };
                    /**
                     * 初始化参数
                     * @author zhongming
                     * @param properties 参数
                     */
                    ClusterLayer.prototype.initProperties = function (properties) {
                        this.properties = {
                            id: 'clusterLayer',
                            name: '点聚合图层',
                            distance: 50,
                            maxSingles: 1000,
                            labelColor: '#FFF',
                            showSingles: true,
                            singleSymbol: {
                                type: 'simple-marker',
                                size: '3px',
                                style: 'circle',
                                color: '#808080',
                                outline: {
                                    width: 0
                                }
                            },
                        };
                        // 替换默认选项
                        if (properties) {
                            for (var key in properties) {
                                this.properties[key] = properties[key];
                            }
                        }
                        // 设置默认的聚合样式
                        if (!this.properties.symbolLimit) {
                            var redSymbol = {
                                type: 'simple-marker',
                                size: '30px',
                                style: 'circle',
                                color: '#FF0000',
                                outline: {
                                    width: 0
                                }
                            };
                            var blueSymbol = {
                                type: 'simple-marker',
                                size: '20px',
                                style: 'circle',
                                color: '#0000FF',
                                outline: {
                                    width: 0
                                }
                            };
                            var greenSymbol = {
                                type: 'simple-marker',
                                size: '15px',
                                style: 'circle',
                                color: '#008000',
                                outline: {
                                    width: 0
                                }
                            };
                            this.properties.symbolLimit = {
                                5: greenSymbol,
                                20: blueSymbol,
                                1000: redSymbol
                            };
                        }
                    };
                    /**
                     * 计算分辨率
                     * @author zhongming
                     */
                    ClusterLayer.prototype.calculateResolution = function () {
                        // 获取视图边界
                        var extent = this.view.getActiveView().extent;
                        var xmax = extent.xmax;
                        var xmin = extent.xmin;
                        // 如果坐标系是wgs84，则转换为墨卡托
                        if (!this.view.getActiveView().spatialReference.isWebMercator && this.view.getActiveView().spatialReference.isWGS84) {
                            xmax = CoordinateUtil.wgs842Mercator(extent.xmax, extent.ymax);
                            xmin = CoordinateUtil.wgs842Mercator(extent.xmin, extent.ymin);
                        }
                        this.resolution = (xmax - xmin) / this.view.getActiveView().width;
                    };
                    /**
                     * 初始化方法
                     * @author zhongming
                     */
                    ClusterLayer.prototype.init = function () {
                    };
                    /**
                     * 移除单点
                     */
                    ClusterLayer.prototype.removeSingles = function () {
                        if (this.singles) {
                            this.layer.removeMany(this.singles);
                            this.singles = [];
                        }
                    };
                    /**
                     * 显示单点
                     * @param graphic 集群点
                     */
                    ClusterLayer.prototype.addSingles = function (graphic) {
                        var _this = this;
                        // 移除单点
                        this.removeSingles();
                        // 获取单个点信息
                        var singles = graphic.attributes.data.pointList;
                        if (singles) {
                            require([
                                'esri/Graphic'
                            ], function (Graphic) {
                                singles.forEach(function (single) {
                                    var singlesGraphic = new Graphic({
                                        geometry: {
                                            type: 'point',
                                            longitude: single.x,
                                            latitude: single.y
                                        },
                                        symbol: _this.properties.singleSymbol,
                                        attributes: graphic.attributes
                                    });
                                    _this.singles.push(singlesGraphic);
                                });
                                _this.layer.addMany(_this.singles);
                            });
                        }
                    };
                    /**
                     * 创建聚合点
                     * @author zhongming
                     */
                    ClusterLayer.prototype.clusterGraphics = function () {
                        var _this = this;
                        if (this.data) {
                            this.data.forEach(function (point) {
                                // 标识当前点是否在已经聚合的点内
                                var clustered = false;
                                // 判断当前点是否在已经聚合的点内
                                for (var i = 0; i < _this.clusters.length; i++) {
                                    var cluster = _this.clusters[i];
                                    // 判断是否在同一集群
                                    if (_this.estimateInCluster(point, cluster)) {
                                        clustered = true;
                                        // 将点添加集群内
                                        _this.clusterAddPoint(point, cluster);
                                        break;
                                    }
                                }
                                // 不再已经聚合的点内，创建新的聚合点
                                if (!clustered) {
                                    _this.createClusteredPoint(point);
                                }
                            });
                        }
                    };
                    /**
                     * 将点添加到集群内
                     * @param point
                     * @param cluster
                     */
                    ClusterLayer.prototype.clusterAddPoint = function (point, cluster) {
                        var count = cluster.attributes.data.clusterCount;
                        var x, y;
                        // 重新计算聚合点位置
                        x = (point.x + (cluster.x * count)) / (count + 1);
                        y = (point.y + (cluster.y * count)) / (count + 1);
                        cluster.x = x;
                        cluster.y = y;
                        // 创建集群的范围
                        if (point.x < cluster.attributes.extent[0]) {
                            cluster.attributes.extent[0] = point.x;
                        }
                        else if (point.x > cluster.attributes.extent[2]) {
                            cluster.attributes.extent[2] = point.x;
                        }
                        if (point.y < cluster.attributes.extent[1]) {
                            cluster.attributes.extent[1] = point.y;
                        }
                        else if (point.y > cluster.attributes.extent[3]) {
                            cluster.attributes.extent[3] = point.y;
                        }
                        // 设置集群的点个数
                        cluster.attributes.data.clusterCount++;
                        // 添加点
                        cluster.attributes.data.pointList.push(point);
                    };
                    /**
                     * 创建聚合点
                     * @author zhongming
                     * @param point 点数据
                     */
                    ClusterLayer.prototype.createClusteredPoint = function (point) {
                        var attributes = {
                            data: {
                                clusterId: this.clusters.length + 1,
                                clusterCount: 1,
                                pointList: [point]
                            },
                            extent: [point.x, point.y, point.x, point.y],
                            layer: this
                        };
                        var cluster = {
                            x: point.x,
                            y: point.y,
                            attributes: attributes
                        };
                        this.clusters.push(cluster);
                    };
                    /**
                     * 判定点是否在聚合点内
                     * @author zhongming
                     * @param point 点
                     * @param cluster 聚合点
                     */
                    ClusterLayer.prototype.estimateInCluster = function (point, cluster) {
                        // 将点的经纬度转为墨卡托
                        var pointXY = CoordinateUtil.wgs842Mercator(point.x, point.y);
                        var clusterXY = CoordinateUtil.wgs842Mercator(cluster.x, cluster.y);
                        // 计算距离
                        var distance = Math.sqrt(Math.pow((clusterXY[0] - pointXY[0]), 2) + Math.pow((clusterXY[1] - pointXY[1]), 2)) / this.resolution;
                        return distance <= this.properties.distance;
                    };
                    return ClusterLayer;
                }(DefaultBaseLayerImpl));
                gis.ClusterLayer = ClusterLayer;
            })(gis = ui.gis || (ui.gis = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
