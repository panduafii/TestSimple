package com.example.simplechatbot

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etQuestion = findViewById<EditText>(R.id.et_question)
        val btnSend = findViewById<Button>(R.id.btn_send)
        val tvResponse = findViewById<TextView>(R.id.tv_response)

        btnSend.setOnClickListener {
            val question = etQuestion.text.toString().trim()
            if (question.isNotEmpty()) {
                tvResponse.text = "Processing..."
                lifecycleScope.launch {
                    try {
                        val responseText = fetchResponseFromAI(question)
                        tvResponse.text = responseText
                    } catch (e: Exception) {
                        Log.e("APIError", "Error: ${e.message}")
                        tvResponse.text = "Error: ${e.message}"
                    }
                }
            } else {
                tvResponse.text = "Please enter a question!"
            }
        }
    }

    private suspend fun fetchResponseFromAI(question: String): String {
        val message = Message(role = "user", content = question)
        val request = GroqRequest(
            messages = listOf(message),
            model = "mixtral-8x7b-32768",
            temperature = 1,
            max_tokens = 1024,
            top_p = 1,
            stream = false,
            response_format = ResponseFormat(type = "json_object"),
            stop = null
        )

        val response = RetrofitClient.apiService.getChatCompletion(request).execute()

        return if (response.isSuccessful && response.body() != null) {
            response.body()!!.choices.firstOrNull()?.message?.content ?: "No response from AI"
        } else {
            throw Exception("Error: ${response.code()} - ${response.message()}")
        }
    }
}
