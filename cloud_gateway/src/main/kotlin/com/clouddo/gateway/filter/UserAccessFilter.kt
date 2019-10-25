package com.clouddo.gateway.filter

import com.clouddo.gateway.feigin.AuthFeign
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class UserAccessFilter : ZuulFilter() {

    companion object {
        private val logger = LoggerFactory.getLogger(UserAccessFilter :: class.java)
    }

    @Autowired
    private lateinit var authFeign: AuthFeign

    @Value("\${zuul.prefix}")
    private lateinit var zuulPrefix: String

    override fun filterOrder(): Int {
        return 1
    }

    override fun filterType(): String {
        return "pre"
    }

    override fun shouldFilter(): Boolean {
        return true
    }

    override fun run(): Any? {
        // option请求不拦截
        val request = RequestContext.getCurrentContext().request
        if (request.method.equals(HttpMethod.OPTIONS.name)) {
            return null
        }
        val requestUri = request.requestURI.substring(zuulPrefix.length)
        val result = this.authFeign.validateUser(mapOf("url" to requestUri)).data
        if (result != null) {
            this.setFailedRequest(result, 40301)
        }
        return null
    }

    /**
     * 网关抛出异常
     */
    private fun setFailedRequest(body: String, code: Int) {
        logger.debug("Reporting error ({}): {}", code, body)
        val ctx = RequestContext.getCurrentContext()
        ctx.response.contentType = "text/html;charset=UTF-8"
        ctx.responseStatusCode = code
        if (ctx.responseBody == null) {
            ctx.responseBody = body
            ctx.setSendZuulResponse(false)
        }
    }


}


