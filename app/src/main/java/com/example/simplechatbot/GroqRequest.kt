package com.example.simplechatbot

data class GroqRequest(
    val messages: List<Message>,
    val model: String,
    val temperature: Int,
    val max_tokens: Int,
    val top_p: Int,
    val stream: Boolean,
    val response_format: ResponseFormat,
    val stop: Any? = null
)

data class Message(
    val role: String,
    val content: String
)

data class ResponseFormat(
    val type: String
)
