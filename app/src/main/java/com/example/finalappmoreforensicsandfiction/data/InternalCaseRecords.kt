package com.example.finalappmoreforensicsandfiction.data

data class CharacterActionRecord(
    val characterName: String,
    val role: String,
    val timelineActions: List<String>,
    val actualKnowledge: String,
    val statedAlibi: String,
    val deceptionType: String // e.g. "Imperfection in Memory", "Omission", "Deliberate Untruth"
)

data class EvidenceSignificanceRecord(
    val evidenceId: String,
    val evidenceTitle: String,
    val physicalOrigin: String,
    val primarySignificance: String,
    val supportedAlternativeExplanations: List<String>,
    val withheldDetailsUntilResolution: String
)

data class CaseAuthoringRecord(
    val caseId: String,
    val caseTitle: String,
    val chronologicalSequence: List<String>,
    val characterRecords: List<CharacterActionRecord>,
    val evidenceRecords: List<EvidenceSignificanceRecord>,
    val withheldInformation: List<String>,
    val evidenceAvailablePerActivity: Map<String, List<String>>
)

object InternalCaseRecords {

    val case1AuthoringRecord = CaseAuthoringRecord(
        caseId = "case_01",
        caseTitle = "The Closed-Room Shadow",
        chronologicalSequence = listOf(
            "16:00 - Housekeeper Pranee waxed the second-floor balcony floor.",
            "19:30 - Somchai arrived; tea was served on the ground floor with sedative added to Kitti's cup.",
            "20:45 - Somchai ascended to second-floor study; an argument ensued resulting in a torn button.",
            "21:15 - Sedated Kitti was pushed, slipped on wet floor wax, hit railing, and fell to garden below."
        ),
        characterRecords = listOf(
            CharacterActionRecord(
                characterName = "Somchai",
                role = "Business Partner & Suspect",
                timelineActions = listOf("Arrived 19:30", "Dank tea", "Ascended stairs 20:45", "Argued & pushed Kitti 21:15", "Left estate 21:20"),
                actualKnowledge = "Knew Kitti was sedated and went to the second-floor balcony room.",
                statedAlibi = "Claimed he never went upstairs and stayed on ground floor until departure at 21:00.",
                deceptionType = "Deliberate Untruth (Contradicted by Latent Fingerprint on 2nd floor door frame)"
            ),
            CharacterActionRecord(
                characterName = "Pranee",
                role = "Housekeeper",
                timelineActions = listOf("Waxed balcony 16:00", "Served tea 19:30", "Observed Somchai go upstairs 20:45", "Heard fall 21:15"),
                actualKnowledge = "Saw Somchai ascend the stairs at 20:45.",
                statedAlibi = "Remained in kitchen area after serving tea.",
                deceptionType = "Truthful Witness (Imperfect memory on exact tea brewing timing)"
            )
        ),
        evidenceRecords = listOf(
            EvidenceSignificanceRecord(
                evidenceId = "c1_h1",
                evidenceTitle = "Heavy Floor Wax Stain",
                physicalOrigin = "Applied by housekeeper at 16:00; excessive pool near doorway.",
                primarySignificance = "Reduced surface friction, causing slip upon force application.",
                supportedAlternativeExplanations = listOf("Pure accidental slip while walking", "Unintentional slip during push"),
                withheldDetailsUntilResolution = "Excess wax pool was caused by incomplete drying, not chemical trap."
            ),
            EvidenceSignificanceRecord(
                evidenceId = "c1_h2",
                evidenceTitle = "Latent Fingerprint on Glass Frame",
                physicalOrigin = "Somchai's hand contact on 2nd floor sliding door frame.",
                primarySignificance = "Proves Somchai was physically present on the 2nd floor balcony doorway.",
                supportedAlternativeExplanations = listOf("Touched door while visiting on a prior occasion", "Touched door during quarrel"),
                withheldDetailsUntilResolution = "Print orientation indicates outward pushing pressure."
            )
        ),
        withheldInformation = listOf(
            "Exact quantitative blood concentration of sedative medication.",
            "Visual observation of the exact millisecond of the push."
        ),
        evidenceAvailablePerActivity = mapOf(
            "act_c1_1" to listOf("c1_h2"), // Fingerprint refutes alibi
            "act_c1_2" to listOf("c1_h3"), // Torn button proves physical force
            "act_c1_3" to listOf("c1_h1", "c1_h2", "c1_h3", "c1_h5") // Intent vs result legal classification
        )
    )

    val case2AuthoringRecord = CaseAuthoringRecord(
        caseId = "case_02",
        caseTitle = "The Missing Masterpiece",
        chronologicalSequence = listOf(
            "18:30 - Curator left bag containing Keycard M-01 unattended on desk for 20 minutes.",
            "22:00 - Guard Wichai inspected rear garden during heavy rain wearing red polyester jacket.",
            "22:32 - Wichai cut CCTV power cables in blind spot.",
            "22:42 - Wichai tapped duplicated Keycard M-01 and entered exhibition room.",
            "22:45 - Canvas severed with utility knife; alarm triggered by frame pressure sensor."
        ),
        characterRecords = listOf(
            CharacterActionRecord(
                characterName = "Wichai",
                role = "Security Guard & Suspect",
                timelineActions = listOf("Patrolled garden 22:00", "Cut CCTV wires 22:32", "Entered with duplicated card 22:42", "Cut canvas 22:45"),
                actualKnowledge = "Knew camera blind spots and duplicated Keycard M-01.",
                statedAlibi = "Claimed he never entered through rear door or touched Master Keycard.",
                deceptionType = "Deliberate Untruth (Contradicted by red fibers on latch and mud shoeprint match)"
            )
        ),
        evidenceRecords = listOf(
            EvidenceSignificanceRecord(
                evidenceId = "c2_h3",
                evidenceTitle = "Digital Keycard Access Log (22:42)",
                physicalOrigin = "Electronic lock server memory.",
                primarySignificance = "Proves Keycard M-01 was scanned at 22:42.",
                supportedAlternativeExplanations = listOf("Curator used card", "An unauthorized person holding card used it"),
                withheldDetailsUntilResolution = "Keycard was cloned or borrowed during the 18:30 window."
            )
        ),
        withheldInformation = listOf(
            "Location where the stolen canvas was hidden off-site."
        ),
        evidenceAvailablePerActivity = mapOf(
            "act_c2_1" to listOf("c2_h3"),
            "act_c2_2" to listOf("c2_h4"),
            "act_c2_3" to listOf("c2_h1", "c2_h3", "c2_h5")
        )
    )

    val case3AuthoringRecord = CaseAuthoringRecord(
        caseId = "case_03",
        caseTitle = "Traces on the Highway",
        chronologicalSequence = listOf(
            "20:18 - Intersection traffic light faulted to flashing amber caution mode.",
            "20:20 - Theera drove pickup truck at >68 km/h on wet asphalt, skidded 18m, struck pedestrian, and fled.",
            "22:00 - Police located pickup truck with broken headlight at Theera's residence."
        ),
        characterRecords = listOf(
            CharacterActionRecord(
                characterName = "Theera",
                role = "Pickup Truck Driver & Suspect",
                timelineActions = listOf("Drove through intersection 20:20", "Braked hard 18m", "Hit pedestrian", "Fled scene"),
                actualKnowledge = "Knew he struck a person at the intersection.",
                statedAlibi = "Claimed he hit a gate post at home yesterday.",
                deceptionType = "Deliberate Untruth (Conclusively refuted by Physical Fracture Fit of headlight shards)"
            )
        ),
        evidenceRecords = listOf(
            EvidenceSignificanceRecord(
                evidenceId = "c3_h3",
                evidenceTitle = "Headlight Glass & Polycarbonate Fragments",
                physicalOrigin = "Broken headlight housing shards left on asphalt.",
                primarySignificance = "Physical Fracture Fit puzzle match conclusively links truck to scene.",
                supportedAlternativeExplanations = listOf("None — Fracture Fit is an individual physical match."),
                withheldDetailsUntilResolution = "Detailed microscopic edge striation mapping."
            )
        ),
        withheldInformation = listOf(
            "Long-term medical prognosis of the victim."
        ),
        evidenceAvailablePerActivity = mapOf(
            "act_c3_1" to listOf("c3_h1"),
            "act_c3_2" to listOf("c3_h3"),
            "act_c3_3" to listOf("c3_h1", "c3_h2", "c3_h3")
        )
    )
}
