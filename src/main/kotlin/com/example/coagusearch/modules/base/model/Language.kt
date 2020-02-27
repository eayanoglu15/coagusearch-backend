package com.example.coagusearch.modules.base.model

import java.util.Locale

enum class Language(val language: String, val defaultTimeZone: String) {
    EN("en_US", "EST"),
    TR("tr_TR", "Europe/Istanbul")
}

fun String.toLanguage(): Language = when {
    startsWith("en") -> Language.EN
    startsWith("tr") -> Language.TR
    else -> Language.EN
}

fun Locale.toLanguage(): Language = language.toLanguage()
