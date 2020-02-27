package com.example.coagusearch.modules.config

import org.apache.commons.lang3.LocaleUtils
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale
import javax.servlet.http.HttpServletRequest

class CustomLocaleResolver : AcceptHeaderLocaleResolver() {
    override fun resolveLocale(request: HttpServletRequest): Locale {
        val language = request.getHeader("Accept-Language")
        if (language.isNullOrBlank()) {
            return Locale.ENGLISH
        }
        return try {
            val locale = LocaleUtils.toLocale(language) ?: Locale.ENGLISH
            if (locale.language.isNullOrBlank()) {
                Locale.ENGLISH
            } else {
                locale
            }
        } catch (_: IllegalArgumentException) {
            super.resolveLocale(request)
        }
    }
}
