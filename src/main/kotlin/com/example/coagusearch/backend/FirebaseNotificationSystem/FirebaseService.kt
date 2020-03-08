package com.example.coagusearch.backend.FirebaseNotificationSystem

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import javax.annotation.PostConstruct
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class FirebaseService {

    @Volatile
    private var initalized: Boolean = false

    @PostConstruct
    @Synchronized
    fun init() {
        if (!initalized) {
            initalized = try {
                initialize()
                true
            } catch (_: Exception) {
                false
            }
        }
    }
    private fun initialize() {
        val configFile = ClassPathResource("firebase_config.json")
        val serviceAccount = configFile.inputStream

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://coagu-search.firebaseio.com")
            .build()

        FirebaseApp.initializeApp(options)
    }

    val firebaseMessaging: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()
}
