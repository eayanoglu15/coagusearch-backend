package com.example.coagusearch.backend.FirebaseNotificationSystem

import com.google.firebase.messaging.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service



@Service
class FirebaseNotificationSender @Autowired constructor(
        private val firebaseService: FirebaseService
) {
    fun pushNotificationImpl(
            userToken: String,
            badge: Int,
            title: String?,
            body: String?,
            dataToSend: Map<String, String>
    ) {
        val message: Message.Builder =
                Message.builder()
                .putAllData(dataToSend).putData("badge", badge.toString())
                .putData("title", title)
                .putData("body", body)
                .setToken(userToken)
        val response = firebaseService.firebaseMessaging.send(message.build())
        println(response)
    }


    fun testNotification(){
        pushNotificationImpl(
                "c4BUs8rcwms:APA91bHP3pVjyIYpl0kXW40IqpdklcAMl-CUkVJb5R67-CPTQENdBXDqb8e3DnA8Anqedw7zaeKYjLKU7PKF4px4qd-n7qs1zeKVM6TNxjQ3f4Y_rvfHVxJhXH-boML0HwuI6gLDYREb",
                3,
                "Muharrem Salel",
                "This is test message",
                mapOf("key" to "333")

        )

    }



}