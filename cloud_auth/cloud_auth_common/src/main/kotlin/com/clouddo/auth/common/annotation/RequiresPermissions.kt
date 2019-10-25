package com.clouddo.auth.common.annotation

import org.apache.shiro.authz.annotation.Logical

@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention()
annotation class RequiresPermissions(
        vararg val value: String = [],
        val logical: Logical = Logical.AND
) {

}