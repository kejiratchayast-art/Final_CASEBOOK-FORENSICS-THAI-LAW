package com.example.finalappmoreforensicsandfiction.data

data class BriefingScenarioOption(
    val id: Int,
    val optionText: String,
    val isCorrect: Boolean,
    val explanation: String
)

data class BriefingScenario(
    val id: String,
    val title: String,
    val situationText: String,
    val questionText: String,
    val options: List<BriefingScenarioOption>
)

object PreCaseBriefingProvider {

    val simulationBoundaryNotice = "In this simulation, tapping evidence opens an observation record. In real life, do not handle potential evidence or enter restricted scenes without authorization. Fictional evidence collection represents work performed by authorized forensic personnel."

    val briefingScenarios = listOf(
        BriefingScenario(
            id = "sc_1",
            title = "Scenario 1: Potentially Dangerous Scene",
            situationText = "You hear shouting and crashing inside a commercial building, then see a man running out holding a heavy metal pipe. Smoke begins to leak under the door.",
            questionText = "What should you do next?",
            options = listOf(
                BriefingScenarioOption(
                    id = 1,
                    optionText = "Move to a safe location, call 191 (Police) and 199 (Fire) in Thailand, and provide dispatchers with location details and observed hazards.",
                    isCorrect = true,
                    explanation = "Correct! Personal safety and emergency response take priority. Assess danger from a safe distance and report details accurately."
                ),
                BriefingScenarioOption(
                    id = 2,
                    optionText = "Enter the building immediately to inspect the room and look for physical evidence before smoke fills the area.",
                    isCorrect = false,
                    explanation = "Incorrect! Never enter an active hazard or fire scene to collect evidence. Life safety always precedes scene preservation."
                ),
                BriefingScenarioOption(
                    id = 3,
                    optionText = "Confront the running man and attempt to disarm him.",
                    isCorrect = false,
                    explanation = "Incorrect! Civilians should avoid confronting suspected offenders. Seek emergency assistance from a safe location."
                )
            )
        ),
        BriefingScenario(
            id = "sc_2",
            title = "Scenario 2: Injured Person Requiring Assistance",
            situationText = "You discover an unresponsive person lying on the roadside near a fallen motorcycle. Traffic is moving nearby and the person is breathing faintly.",
            questionText = "What should you do next?",
            options = listOf(
                BriefingScenarioOption(
                    id = 1,
                    optionText = "Call 1669 (Medical Emergency in Thailand), follow dispatcher instructions, warn oncoming traffic safely, and avoid unnecessary movement unless in immediate danger.",
                    isCorrect = true,
                    explanation = "Correct! Life safety takes priority over preserving evidence. Calling 1669 ensures professional medical responders are dispatched."
                ),
                BriefingScenarioOption(
                    id = 2,
                    optionText = "Delay calling 1669 until you photograph all skid marks on the road so evidence is not disturbed.",
                    isCorrect = false,
                    explanation = "Incorrect! Never delay emergency medical assistance to preserve a scene or take photographs."
                ),
                BriefingScenarioOption(
                    id = 3,
                    optionText = "Assume the unresponsive person is deceased and leave the area without calling for help.",
                    isCorrect = false,
                    explanation = "Incorrect! Never assume an unresponsive person is dead. Call 1669 immediately."
                )
            )
        ),
        BriefingScenario(
            id = "sc_3",
            title = "Scenario 3: Object That May Be Evidence",
            situationText = "While walking through a park, you spot a knife with dark stains resting on a bench near a damaged bicycle.",
            questionText = "What should you do next?",
            options = listOf(
                BriefingScenarioOption(
                    id = 1,
                    optionText = "Do not touch or move the knife. Keep others away from the bench, call 191 (Police), and report your observation to responding officers.",
                    isCorrect = true,
                    explanation = "Correct! Staying outside the area and avoiding physical contact prevents evidence contamination."
                ),
                BriefingScenarioOption(
                    id = 2,
                    optionText = "Pick up the knife using a plastic bag or gloves so you can hand it directly to police.",
                    isCorrect = false,
                    explanation = "Incorrect! Wearing gloves or bags does not grant permission to handle potential evidence."
                ),
                BriefingScenarioOption(
                    id = 3,
                    optionText = "Clean the bench so park visitors are not alarmed.",
                    isCorrect = false,
                    explanation = "Incorrect! Cleaning or rearranging objects alters and destroys physical evidence."
                )
            )
        ),
        BriefingScenario(
            id = "sc_4",
            title = "Scenario 4: Object Already Moved by Witness",
            situationText = "Before realizing the room was a crime scene, you picked up a dropped wallet from the floor. You now see police officers arriving to secure the area.",
            questionText = "What should you do next?",
            options = listOf(
                BriefingScenarioOption(
                    id = 1,
                    optionText = "Inform responding officers truthfully that you touched and moved the wallet, explaining exactly where it was located when you picked it up.",
                    isCorrect = true,
                    explanation = "Correct! Accurate disclosure helps forensic experts account for scene disturbance rather than creating false leads."
                ),
                BriefingScenarioOption(
                    id = 2,
                    optionText = "Secretly put the wallet back in what you guess was its original position and say nothing.",
                    isCorrect = false,
                    explanation = "Incorrect! Attempting to restore the scene creates false evidence patterns and conceals known changes."
                ),
                BriefingScenarioOption(
                    id = 3,
                    optionText = "Conceal the wallet in your pocket so you do not get into trouble.",
                    isCorrect = false,
                    explanation = "Incorrect! Concealing objects involved in an investigation creates serious legal complications."
                )
            )
        )
    )

    val legalConsequencesPanel = LegalSource(
        lawTitle = "Criminal Code of Thailand — Evidence Tampering Provisions",
        sectionArticle = "Criminal Code Section 184 (Destruction or Alteration of Evidence)",
        officialSourceTitle = "Office of the Council of State — Thai",
        officialSourceUrl = "https://www.krisdika.go.th",
        relevantAmendment = "Act Amending Criminal Code (No. 26) B.E. 2560 (2017)",
        effectiveDate = "November 13, 1956 (Original) / February 10, 2017 (Amended)",
        verificationDate = "May 10, 2024",
        verificationStatus = "Verified against official primary source — Unofficial English translation",
        revisionHistory = "v1.2 — Aligned with official Gazette text",
        contentVersion = "1.2",
        statutoryText = """
            [Criminal Code Section 184]
            "Whoever damages, destroys, causes to disappear, or renders useless any evidence of an offence, with intent to assist another person to avoid criminal punishment or to receive lesser punishment, shall be punished with imprisonment not exceeding five years or fine not exceeding one hundred thousand baht, or both."
        """.trimIndent(),
        educationalExplanation = """
            Legal Analysis & Intent Requirement:
            Section 184 requires specific intent to assist an offender in avoiding criminal liability or receiving lesser punishment.
            
            Distinction between Accidental Contact & Deliberate Tampering:
            - Accidental contact or emergency medical assistance that alters a scene does NOT constitute an offence under Section 184 because specific intent to pervert justice is absent.
            - Deliberate concealment, wiping fingerprints to protect a suspect, or disposing of weapons with intent to hinder justice constitutes an offence under Section 184.
        """.trimIndent(),
        caseApplication = "Real-World Civilian Relevance: Truthful disclosure of accidental contact or emergency aid is essential. Civilians should never attempt to conceal items or alter crime scenes.",
        legalQuestion = "What mental element (mens rea) is required for evidence tampering under Thai Criminal Code Section 184?",
        fictionalFacts = "Accidental touching during emergency first aid vs deliberate concealment of evidence.",
        conditionsAndElements = listOf(
            "Actus Reus: Damaging, destroying, concealing, or rendering evidence useless.",
            "Mens Rea: Specific intent to assist a person to avoid or lessen criminal punishment.",
            "Emergency medical aid lacks specific intent to hinder justice."
        ),
        missingOrUncertainFacts = "Individual judicial evaluation of specific intent.",
        limitsOfConclusion = "Educational explanation based on statutory text. Does not constitute formal legal advice."
    )
}
