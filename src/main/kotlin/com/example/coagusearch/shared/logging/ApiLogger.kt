package com.example.coagusearch.shared.logging


import com.example.coagusearch.modules.users.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiLogger(
        private val userService: UserService
       // private val userVersionService: UserVersionService
) : HandlerInterceptorAdapter() {
    private val requiredAndroidVersion: Int = 19
    private val requiredIosVersion: Int = 18
    private val requiredVersionMax: Int = if (requiredAndroidVersion > requiredIosVersion)
        requiredAndroidVersion else requiredIosVersion

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestId = UUID.randomUUID().toString()
        log(request, response, requestId, getClientIpAddressIfServletRequestExist(request))
        val startTime = System.currentTimeMillis()
        request.setAttribute("startTime", startTime)
        request.setAttribute("requestId", requestId)
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: java.lang.Exception?
    ) {
        super.afterCompletion(request, response, handler, ex)
        val startTime = request.getAttribute("startTime") as Long

        val endTime = System.currentTimeMillis()

        val executeTime = endTime - startTime
        logger.info(
                "Post-Handle RequestId {} TimeTaken: {}ms Status: {} HttpMethod: {} URI: {} Source: {} User: {}",
                request.getAttribute("requestId"),
                executeTime,
                response.status,
                request.method,
                request.requestURI,
                getClientIpAddressIfServletRequestExist(request),
                request.getAttribute("userId") ?: "NO"
        )
    }

    private fun log(request: HttpServletRequest, response: HttpServletResponse?, requestId: String, ip: String) {
        logger.info(
                "Pre-Handle RequestId: {} Host: {} HttpMethod: {} URI: {} Source: {}",
                requestId,
                request.getHeader("host"),
                request.method,
                request.requestURI,
                ip
        )
    }

    companion object {
        const val LOWEST_VERSION = -1

        private val logger = LoggerFactory.getLogger(ApiLogger::class.java)

        private val ipHeaderCandidates = arrayOf(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        )

        fun getClientIpAddressIfServletRequestExist(request: HttpServletRequest): String {
            for (header in ipHeaderCandidates) {
                val ipList = request.getHeader(header)
                if (ipList != null && ipList.isNotEmpty() && !"unknown".equals(ipList, ignoreCase = true)) {
                    return ipList.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                }
            }

            return request.remoteAddr
        }
    }
}
