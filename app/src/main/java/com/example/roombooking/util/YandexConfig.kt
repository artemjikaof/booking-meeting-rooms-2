package com.example.roombooking.util

import android.net.Uri

object YandexConfig {
    // ВНИМАНИЕ: В реальном проекте эти данные должны быть в BuildConfig или на бэкенде
    // Для разработки используйте значения из консоли Яндекса
    const val CLIENT_ID = "7cbe919c321d409ca5d75bf786c15a73"
    const val CLIENT_SECRET = "d41b2a44829f4a27ac2544aa24d2e47d"
    const val REDIRECT_URI = "roombooking://yandex-auth"
    
    fun getAuthUrl(): String {
        val encodedRedirect = Uri.encode(REDIRECT_URI)
        // Используем точные названия из консоли Яндекса
        val scope = Uri.encode("login:info calendar:read_all calendar:write_all")
        return "https://oauth.yandex.ru/authorize?response_type=code&client_id=$CLIENT_ID&redirect_uri=$encodedRedirect&scope=$scope"
    }
}
