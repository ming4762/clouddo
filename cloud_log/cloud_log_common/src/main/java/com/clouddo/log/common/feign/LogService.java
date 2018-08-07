package com.clouddo.log.common.feign;

import com.clouddo.commons.common.util.message.Result;
import com.clouddo.log.common.feign.impl.LogServiceImpl;
import com.clouddo.log.common.model.LogModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/7下午2:24
 */
@FeignClient(name = "cloud-message-bus", fallback = LogServiceImpl.class)
public interface LogService {

    /**
     * 保存日志
     * @param logModel
     * @return
     */
    @PostMapping("/receive/saveLog")
    public Result save(LogModel logModel);
}
