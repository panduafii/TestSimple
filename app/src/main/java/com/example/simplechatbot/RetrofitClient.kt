package com.example.simplechatbot

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.groq.com/"

    val apiService: GroqApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val token = BuildConfig.GROQ_API_KEY // Token diambil dari BuildConfig
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(GroqApiService::class.java)
    }
}
