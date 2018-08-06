package com.clouddo.admin.thymeleaf;

import com.clouddo.admin.thymeleaf.attribute.CustomAttributesTag;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * thymeleaf自定义标签
 * @author zhongming
 * @since 3.0
 * 2018/7/20上午8:53
 */
public class CharsmingDialect extends AbstractProcessorDialect {

    private static final String NAME = "Charsming";
    private static final String PREFIX = "charsming";

    public CharsmingDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String value) {
        return createStandardProcessorsSet(value);
    }

    private Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();
        //添加自定义属性解析
        processors.add(new CustomAttributesTag(dialectPrefix));
        //添加权限解析
//        processors.add(new HasPermissionAttrProcessor(dialectPrefix));
        return processors;
    }

    /**
     * 获取标签的值
     * @param element
     * @param attributeName
     * @return
     */
    public static String getRawValue(final IProcessableElementTag element, final AttributeName attributeName) {
        Validate.notNull(element, "element must not be null");
        Validate.notNull(attributeName, "attributeName must not be empty");

        final String rawValue = StringUtils.trim(element.getAttributeValue(attributeName));
        Validate.notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }

}
