package com.example.finalappmoreforensicsandfiction.service

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class ChatMessage(
    val role: String,
    val text: String,
    val mode: String = "Case Evidence",
    val references: List<String> = emptyList()
)

class ChatStore(context: Context, private val caseId: String) {
    private val prefs = context.getSharedPreferences("casebook_prefs", Context.MODE_PRIVATE)

    fun load(): List<ChatMessage> = runCatching {
        val array = JSONArray(prefs.getString("chat_$caseId", "[]"))
        (0 until array.length()).map { i ->
            val value = array.getJSONObject(i)
            val ids = value.optJSONArray("references") ?: JSONArray()
            val mode = value.optString("mode", "Case Evidence")
            ChatMessage(value.getString("role"), value.getString("text"), mode, (0 until ids.length()).map { ids.getString(it) })
        }
    }.getOrDefault(emptyList())

    fun save(messages: List<ChatMessage>) {
        val array = JSONArray()
        messages.takeLast(60).forEach { message ->
            array.put(JSONObject().apply {
                put("role", message.role)
                put("text", message.text)
                put("mode", message.mode)
                put("references", JSONArray(message.references))
            })
        }
        prefs.edit().putString("chat_$caseId", array.toString()).apply()
    }

    fun draft(): String = prefs.getString("chat_draft_$caseId", "").orEmpty()
    fun saveDraft(text: String) { prefs.edit().putString("chat_draft_$caseId", text).apply() }
}

object CasebookChat {
    suspend fun ask(
        caseId: String,
        mode: String,
        question: String,
        history: List<ChatMessage>,
        discoveredIds: Set<String>
    ): ChatMessage = withContext(Dispatchers.IO) {
        val connection = URL("http://127.0.0.1:3000/api/ask-casebook").openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.connectTimeout = 6000
            connection.readTimeout = 35000
            connection.doOutput = true

            val input = JSONObject().apply {
                put("caseId", caseId)
                put("mode", mode)
                put("question", question)
                put("discoveredEvidenceIds", JSONArray(discoveredIds.toList()))
                val messages = JSONArray()
                history.takeLast(10).forEach {
                    messages.put(
                        JSONObject()
                            .put("role", it.role)
                            .put("text", it.text.take(2000))
                    )
                }
                put("history", messages)
            }

            connection.outputStream.use { it.write(input.toString().toByteArray(Charsets.UTF_8)) }
            val code = connection.responseCode
            val text = (if (code == 200) connection.inputStream else connection.errorStream)?.bufferedReader()?.use { it.readText() }.orEmpty()
            val response = JSONObject(text)
            if (code != 200) error(response.optString("error", "AI is unavailable. Please retry."))
            require(response.getString("status") == "LIVE") { "No live AI answer received." }
            val answer = response.getString("answer")
            require(answer.isNotBlank()) { "The AI returned an empty answer. Please retry." }
            val ids = response.getJSONArray("referencedSourceIds")
            val references = (0 until ids.length()).map { ids.getString(it) }
            coroutineContext.ensureActive()
            ChatMessage("assistant", answer, mode, references)
        } catch (error: java.io.IOException) {
            coroutineContext.ensureActive()
            throw IllegalStateException("Cannot reach the AI server. Check the backend and connection, then retry. Your draft is saved.", error)
        } finally {
            connection.disconnect()
        }
    }
}
