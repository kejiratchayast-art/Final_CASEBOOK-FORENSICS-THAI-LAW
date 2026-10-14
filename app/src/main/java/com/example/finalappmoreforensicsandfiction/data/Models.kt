package com.example.finalappmoreforensicsandfiction.data

enum class AppMode(val title: String, val description: String) {
    READ_AND_LEARN(
        title = "Read & Learn",
        description = "Focus on reading the investigation narrative and learning concepts. Activities are optional."
    ),
    INVESTIGATE(
        title = "Investigate",
        description = "Interactive mode with 2D scene exploration, evidence inspection, and required activities."
    )
}

enum class FontSizeOption(val scale: Float, val label: String) {
    SMALL(0.88f, "Small"),
    NORMAL(1.0f, "Normal"),
    LARGE(1.18f, "Large")
}

enum class EvidenceCategory(val label: String) {
    FINGERPRINT("Fingerprints"),
    FOOTPRINT("Footprints & Shoeprint"),
    CHEMICAL("Chemicals & Fluids"),
    CLOTHING("Fibers & Clothing"),
    DOCUMENT("Documents & Digital Logs"),
    DIGITAL("Digital & CCTV"),
    VEHICLE("Vehicles & Skid Marks"),
    INJURY("Injuries & Pathology"),
    GLASS("Glass & Fracture Fit")
}

data class EvidenceHotspot(
    val id: String,
    val xRatio: Float,
    val yRatio: Float,
    val title: String,
    val category: EvidenceCategory,
    val observedFacts: List<String>,
    val forensicInterpretations: List<String>,
    val limitations: String
)

data class InterviewQuestion(
    val id: String,
    val questionText: String,
    val answerText: String,
    val revealedEvidenceId: String? = null
)

data class WitnessStatement(
    val id: String,
    val witnessName: String,
    val role: String,
    val statement: String,
    val inconsistencies: List<String>,
    val timeRecorded: String,
    val interviewQuestions: List<InterviewQuestion> = emptyList()
)

data class TimelineEvent(
    val id: String,
    val time: String,
    val title: String,
    val description: String,
    val location: String,
    val isContested: Boolean = false,
    val evidenceId: String? = null
)

data class ForensicConcept(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val detailedExplanation: String,
    val scientificLimitations: String,
    val relatedCaseIds: List<String>
)

data class LegalSource(
    val lawTitle: String,
    val sectionArticle: String,
    val officialSourceTitle: String,
    val officialSourceUrl: String,
    val relevantAmendment: String,
    val effectiveDate: String,
    val verificationDate: String,
    val verificationStatus: String,
    val revisionHistory: String,
    val contentVersion: String,
    val statutoryText: String,
    val educationalExplanation: String,
    val caseApplication: String,
    val legalQuestion: String,
    val fictionalFacts: String,
    val conditionsAndElements: List<String>,
    val missingOrUncertainFacts: String,
    val limitsOfConclusion: String
)

enum class ActivityType(val label: String) {
    EVIDENCE_REASONING("Inspect Evidence & Reason"),
    CHRONOLOGY("Arrange Chronologically"),
    MATCHING("Match Observation to Explanation"),
    INCONSISTENCY("Identify Witness Contradiction"),
    MULTIPLE_CHOICE_REASON("Multiple Choice Reasoning")
}

data class ActivityOption(
    val id: Int,
    val text: String,
    val isCorrect: Boolean,
    val explanation: String,
    val isInsufficientInformation: Boolean = false
)

data class LearningActivity(
    val id: String,
    val type: ActivityType,
    val title: String,
    val instruction: String,
    val learningObjectiveRef: String,
    val options: List<ActivityOption> = emptyList(),
    val correctSequence: List<Int> = emptyList(),
    val matchingPairs: List<Pair<String, String>> = emptyList(),
    val hint: String = ""
)

data class CaseResolution(
    val reconstructionText: String,
    val evidenceSupportedConclusions: String,
    val remainingUncertainties: String,
    val legalAnalysisSummary: String,
    val forensicTakeaways: List<String>,
    val legalTakeaways: List<String>
)

enum class SceneType {
    BALCONY_ROOM,
    ART_GALLERY,
    HIGHWAY_CROSSING
}

data class GlossaryTerm(
    val term: String,
    val category: String,
    val definition: String,
    val exampleInCase: String
)

data class AutopsyReport(
    val id: String,
    val caseId: String,
    val victimName: String,
    val age: Int,
    val gender: String,
    val examDate: String,
    val examinerTitle: String,
    val externalObservations: List<String>,
    val internalFindings: List<String>,
    val toxicologySummary: String,
    val limitationsAndUncertainties: String
)

data class AnatomyLesson(
    val id: String,
    val title: String,
    val regionCategory: String,
    val summary: String,
    val structuralDescription: String,
    val relevantCaseContext: String,
    val anatomicalTerms: List<String>
)

data class RealCaseStudy(
    val id: String,
    val dossierId: String,
    val title: String,
    val courtStage: String,
    val summary: String,
    val documentedFacts: List<String>,
    val forensicEvidenceUsed: List<String>,
    val thaiLawApplied: String,
    val judgmentOutcome: String,
    val officialSourceCitation: String
)

data class AiFeedbackResult(
    val caseId: String,
    val userHypothesis: String,
    val supportingEvidence: List<String>,
    val assumptionsToReconsider: List<String>,
    val remainingUncertainties: List<String>,
    val guidingQuestion: String,
    val isDemoResponse: Boolean,
    val timestamp: String
)

data class CaseData(
    val id: String,
    val caseNumber: Int,
    val title: String,
    val subtitle: String,
    val synopsis: String,
    val difficulty: String,
    val estimatedMinutes: Int,
    val sceneType: SceneType,
    val premise: String,
    val learningObjectives: List<String>,
    val introductionText: String,
    val hotspots: List<EvidenceHotspot>,
    val witnessStatements: List<WitnessStatement>,
    val timeline: List<TimelineEvent>,
    val forensicConcepts: List<ForensicConcept>,
    val legalSource: LegalSource,
    val activities: List<LearningActivity>,
    val resolution: CaseResolution,
    val dossierId: String = "CASE-2024-01"
)

data class UserProgressState(
    val completedCaseIds: Set<String> = emptySet(),
    val activeCaseId: String? = null,
    val activeMode: AppMode = AppMode.READ_AND_LEARN,
    val fontSizeOption: FontSizeOption = FontSizeOption.NORMAL,
    val isSoundEnabled: Boolean = false,
    val showHotspotLocationsHint: Boolean = false,
    val caseStepMap: Map<String, Int> = emptyMap(),
    val collectedEvidenceMap: Map<String, Set<String>> = emptyMap(),
    val bookmarkedCaseIds: Set<String> = emptySet(),
    val caseNotesMap: Map<String, String> = emptyMap(),
    val activityAnswersMap: Map<String, Map<String, Int>> = emptyMap(),
    val activityCompletedMap: Map<String, Set<String>> = emptyMap(),
    val revealedResolutionCaseIds: Set<String> = emptySet(),
    val savedAiFeedbackMap: Map<String, String> = emptyMap() // CaseId -> JSON/Formatted AI Feedback
)
