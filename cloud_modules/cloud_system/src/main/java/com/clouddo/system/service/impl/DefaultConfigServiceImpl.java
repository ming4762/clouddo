package com.clouddo.system.service.impl;

import com.clouddo.system.config.LocalConfigProperties;
import com.clouddo.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/5下午3:50
 */
@Service
@Primary
public class DefaultConfigServiceImpl implements ConfigService {

    @Autowired
    private LocalConfigProperties localConfigProperties;

    @Override
    public Object readConfig() {
        return this.localConfigProperties;
    }
}
