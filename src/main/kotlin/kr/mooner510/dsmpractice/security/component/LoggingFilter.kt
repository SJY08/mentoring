package kr.mooner510.dsmpractice.security.component

import io.swagger.v3.core.util.Json
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoggingFilter : OncePerRequestFilter() {
    companion object {
        private val timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        private val writer = BufferedWriter(OutputStreamWriter(System.out))
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (request.requestURI.let { it.startsWith("/v1") && (!it.contains("api-docs") || it.endsWith("-config")) }) {
            filterChain.doFilter(request, response)
            return
        }

        val cachedRequest = ContentCachingRequestWrapper(request)
        val start = System.currentTimeMillis()
        filterChain.doFilter(cachedRequest, response)
        val end = System.currentTimeMillis()

        writer.write("\n")
        writer.append(
            """
            ${LocalDateTime.now().format(timeFormat)} :: ${request.method} ${
                URLDecoder.decode(
                    request.requestURI,
                    StandardCharsets.UTF_8
                )
            }${
                if (request.parameterMap.isEmpty()) ""
                else request.parameterMap.entries.joinToString(
                    prefix = "?",
                    separator = "&"
                ) { "${it.key}=${it.value.joinToString()}" }
            } [${response.status}] (${end - start}ms)
            ${
                request.headerNames?.asSequence()
                    ?.filterNot {
                        it.startsWith("cf-") ||
                                it.startsWith("cdn-") ||
                                it.startsWith("sec-") ||
                                it == "accept-encoding" ||
                                it == "accept-language" ||
                                it == "dnt" ||
                                it == "priority" ||
                                it == "connection"
                    }
                    ?.map { it to request.getHeader(it) }
                    ?.joinToString("\n", ">> Headers: \n") {
                        "                ${it.first.padEnd(16)}: ${it.second}"
                    } ?: ""
            }
            ${request.cookies?.joinToString(prefix = ">> Cookies: ") { "${it.name}=${it.value}" } ?: ""}
            """.trimIndent()
        )
        if (request.method == "POST" || request.method == "PUT") {
            writer.append(">> Body: ${Json.pretty(cachedRequest.contentAsString)}")
        }
        writer.flush()
    }
}
