package com.clouddo.admin.thymeleaf.attribute;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * html标签自定义属性解析器
 * TODO 暂不支持多自定义属性解析
 * 解决html自定义标签问题
 * thymeleaf自带的th:attr标签可以解决一般的自定义标签，比如th:attr="data-iframehref=${url}"
 * 但是对于中间有空格的无法处理 例如 th:attr="data-iframe href=${url}"
 * @author zhongming
 * @since 3.0
 * 2018/7/20上午8:48
 */
public class CustomAttributesTag extends AbstractAttributeTagProcessor {

    //属性名称
    private static final String ATTR_NAME = "attr";
    //同一方言中的优先级
    private static final int PRECEDENCE = 10000;

    public CustomAttributesTag(String dialectPrefix) {
        super(
                TemplateMode.HTML, // 处理thymeleaf 的模型
                dialectPrefix, // 标签前缀名
                 null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTR_NAME, // 标签前缀的 属性 例如：< risk:sansiEncrypt="">
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true
        );
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler iElementTagStructureHandler) {
        final IEngineConfiguration configuration = context.getConfiguration();
        //获得Thymeleaf标准表达式解析器
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        //将属性值解析为Thymeleaf标准表达式
        if(attributeValue.contains("=")) {
            final IStandardExpression expression = parser.parseExpression(context, attributeValue.split("=")[1]);
            //执行刚刚解析的表达式获取结果
            Object value = expression.execute(context);
            iElementTagStructureHandler.setAttribute(attributeValue.split("=")[0], (String) value);
        }
    }
}
