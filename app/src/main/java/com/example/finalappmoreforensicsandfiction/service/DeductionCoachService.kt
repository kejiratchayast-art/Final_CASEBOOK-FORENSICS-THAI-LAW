package com.example.finalappmoreforensicsandfiction.service

import com.example.finalappmoreforensicsandfiction.data.AiFeedbackResult
import com.example.finalappmoreforensicsandfiction.data.CaseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DeductionCoachService {

    // Configurable backend endpoint URL (10.0.2.2 maps to host localhost from Android Emulator)
    private const val BACKEND_URL = "http://127.0.0.1:3000/api/deduction-coach"

    suspend fun reviewUserHypothesis(
        caseData: CaseData,
        userHypothesis: String,
        discoveredEvidenceIds: Set<String>
    ): AiFeedbackResult = withContext(Dispatchers.IO) {
        val dateStr = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US).format(Date())

        var connection: HttpURLConnection? = null
        try {
            val url = URL(BACKEND_URL)
            val activeConnection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                setRequestProperty("Accept", "application/json")
                doOutput = true
                connectTimeout = 6000
                readTimeout = 35000
            }
            connection = activeConnection

            val jsonInput = JSONObject().apply {
                put("caseId", caseData.id)
                put("hypothesis", userHypothesis)
                val arr = JSONArray()
                discoveredEvidenceIds.forEach { arr.put(it) }
                put("discoveredEvidenceIds", arr)
                val observations = JSONArray()
                caseData.hotspots.filter { it.id in discoveredEvidenceIds }.forEach { item ->
                    observations.put(JSONObject().apply {
                        put("id", item.id)
                        put("title", item.title)
                        put("observedFacts", JSONArray(item.observedFacts))
                    })
                }
                put("evidence", observations)
            }

            activeConnection.outputStream.use { os ->
                val input = jsonInput.toString().toByteArray(Charsets.UTF_8)
                os.write(input, 0, input.size)
            }

            val responseCode = activeConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 203) {
                val inputStream = if (responseCode == HttpURLConnection.HTTP_OK) activeConnection.inputStream else activeConnection.errorStream
                val responseStr = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { br ->
                    val sb = StringBuilder()
                    var line: String?
                    while (br.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                    sb.toString()
                }

                val jsonResp = JSONObject(responseStr)
                val status = jsonResp.optString("status", "DEMO")
                val isLive = (status == "LIVE")
                val fbObj = jsonResp.optJSONObject("feedback") ?: jsonResp
                for (field in listOf("supportingEvidence", "assumptionsToReconsider", "remainingUncertainties")) {
                    val array = fbObj.getJSONArray(field)
                    for (i in 0 until array.length()) require(array.get(i) is String)
                }
                require(fbObj.getString("guidingQuestion").isNotBlank())
                coroutineContext.ensureActive()

                val supporting = mutableListOf<String>()
                val supArr = fbObj.optJSONArray("supportingEvidence")
                if (supArr != null) {
                    for (i in 0 until supArr.length()) {
                        supporting.add(supArr.getString(i))
                    }
                }

                val assumptions = mutableListOf<String>()
                val assArr = fbObj.optJSONArray("assumptionsToReconsider")
                if (assArr != null) {
                    for (i in 0 until assArr.length()) {
                        assumptions.add(assArr.getString(i))
                    }
                }

                val uncertainties = mutableListOf<String>()
                val uncArr = fbObj.optJSONArray("remainingUncertainties")
                if (uncArr != null) {
                    for (i in 0 until uncArr.length()) {
                        uncertainties.add(uncArr.getString(i))
                    }
                }

                val question = fbObj.optString("guidingQuestion", "How does physical trace evidence corroborate your deduction?")

                AiFeedbackResult(
                    caseId = caseData.id,
                    userHypothesis = userHypothesis,
                    supportingEvidence = supporting,
                    assumptionsToReconsider = assumptions,
                    remainingUncertainties = uncertainties,
                    guidingQuestion = question,
                    isDemoResponse = !isLive,
                    timestamp = dateStr
                )
            } else {
                val errorBody = activeConnection.errorStream?.bufferedReader()?.use { it.readText() }
                val message = runCatching { JSONObject(errorBody ?: "{}").optString("error") }.getOrDefault("")
                throw Exception(message.ifBlank { "Server returned HTTP $responseCode" })
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            // Fallback structured error / offline demo response preserving user draft
            AiFeedbackResult(
                caseId = caseData.id,
                userHypothesis = userHypothesis,
                supportingEvidence = listOf("Offline Fallback: Your draft hypothesis has been preserved."),
                assumptionsToReconsider = listOf("Network connection unavailable or backend server offline (${e.localizedMessage ?: "Connection error"}). Please check server configuration."),
                remainingUncertainties = listOf("Live AI verification BLOCKED. Operating in local fallback mode."),
                guidingQuestion = "How does physical trace evidence corroborate or refute the suspect's statement?",
                isDemoResponse = true,
                timestamp = dateStr
            )
        } finally {
            connection?.disconnect()
        }
    }
}

