package com.example.finalappmoreforensicsandfiction.data

import org.json.JSONObject
import org.json.JSONArray

/** Structured storage; legacy plain text is retained separately by the notebook. */
object AiFeedbackStorage {
    fun encode(value: AiFeedbackResult): String = JSONObject().apply {
        put("caseId", value.caseId)
        put("userHypothesis", value.userHypothesis)
        put("supportingEvidence", JSONArray(value.supportingEvidence))
        put("assumptionsToReconsider", JSONArray(value.assumptionsToReconsider))
        put("remainingUncertainties", JSONArray(value.remainingUncertainties))
        put("guidingQuestion", value.guidingQuestion)
        put("isDemoResponse", value.isDemoResponse)
        put("timestamp", value.timestamp)
    }.toString()

    fun decode(text: String?): AiFeedbackResult? = runCatching {
        val obj = JSONObject(text ?: return null)
        fun strings(key: String): List<String> {
            val array = obj.getJSONArray(key)
            return (0 until array.length()).map { array.getString(it) }
        }
        AiFeedbackResult(obj.getString("caseId"), obj.getString("userHypothesis"),
            strings("supportingEvidence"), strings("assumptionsToReconsider"),
            strings("remainingUncertainties"), obj.getString("guidingQuestion"),
            obj.getBoolean("isDemoResponse"), obj.getString("timestamp"))
    }.getOrNull()
}
