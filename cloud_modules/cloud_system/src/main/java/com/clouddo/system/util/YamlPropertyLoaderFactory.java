package com.clouddo.system.util;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

/**
 * @author zhongming
 * @since 3.0
 * 2018/9/5下午4:34
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        if (resource == null) {
            return super.createPropertySource(name, resource);
        }
        List<PropertySource<?>> propertySourceList =  new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        if (propertySourceList != null && propertySourceList.size() > 0) {
            return propertySourceList.get(0);
        } else {
            return super.createPropertySource(name, resource);
        }
    }
}
