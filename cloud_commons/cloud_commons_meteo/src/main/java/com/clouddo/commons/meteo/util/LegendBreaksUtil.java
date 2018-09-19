package com.clouddo.commons.meteo.util;

import com.clouddo.commons.meteo.config.model.LegendBreak;
import org.meteoinfo.legend.ColorBreak;
import org.meteoinfo.legend.PolygonBreak;
import org.meteoinfo.legend.PolylineBreak;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhongming
 * @since 1.0
 * 2018/7/2上午8:38
 */
public class LegendBreaksUtil {


    /**
     * 获取默认的24小时雨量等值线breaks
     * @return
     */
    public static List<ColorBreak> default24RainPolylineBreaks() {
        List<ColorBreak> breaks = new ArrayList<ColorBreak>();
        List<LegendBreak> legendBreakList = default24RainBreaks();
        if(legendBreakList != null) {
            for(LegendBreak legendBreak : legendBreakList) {
                breaks.add(convertLegendBreakToPolylineBreak(legendBreak));
            }
        }
        return breaks;
    }

    /**
     * 获取默认的24小时雨量等值面breaks
     * @return
     */
    public static List<ColorBreak> default24RainPolygonBreaks() {
        List<ColorBreak> breaks = new ArrayList<ColorBreak>();


        List<LegendBreak> legendBreakList = default24RainBreaks();
        if(legendBreakList != null) {
            for(LegendBreak legendBreak : legendBreakList) {
                breaks.add(convertLegendBreakToPolygonBreak(legendBreak));
            }
        }
        return breaks;
    }

    /**
     * 获取24小时雨里默认分割点
     * @return
     */
    private static List<LegendBreak> default24RainBreaks() {
        List<LegendBreak> legendBreakList = new ArrayList<LegendBreak>();
        legendBreakList.add(new LegendBreak(0.0, 0.1, new Color(255, 255, 255)));
        legendBreakList.add(new LegendBreak(0.1, 10.0, new Color(127, 255, 255)));
        legendBreakList.add(new LegendBreak(10.0, 25.0, new Color(35, 183, 255)));
        legendBreakList.add(new LegendBreak(25.0, 50.0, new Color(0, 120, 180)));
        legendBreakList.add(new LegendBreak(50.0, 100.0, new Color(0, 39, 185)));
        legendBreakList.add(new LegendBreak(100.0, 250.0, new Color(150, 0, 250)));
        legendBreakList.add(new LegendBreak(250.0, 10000.0, new Color(85, 0, 143)));
        return legendBreakList;
    }

    /**
     * 批量将等值线面分割点转为等值面分割点
     * @param legendBreakList
     * @return
     */
    public static List<ColorBreak> batchConvertLegendBreakToPolygonBreak(List<LegendBreak> legendBreakList) {
        List<ColorBreak> breaks = new ArrayList<ColorBreak>();
        if(legendBreakList != null) {
            for(LegendBreak legendBreak : legendBreakList) {
                breaks.add(convertLegendBreakToPolygonBreak(legendBreak));
            }
        }
        return breaks;
    }

    /**
     * 批量将等值线面分割点转为等值线分割点
     * @param legendBreakList
     * @return
     */
    public static List<ColorBreak> batchConvertLegendBreakToPolylineBreak(List<LegendBreak> legendBreakList) {
        List<ColorBreak> breaks = new ArrayList<ColorBreak>();
        if(legendBreakList != null) {
            for(LegendBreak legendBreak : legendBreakList) {
                breaks.add(convertLegendBreakToPolylineBreak(legendBreak));
            }
        }
        return breaks;
    }

    /**
     * 将等值线面分割点转为等值面分割点
     * @param legendBreak
     * @return
     */
    public static PolygonBreak convertLegendBreakToPolygonBreak(LegendBreak legendBreak) {
        PolygonBreak polygonBreak = new PolygonBreak();
        polygonBreak.setStartValue(legendBreak.getStartValue());
        polygonBreak.setEndValue(legendBreak.getEndValue());
        polygonBreak.setColor(legendBreak.getColor());
        return polygonBreak;
    }

    /**
     * 将等值线面分割点转为等值线分割点
     * @param legendBreak
     * @return
     */
    public static PolylineBreak convertLegendBreakToPolylineBreak(LegendBreak legendBreak) {
        PolylineBreak polylineBreak = new PolylineBreak();
        polylineBreak.setStartValue(legendBreak.getStartValue());
        polylineBreak.setEndValue(legendBreak.getEndValue());
        return polylineBreak;
    }
}
