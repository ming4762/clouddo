package com.clouddo.messagebus.feign;

import com.clouddo.log.common.model.LogModel;
import com.clouddo.messagebus.feign.impl.LogServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志Feign工具类
 * @author zhongming
 * @since 3.0
 * 2018/5/30上午11:13
 */
@FeignClient(value = "cloud-log-server", fallback = LogServiceImpl.class)
public interface LogService {

    /**
     * 保存日志
     * @param logModel
     */
    @RequestMapping(value = "/log/saveLog")
    public void saveLog(LogModel logModel);
}
