package com.clouddo.admin.thymeleaf.attribute;

import com.clouddo.admin.thymeleaf.CharsmingDialect;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/27上午11:11
 */
public class HasPermissionAttrProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTRIBUTE_NAME = "hasPermission";
    private static final int PRECEDENCE = 300;

    public HasPermissionAttrProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTRIBUTE_NAME, // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag iProcessableElementTag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        final String rawValue = CharsmingDialect.getRawValue(iProcessableElementTag, attributeName);
        //解析该标签需要的权限
    }



}
