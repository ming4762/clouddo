package com.clouddo.wechat.common.mp.config;

import com.clouddo.wechat.common.mp.handler.*;
import com.google.common.collect.Maps;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.*;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/31上午8:29
 */
@Configuration
@EnableConfigurationProperties(WeChatMpProperties.class)
public class WeChatMpConfiguration {

    private LogHandler logHandler;
    private NullHandler nullHandler;
    private KfSessionHandler kfSessionHandler;
    private StoreCheckNotifyHandler storeCheckNotifyHandler;
    private LocationHandler locationHandler;
    private MenuHandler menuHandler;
    private MsgHandler msgHandler;
    private UnsubscribeHandler unsubscribeHandler;
    private SubscribeHandler subscribeHandler;

    private WeChatMpProperties properties;

    private static Map<String, WxMpMessageRouter> routers = Maps.newLinkedHashMap();
    private static Map<String, WxMpService> mpServices = Maps.newLinkedHashMap();

    public static Map<String, WxMpMessageRouter> getRouters() {
        return routers;
    }

    public static Map<String, WxMpService> getMpServices() {
        return mpServices;
    }

    @Autowired
    public WeChatMpConfiguration(LogHandler logHandler, NullHandler nullHandler, KfSessionHandler kfSessionHandler,
                             StoreCheckNotifyHandler storeCheckNotifyHandler, LocationHandler locationHandler,
                             MenuHandler menuHandler, MsgHandler msgHandler, UnsubscribeHandler unsubscribeHandler,
                             SubscribeHandler subscribeHandler, WeChatMpProperties properties) {
        this.logHandler = logHandler;
        this.nullHandler = nullHandler;
        this.kfSessionHandler = kfSessionHandler;
        this.storeCheckNotifyHandler = storeCheckNotifyHandler;
        this.locationHandler = locationHandler;
        this.menuHandler = menuHandler;
        this.msgHandler = msgHandler;
        this.unsubscribeHandler = unsubscribeHandler;
        this.subscribeHandler = subscribeHandler;
        this.properties = properties;
    }

    /**
     * 获取默认的mpService(配置文件中第一个)
     * @return
     */
    public static WxMpService getDefaultService() {
        Iterator<Map.Entry<String, WxMpService>> iterator = mpServices.entrySet().iterator();
        return iterator.next().getValue();
    }

    @PostConstruct
    public void services() {

        // 根据配置文件创建mpServices routers
        this.properties.getConfigs().forEach((key, config) -> {
            WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
            configStorage.setAppId(config.getAppid());
            configStorage.setSecret(config.getSecret());
            configStorage.setToken(config.getToken());
            configStorage.setAesKey(config.getAesKey());
            WxMpService service = new WxMpServiceImpl();
            service.setWxMpConfigStorage(configStorage);
            mpServices.put(config.getAppid(), service);
            routers.put(config.getAppid(), this.newRouter(service));
        });
    }


    private WxMpMessageRouter newRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(this.kfSessionHandler)
                .end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(MenuButtonType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SUBSCRIBE).handler(this.subscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.UNSUBSCRIBE)
                .handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.LOCATION).handler(this.locationHandler)
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
                .handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
                .event(EventType.SCAN).handler(this.nullHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
