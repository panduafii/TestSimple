package com.example.simplechatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GroqApiService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer <your-api-key>"
    )
    @POST("v1/chat/completions")
    fun getChatCompletion(
        @Body request: GroqRequest
    ): Call<GroqResponse>
}
