package com.example.finalappmoreforensicsandfiction

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.finalappmoreforensicsandfiction.service.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatVerificationTest {
    @Test fun liveQuestionFollowUpAndStorage() = runBlocking {
        val question = "What can the floor wax observation establish and what remains uncertain?"
        val first = CasebookChat.ask("case_01", "Case Evidence", question, emptyList(), setOf("c1_h1"))
        assertEquals("assistant", first.role)
        assertTrue(first.text.isNotBlank())
        assertTrue(first.references.all { it == "c1_h1" })
        val follow = CasebookChat.ask("case_01", "Case Evidence", "Does that observation alone establish intent?",
            listOf(ChatMessage("user", question), first), setOf("c1_h1"))
        assertTrue(follow.text.isNotBlank())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val store = ChatStore(context, "case_01")
        val original = store.load()
        val other = ChatStore(context, "case_02").load()
        try {
            store.save(listOf(ChatMessage("user", question), first, follow))
            assertEquals(first, ChatStore(context, "case_01").load()[1])
            assertEquals(other, ChatStore(context, "case_02").load())
        } finally { store.save(original) }
    }
}
