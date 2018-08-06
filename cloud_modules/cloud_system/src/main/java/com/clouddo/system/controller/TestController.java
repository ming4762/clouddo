package com.clouddo.system.controller;

import com.clouddo.commons.common.util.message.Result;
import com.clouddo.commons.meteo.util.MeteoDrawUtil;
import com.netflix.discovery.EurekaClient;
import org.meteoinfo.data.StationData;
import org.meteoinfo.layer.VectorLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/2下午1:15
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping("/meteoTest")
    @ResponseBody
    public Object meteoTest() {
        VectorLayer vectorLayer = MeteoDrawUtil.createRainShadedLayer(getTestData(), "/Users/ming/Downloads/QQ/shp/Export_Output.shp");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("polygonShapes", vectorLayer.getShapes());
        data.put("colorBreaks", vectorLayer.getLegendScheme().getLegendBreaks());

        return Result.success(data);
    }

    @RequestMapping("/contourTest")
    @ResponseBody
    public Object contourTest() {
        VectorLayer vectorLayer = MeteoDrawUtil.createRainContourLayer(getTestData(), "/Users/ming/Downloads/QQ/shp/Export_Output.shp");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("polygonShapes", vectorLayer.getShapes());
        data.put("colorBreaks", vectorLayer.getLegendScheme().getLegendBreaks());
        return Result.success(data);
    }

    private StationData getTestData() {
        StationData stationData = new StationData();
        stationData.addData("41800501",122.172221,37.231388,0);
        stationData.addData("41815092",121.976127,37.381709,0);
        stationData.addData("41815392",122.10575,37.43554,19.5);
        stationData.addData("41815412",122.127962,37.364874,17);
        stationData.addData("41815422",122.184207,37.343873,0);
        stationData.addData("41815432",122.034467,37.36433,83);
        stationData.addData("41815442",122.073385,37.453226,90);
        stationData.addData("41815452",122.107062,37.474609,60);
        stationData.addData("41815462",122.120516,37.533424,25.5);
        stationData.addData("41815472",122.49793,37.270414,0);
        stationData.addData("41815482",122.515525,37.354743,28);
        stationData.addData("41815490",121.850401,37.155312,20);
        stationData.addData("41815492",122.452781,37.284489,0);
        stationData.addData("41815501",122.355669,37.13006,34);
        stationData.addData("41815512",122.563206,37.228967,0);
        stationData.addData("41815522",122.503293,37.204754,28.5);
        stationData.addData("41815532",122.38386,37.274962,11.5);
        stationData.addData("41815542",122.29195,37.227779,18.5);
        stationData.addData("41815562",122.26314,37.10343,0);
        stationData.addData("41815572",122.262403,37.009482,5);
        stationData.addData("41815582",122.229294,36.94068,0);
        stationData.addData("41815592",122.308287,36.923658,24.5);
        stationData.addData("41815602",122.346696,37.907093,42);
        stationData.addData("41815612",122.532892,37.249198,40);
        stationData.addData("41815622",122.491149,37.25209,0);
        stationData.addData("41815632",122.38695,37.309338,0);
        stationData.addData("41815642",122.31218,37.218358,0);
        stationData.addData("41815652",122.342189,37.010219,0);
        stationData.addData("41815932",121.447214,37.110365,292);
        stationData.addData("41815942",121.447589,37.056463,42);
        stationData.addData("41815952",121.298866,36.929455,34);
        stationData.addData("41815962",121.281311,36.999113,35.5);
        stationData.addData("41815972",121.310701,37.097576,0);
        stationData.addData("41815982",121.269899,37.112468,26);
        stationData.addData("41815992",121.300765,37.128711,0);
        stationData.addData("41816042",121.573513,37.082662,0);
        stationData.addData("41816052",121.592203,37.059166,0);
        stationData.addData("41816062",121.638015,36.924197,337);
        stationData.addData("41816072",121.687046,36.999214,20);
        stationData.addData("4181607B",122.3293,37.1167,0);
        stationData.addData("41816082",121.735004,36.952908,0);
        stationData.addData("41816092",121.674805,36.882452,15);
        stationData.addData("41816122",121.706015,36.928125,14);
        stationData.addData("4181618B",122.3764,36.967,0);
        stationData.addData("4181817B",121.90078,37.38731,0);
        stationData.addData("4183901B",122.11262,37.45893,0);
        stationData.addData("4183902B",122.1692,37.35889,0);
        stationData.addData("4183903B",122.198133,37.356658,0);
        stationData.addData("4183904B",122.0793,37.43389,0);
        stationData.addData("4183906B",122.0289,37.44219,0);
        stationData.addData("4183907B",122.006846,37.396096,0);
        stationData.addData("4183908B",122.08678,37.42014,0);
        stationData.addData("4183910B",122.161325,37.357461,0);
        stationData.addData("4183912B",122.0361,37.46317,0);
        stationData.addData("41839700",122.033701,37.423725,25);
        stationData.addData("41839750",122.024912,37.516461,29);
        stationData.addData("41839760",121.9317,37.407715,27);
        stationData.addData("41839770",122.033022,37.482688,45.5);
        stationData.addData("41839780",122.1131,37.5372,27);
        stationData.addData("41839790",122.126572,37.487596,31.5);
        stationData.addData("41839800",122.1742,37.3631,27.5);
        stationData.addData("41839810",122.1447,37.4203,26);
        stationData.addData("41839830",122.35139,37.376488,27.5);
        stationData.addData("41839910",122.479163,37.154125,39.5);
        stationData.addData("41839920",122.445486,37.292913,0);
        stationData.addData("41839930",122.451839,37.232775,38.5);
        stationData.addData("41839950",122.546444,37.371845,35.5);
        stationData.addData("41839970",122.369,37.251,30);
        stationData.addData("41839980",122.302352,37.114997,29);
        stationData.addData("41840000",122.69,37.3977,58.5);
        stationData.addData("4184001B",122.134722,37.238333,0);
        stationData.addData("4184002B",122.3457,36.8973,0);
        stationData.addData("41840030",122.305518,37.014897,34);
        stationData.addData("4184003B",122.4043,37.3184,0);
        stationData.addData("41840040",122.345311,36.977062,38);
        stationData.addData("4184004B",122.235833,37.054444,0);
        stationData.addData("41840050",122.575594,37.243239,41);
        stationData.addData("4184005B",122.3066,36.9651,0);
        stationData.addData("41840060",122.495579,36.98709,57);
        stationData.addData("4184006B",122.115194,37.023194,0);
        stationData.addData("41840070",122.438362,36.969745,56);
        stationData.addData("4184007B",122.3301,37.1468,0);
        stationData.addData("41840080",122.235395,36.956145,38.5);
        stationData.addData("4184008B",122.3956,37.1218,0);
        stationData.addData("41840090",122.391177,36.930761,54);
        stationData.addData("4184009B",122.4099,37.1137,0);
        stationData.addData("41840100",122.2972,37.2397,8.5);
        stationData.addData("4184010B",121.861389,36.976111,0);
        stationData.addData("41840110",122.300346,36.892008,31);
        stationData.addData("4184011B",121.832083,37.048139,0);
        stationData.addData("4184012B",121.775556,37.068111,0);
        stationData.addData("4184013B",121.786417,37.063167,0);
        stationData.addData("4184014B",121.944611,37.137778,0);
        stationData.addData("41840150",122.310694,37.215242,26.5);
        stationData.addData("4184015B",122.508583,37.129722,0);
        stationData.addData("4184016B",122.183917,37.18225,0);
        stationData.addData("4184017B",122.4902,37.3595,0);
        stationData.addData("4184018B",122.2281,36.9642,0);
        stationData.addData("4184019B",121.851667,37.158056,0);
        stationData.addData("4184020B",121.784611,37.179778,0);
        stationData.addData("4184021B",121.803278,37.181056,0);
        stationData.addData("4184022B",122.4167,36.9833,0);
        stationData.addData("4184023B",122.3098,37.1467,0);
        stationData.addData("4184024B",121.844167,37.301444,0);
        stationData.addData("41840250",122.322222,37.166935,24.5);
        stationData.addData("41840400",122.5123,36.906358,36.5);
        stationData.addData("41840450",122.4167,36.8833,43);
        stationData.addData("41840500",122.264233,36.937477,43);
        stationData.addData("41840550",122.211057,37.200395,30);
        stationData.addData("41840590",121.9299,37.2463,36);
        stationData.addData("41840610",122.0983,37.2136,0);
        stationData.addData("41840630",122.153616,37.162437,33);
        stationData.addData("41840640",122.1099,37.1667,0);
        stationData.addData("41840650",122.035136,37.360175,29);
        stationData.addData("41840660",122.423202,37.383046,31);
        stationData.addData("41840670",122.1196,37.0587,0);
        stationData.addData("41840700",121.967624,37.305394,28.5);
        stationData.addData("41840710",122.099777,37.099651,30);
        stationData.addData("41840720",122.010192,37.074138,28.5);
        stationData.addData("41840730",121.9994,36.9964,34.5);
        stationData.addData("41840740",122.087955,37.019872,20);
        stationData.addData("41840750",121.892024,37.314077,39.5);
        stationData.addData("41840760",122.056197,36.940005,34.5);
        stationData.addData("41840770",121.865277,37.319443,0);
        stationData.addData("41840800",121.865384,37.273748,36.5);
        stationData.addData("41840850",121.743644,37.229554,0);
        stationData.addData("41840900",121.770788,37.222389,0);
        stationData.addData("41840950",121.818446,37.209857,36);
        stationData.addData("41841010",122.166811,37.018799,34);
        stationData.addData("41841020",121.6878,37.1244,37);
        stationData.addData("41841030",122.224907,37.232796,37);
        stationData.addData("41841040",121.825892,37.076467,26);
        stationData.addData("41841060",121.784721,37.179721,0);
        stationData.addData("41841070",121.789166,37.111388,0);
        stationData.addData("41841150",121.823692,37.117472,35);
        stationData.addData("41841200",122.0847,37.284996,45);
        stationData.addData("41841250",122.0414,37.1955,36.5);
        stationData.addData("41841300",121.91992,37.064911,43.5);
        stationData.addData("41841320",121.811388,37.033333,0);
        stationData.addData("41841550",122.130741,36.961743,21.5);
        stationData.addData("41841650",122.003067,37.14454,40.5);
        stationData.addData("41841700",121.671597,37.033867,32.5);
        stationData.addData("41841730",121.605067,37.033707,37.5);
        stationData.addData("41841740",121.86621,36.976881,37.5);
        stationData.addData("41841750",121.787769,36.975781,42.5);
        stationData.addData("41841800",121.634282,36.922953,38);
        stationData.addData("41841810",121.490646,37.011894,34.5);
        stationData.addData("41841820",121.430778,37.017753,43);
        stationData.addData("41841830",121.5142,36.9081,32.5);
        stationData.addData("41841840",121.374,36.978894,48);
        stationData.addData("41841850",121.3994,37.1669,53.5);
        stationData.addData("41841870",121.450864,36.885981,41.5);
        stationData.addData("41841880",121.504742,36.870747,37);
        stationData.addData("41841890",121.697604,36.831222,38);
        stationData.addData("41841900",121.397895,37.116357,44);
        stationData.addData("41841910",121.615056,36.813626,36.5);
        stationData.addData("41841920",121.760882,36.913275,40);
        stationData.addData("41841950",121.21148,37.028997,60.5);
        stationData.addData("41842000",121.303705,37.078971,46);
        stationData.addData("41842100",121.486267,37.069445,44);
        return stationData;
    }

}
