package com.example.simplechatbot

data class GroqResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: MessageContent
)

data class MessageContent(
    val role: String,
    val content: String
)
