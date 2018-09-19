package com.clouddo.commons.meteo.util;

import com.clouddo.commons.meteo.config.IsosurfaceConfigCreater;
import com.clouddo.commons.meteo.config.model.IsosurfaceConfig;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.StationData;
import org.meteoinfo.data.mapdata.MapDataManage;
import org.meteoinfo.data.meteodata.DrawMeteoData;
import org.meteoinfo.data.meteodata.GridDataSetting;
import org.meteoinfo.geoprocess.analysis.InterpolationMethods;
import org.meteoinfo.geoprocess.analysis.InterpolationSetting;
import org.meteoinfo.layer.VectorLayer;
import org.meteoinfo.legend.ColorBreak;
import org.meteoinfo.legend.LegendManage;
import org.meteoinfo.legend.LegendScheme;
import org.meteoinfo.legend.LegendType;
import org.meteoinfo.shape.ShapeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 气象描画工具类
 * @author zhongming
 * @since 3.0
 * 2018/7/2上午8:52
 */
public class MeteoDrawUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteoDrawUtil.class);

    /**
     * 获取24小时雨量等值面
     * @param stationData 站点数据
     * @return 等值面图层
     */
    public static VectorLayer createRainShadedLayer(StationData stationData) {

        return createShadedLayer(stationData, LegendBreaksUtil.default24RainPolygonBreaks(), IsosurfaceConfigCreater.createDefaultIsosurfaceConfig());
    }

    /**
     * 获取24小时雨量等值面
     * @param stationData 站点数据
     * @param borderShapePath 边界路径
     * @return 等值面图层
     */
    public static VectorLayer createRainShadedLayer(StationData stationData, String borderShapePath) {
        IsosurfaceConfig isosurfaceConfig = IsosurfaceConfigCreater.createDefaultIsosurfaceConfig();
        isosurfaceConfig.setBorderShapePath(borderShapePath);
        return createShadedLayer(stationData, LegendBreaksUtil.default24RainPolygonBreaks(), isosurfaceConfig);
    }

    /**
     * 获取24小时雨量等值线
     * @param stationData 站点数据
     * @param borderShapePath 边界路径
     * @return 等值面图层
     */
    public static VectorLayer createRainContourLayer(StationData stationData, String borderShapePath) {
        IsosurfaceConfig isosurfaceConfig = IsosurfaceConfigCreater.createDefaultIsosurfaceConfig();
        isosurfaceConfig.setBorderShapePath(borderShapePath);
        return createContourLayer(stationData, LegendBreaksUtil.default24RainPolylineBreaks(), isosurfaceConfig);
    }

    /**
     * 生成等值面图层
     * @param stationData 站点数据
     * @param breaks 颜色分级
     * @param isosurfaceConfig 等值线配置
     * @return 等值面图层
     */
    public static VectorLayer createShadedLayer(StationData stationData, List<ColorBreak> breaks, IsosurfaceConfig isosurfaceConfig) {
        //创建格点数据配置
        GridDataSetting gridDataSetting = new GridDataSetting();
        //创建边界 TODO 待健壮性完善，异常处理
        VectorLayer borderLayer = null;
        if(!StringUtils.isEmpty(isosurfaceConfig.getBorderShapePath())) {
            try {
                borderLayer = MapDataManage.readMapFile_ShapeFile(isosurfaceConfig.getBorderShapePath());
                //TODO 待测试
                gridDataSetting.dataExtent = borderLayer.getExtent();
                stationData.projInfo = borderLayer.getProjInfo();
            } catch (Exception e) {
                LOGGER.warn("shape文件读取失败，shape文件路径：{}", isosurfaceConfig.getBorderShapePath());
                e.printStackTrace();
            }
        }

        //设置格点数
        gridDataSetting.xNum = isosurfaceConfig.getxNum();
        gridDataSetting.yNum = isosurfaceConfig.getyNum();
        if(isosurfaceConfig.getxNum() == null || isosurfaceConfig.getxNum() == 0) {
            gridDataSetting.xNum = 20;
            LOGGER.warn("X轴格点数不存在，设置为默认值20" );
        }
        if(isosurfaceConfig.getyNum() == null || isosurfaceConfig.getyNum() == 0) {
            gridDataSetting.yNum = 20;
            LOGGER.warn("Y轴格点数不存在，设置为默认值20" );
        }



        InterpolationSetting gridInterp = new InterpolationSetting();
        gridInterp.setGridDataSetting(gridDataSetting);

        gridInterp.setInterpolationMethod(InterpolationMethods.IDW_Radius);
        gridInterp.setRadius(isosurfaceConfig.getRadius());
        gridInterp.setMinPointNum(isosurfaceConfig.getMinPointNum());

        //生成格点数据
        GridData gridData = stationData.interpolateData(gridInterp);
        LegendScheme legendScheme = LegendManage.createLegendSchemeFromGridData(gridData, LegendType.GraduatedColor,
                ShapeTypes.Polygon);
        //设置分级
        if(breaks != null) {
            legendScheme.setLegendBreaks(breaks);
        }
        //创建图层
        VectorLayer shadedLayer = new VectorLayer(ShapeTypes.Polygon);
        shadedLayer = DrawMeteoData.createShadedLayer(gridData, legendScheme, "Rain", "Rain", true);

        VectorLayer lastLayer = null;
        //TODO
        if(borderLayer != null && shadedLayer != null) {
            lastLayer = shadedLayer.clip(borderLayer);
        }

        return lastLayer == null ? shadedLayer : lastLayer;

    }


    /**
     * 生成等值线图层
     * @param stationData 站点数据
     * @param breaks 颜色分级
     * @param isosurfaceConfig 等值面配置
     * @return 等值面图层
     */
    public static VectorLayer createContourLayer(StationData stationData, List<ColorBreak> breaks, IsosurfaceConfig isosurfaceConfig) {
        //创建格点数据配置
        GridDataSetting gridDataSetting = new GridDataSetting();
        //创建边界 TODO 待健壮性完善，异常处理
        VectorLayer borderLayer = null;
        if(!StringUtils.isEmpty(isosurfaceConfig.getBorderShapePath())) {
            try {
                borderLayer = MapDataManage.readMapFile_ShapeFile(isosurfaceConfig.getBorderShapePath());
                //TODO 待测试
                gridDataSetting.dataExtent = borderLayer.getExtent();
                stationData.projInfo = borderLayer.getProjInfo();
            } catch (Exception e) {
                LOGGER.warn("shape文件读取失败，shape文件路径：{}", isosurfaceConfig.getBorderShapePath());
                e.printStackTrace();
            }
        }

        //设置格点数
        gridDataSetting.xNum = isosurfaceConfig.getxNum();
        gridDataSetting.yNum = isosurfaceConfig.getyNum();
        if(isosurfaceConfig.getxNum() == null || isosurfaceConfig.getxNum() == 0) {
            gridDataSetting.xNum = 20;
            LOGGER.warn("X轴格点数不存在，设置为默认值20" );
        }
        if(isosurfaceConfig.getyNum() == null || isosurfaceConfig.getyNum() == 0) {
            gridDataSetting.yNum = 20;
            LOGGER.warn("Y轴格点数不存在，设置为默认值20" );
        }



        InterpolationSetting gridInterp = new InterpolationSetting();
        gridInterp.setGridDataSetting(gridDataSetting);

        gridInterp.setInterpolationMethod(InterpolationMethods.IDW_Radius);
        gridInterp.setRadius(isosurfaceConfig.getRadius());
        gridInterp.setMinPointNum(isosurfaceConfig.getMinPointNum());

        //生成格点数据
        GridData gridData = stationData.interpolateData(gridInterp);
        LegendScheme legendScheme = LegendManage.createLegendSchemeFromGridData(gridData, LegendType.GraduatedColor,
                ShapeTypes.Polyline);
        //设置分级
        if(breaks != null) {
            legendScheme.setLegendBreaks(breaks);
        }
        //创建图层
        VectorLayer shadedLayer = new VectorLayer(ShapeTypes.Polyline);
        shadedLayer = DrawMeteoData.createContourLayer(gridData, legendScheme, "Rain", "Rain", true);

        VectorLayer lastLayer = null;
        //TODO
        if(borderLayer != null && shadedLayer != null) {
            lastLayer = shadedLayer.clip(borderLayer);
        }

        return lastLayer == null ? shadedLayer : lastLayer;

    }
}
