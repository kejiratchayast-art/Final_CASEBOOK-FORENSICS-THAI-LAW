package com.example.finalappmoreforensicsandfiction.data

object CasesProvider {

    const val disclaimerNotice = "All cases are fictional and intended for educational use. This content is not legal advice."

    // Shared Glossary Terms
    val sharedGlossary = listOf(
        GlossaryTerm(
            term = "Locard's Exchange Principle",
            category = "Forensics",
            definition = "A forensic science principle stating that every contact leaves a trace. Whenever two objects collide or touch, material is transferred between them.",
            exampleInCase = "Case 1: Torn shirt button threads and latent fingerprints left at the scene."
        ),
        GlossaryTerm(
            term = "Latent Fingerprint",
            category = "Forensics",
            definition = "An invisible impression left by sweat and oils from friction ridges on finger surfaces, revealed using powders or chemical reagents.",
            exampleInCase = "Case 1: Overlapped latent print recovered from the balcony glass door edge."
        ),
        GlossaryTerm(
            term = "Physical / Fracture Fit",
            category = "Forensics",
            definition = "The process of matching broken or severed fragments of an object back together like puzzle pieces to prove they originated from the same item.",
            exampleInCase = "Case 3: Broken polycarbonate headlight fragment fitted perfectly onto the suspect's pickup truck."
        ),
        GlossaryTerm(
            term = "Beyond Reasonable Doubt (Section 227)",
            category = "Thai Law",
            definition = "The standard of proof required in Thai criminal procedure. A court cannot convict a defendant unless convinced of guilt beyond reasonable doubt.",
            exampleInCase = "Case 1: The court gives the benefit of reasonable doubt if evidence leaves open the possibility of a pure accident."
        ),
        GlossaryTerm(
            term = "Theft at Night / Aggravated Theft (Section 335)",
            category = "Thai Law",
            definition = "Under Section 335 of the Thai Criminal Code, committing theft at night or by overcoming barriers increases the statutory penalty.",
            exampleInCase = "Case 2: Stealing the painting at 22:45 after cutting CCTV wires and bypassing the rear lock."
        )
    )

    // CASE 1: The Closed-Room Shadow
    private val case1Hotspots = listOf(
        EvidenceHotspot(
            id = "c1_h1",
            xRatio = 0.32f,
            yRatio = 0.74f,
            title = "Heavy Floor Wax Stain",
            category = EvidenceCategory.CHEMICAL,
            observedFacts = listOf(
                "A 45-cm skid mark is visible on the parquet floor near the balcony sliding door.",
                "Uncured floor wax chemical residue is concentrated along the edge."
            ),
            forensicInterpretations = listOf(
                "The skid mark indicates rapid loss of shoe heel traction across the polished surface.",
                "Abusually heavy wax buildup significantly reduced the floor friction coefficient."
            ),
            limitations = "Chemical wax alone cannot determine whether the spill was an innocent cleaning accident or deliberately placed."
        ),
        EvidenceHotspot(
            id = "c1_h2",
            xRatio = 0.64f,
            yRatio = 0.42f,
            title = "Latent Fingerprint on Glass Frame",
            category = EvidenceCategory.FINGERPRINT,
            observedFacts = listOf(
                "An overlapped latent fingerprint was recovered from the interior sliding glass frame.",
                "Initial ridge minutiae matching identifies the print as belonging to Somchai."
            ),
            forensicInterpretations = listOf(
                "The ridge pressure pattern indicates rapid stabilization or pushing against the door.",
                "Confirms Somchai physically touched the balcony door frame."
            ),
            limitations = "Fingerprints prove physical contact but cannot establish the exact time or intent of touch."
        ),
        EvidenceHotspot(
            id = "c1_h3",
            xRatio = 0.48f,
            yRatio = 0.62f,
            title = "Torn Shirt Button",
            category = EvidenceCategory.CLOTHING,
            observedFacts = listOf(
                "A mother-of-pearl button with frayed thread was found near the desk corner.",
                "The victim's shirt was missing its second button, with thread fibers pulled violently."
            ),
            forensicInterpretations = listOf(
                "Sudden thread tension is consistent with physical pulling or grappling during an altercation.",
                "Aligns with Locard's Exchange Principle regarding physical contact."
            ),
            limitations = "Torn threads can also occur if the wearer catches their shirt while falling against furniture."
        ),
        EvidenceHotspot(
            id = "c1_h4",
            xRatio = 0.22f,
            yRatio = 0.58f,
            title = "Fresh Blood Spatter on Balcony Rail",
            category = EvidenceCategory.INJURY,
            observedFacts = listOf(
                "Fresh blood spatter is present on the metal railing 95 cm above floor level.",
                "The victim suffered a posterior scalp laceration matching the railing width."
            ),
            forensicInterpretations = listOf(
                "The victim's head struck the railing forcefully before tumbling over the edge.",
                "Spatter directionality confirms blunt impact occurred prior to the fall."
            ),
            limitations = "Blunt impact cannot distinguish between accidental tripping and a deliberate push."
        ),
        EvidenceHotspot(
            id = "c1_h5",
            xRatio = 0.78f,
            yRatio = 0.68f,
            title = "Teacup with White Sediment",
            category = EvidenceCategory.CHEMICAL,
            observedFacts = listOf(
                "Two teacups were found on the desk; one contained leftover tea with fine white sediment.",
                "Laboratory screening detected fast-acting sedative drug contamination."
            ),
            forensicInterpretations = listOf(
                "The victim likely ingested a central nervous system depressant causing dizziness and loss of balance.",
                "Explains why the victim failed to brace during the slip."
            ),
            limitations = "Full blood toxicology reports are required to quantify exact blood concentration levels."
        )
    )

    private val case1Witnesses = listOf(
        WitnessStatement(
            id = "c1_w1",
            witnessName = "Somchai",
            role = "Victim's Business Associate / Suspect",
            statement = "I visited Kitti around 19:30 for tea and business. We drank tea downstairs. I never went upstairs to the second floor and left at 21:00. Kitti was fine when I departed.",
            inconsistencies = listOf(
                "Contradicted by Somchai's latent fingerprint on the second-floor balcony door frame.",
                "Contradicted by the housekeeper who saw Somchai walk upstairs at 20:45."
            ),
            timeRecorded = "22:15",
            interviewQuestions = listOf(
                InterviewQuestion("q1_1", "What was the purpose of your visit at 19:30?", "We discussed a joint real estate venture over tea in the downstairs living room."),
                InterviewQuestion("q1_2", "Did you ever go upstairs to the second-floor study or balcony?", "No, I remained downstairs the entire time and left at 21:00.", "c1_h2"),
                InterviewQuestion("q1_3", "Did you notice anyone else in the house?", "Only Pranee, the housekeeper, who served tea and mentioned polishing floors earlier.")
            )
        ),
        WitnessStatement(
            id = "c1_w2",
            witnessName = "Pranee",
            role = "Housekeeper",
            statement = "I finished waxing the balcony floor at 16:00. At 20:45, I saw Somchai walk up the stairs to the second floor. At 21:15, I heard a loud crash in the garden.",
            inconsistencies = listOf(
                "Floor waxing timing matches partial drying, but the excessive wax pool at the doorway remains unexplained."
            ),
            timeRecorded = "22:40",
            interviewQuestions = listOf(
                InterviewQuestion("q2_1", "When did you apply floor wax on the balcony?", "Around 16:00. Floor wax takes several hours to cure completely."),
                InterviewQuestion("q2_2", "Did you observe anyone going upstairs later?", "Yes, I saw Somchai walk upstairs toward Kitti's study at 20:45."),
                InterviewQuestion("q2_3", "What did you hear when the fall occurred?", "A sudden thud in the garden at 21:15. I ran outside immediately.")
            )
        )
    )

    private val case1Timeline = listOf(
        TimelineEvent("t1", "16:00", "Balcony Floor Waxed", "Housekeeper applied floor wax on the second-floor balcony.", "Second Floor Balcony"),
        TimelineEvent("t2", "19:30", "Somchai Arrives", "Somchai arrived for business discussion and tea.", "Ground Floor Living Room"),
        TimelineEvent("t3", "20:45", "Ascent to Second Floor (Contested)", "Housekeeper saw Somchai go upstairs; Somchai denies going upstairs.", "Staircase", isContested = true, evidenceId = "c1_h2"),
        TimelineEvent("t4", "21:15", "Fatal Fall Occurs", "Loud impact heard as Kitti fell from the balcony to the garden below.", "Garden"),
        TimelineEvent("t5", "21:30", "Police & Forensics Arrive", "Crime scene secured and physical evidence collected.", "Kitti Estate")
    )

    private val case1ForensicConcepts = listOf(
        ForensicConcept(
            id = "fc_locard",
            title = "Locard's Exchange Principle",
            category = "Basic Forensic Science",
            summary = "Every contact leaves a trace. Physical contact between objects results in material transfer.",
            detailedExplanation = "Formulated by Dr. Edmond Locard, this principle dictates that a perpetrator brings evidence into a crime scene and takes evidence away. In this case, latent fingerprints and torn shirt fibers represent physical exchange.",
            scientificLimitations = "Trace evidence can fade, be destroyed, or suffer contamination if crime scene perimeters are not immediately established.",
            relatedCaseIds = listOf("case_01", "case_02")
        ),
        ForensicConcept(
            id = "fc_fingerprint",
            title = "Latent Fingerprint Analysis",
            category = "Personal Identification",
            summary = "Detection and comparison of friction ridge impressions left by sweat and skin oils.",
            detailedExplanation = "Latent prints are made visible using forensic powders or chemical developers, then compared against known exemplars using minutiae ridge characteristics.",
            scientificLimitations = "Fingerprints establish WHO touched an object, but cannot specify WHEN the touch occurred or the INDIVIDUAL'S INTENT.",
            relatedCaseIds = listOf("case_01")
        )
    )

    private val case1LegalSource = LegalSource(
        lawTitle = "Criminal Code of Thailand & Criminal Procedure Code",
        sectionArticle = "Criminal Code Sections 288 & 290, Criminal Procedure Code Section 227",
        officialSourceTitle = "Office of the Council of State — Thai",
        officialSourceUrl = "https://www.krisdika.go.th",
        relevantAmendment = "Act Amending Criminal Code (No. 26) B.E. 2560 (2017)",
        effectiveDate = "November 13, 1956 (Original) / February 10, 2017 (Amended)",
        verificationDate = "May 10, 2024",
        verificationStatus = "Verified against official primary source — Unofficial English translation",
        revisionHistory = "v1.2 — Aligned with official Gazette text",
        contentVersion = "1.2",
        statutoryText = """
            [Criminal Code Section 288]
            "Whoever kills another person shall be punished with death, imprisonment for life, or imprisonment of fifteen to twenty years."
            
            [Criminal Code Section 290]
            "Whoever causes death to another person by inflicting injury upon life/body without intent to kill shall be punished with imprisonment of three to fifteen years."
            
            [Criminal Procedure Code Section 227]
            "The Court shall exercise its discretion in weighing all evidence. Do not convict the defendant unless convinced that an offense was committed and defendant committed it.
            When reasonable doubt exists whether defendant committed the offense, give the benefit of doubt to the defendant."
        """.trimIndent(),
        educationalExplanation = """
            Thai criminal legal analysis requires evaluating both subjective intent (intent to kill vs intent to harm vs negligence) and objective act-result causation.
            
            If a suspect engaged in a struggle and pushed the victim without intent to kill, causing the victim to slip on wax and fall to their death, Section 290 (Inflicting injury causing death) applies. If there was specific intent to push over the railing, Section 288 (Murder) applies.
            
            Under Section 227 of the Criminal Procedure Code, if forensic evidence leaves open a reasonable doubt that the fall was a pure accident, the court must acquit or give the benefit of doubt to the defendant.
        """.trimIndent(),
        caseApplication = "In this fictional case, latent fingerprints and torn shirt buttons prove physical contact occurred on the second floor, contradicting Somchai's alibi. However, establishing murder under Section 288 requires proving specific intent beyond reasonable doubt.",
        legalQuestion = "Does pushing a victim during a heated argument resulting in a fatal balcony fall constitute Murder (Section 288) or Inflicting Injury Causing Death (Section 290) under Thai Law?",
        fictionalFacts = "Somchai went upstairs, argued with Kitti, grabbed his shirt tearing a button, and pushed him while Kitti was sedated, causing Kitti to slip on floor wax and fall over the railing.",
        conditionsAndElements = listOf(
            "Section 288 requires specific intent to kill (desire for result or foreseeing result).",
            "Section 290 requires intent to inflict injury without intent to kill, resulting in death.",
            "Causation must exist between the push and the fatal fall."
        ),
        missingOrUncertainFacts = "Exact quantitative toxicology levels in Kitti's blood and direct eyewitness observation at the moment of the fall.",
        limitsOfConclusion = "Educational analysis based strictly on fictional case facts. Final classification depends on judicial discretion."
    )

    private val case1Activities = listOf(
        LearningActivity(
            id = "act_c1_1",
            type = ActivityType.EVIDENCE_REASONING,
            title = "Activity 1: Evaluate Witness Contradiction",
            instruction = "Somchai claimed: 'I never walked upstairs to the second floor.' Which physical evidence directly refutes this claim?",
            learningObjectiveRef = "Identify contradictions between witness statements and physical evidence.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Floor wax stain on the balcony floor.",
                    isCorrect = false,
                    explanation = "Incorrect: Floor wax shows surface slipperiness but does not identify who went upstairs."
                ),
                ActivityOption(
                    id = 2,
                    text = "Somchai's latent fingerprint recovered from the second-floor sliding glass door frame.",
                    isCorrect = true,
                    explanation = "Correct! Physical latent fingerprint evidence proves Somchai touched the second-floor door frame, directly refuting his statement."
                ),
                ActivityOption(
                    id = 3,
                    text = "The teacup containing sediment on the ground floor desk.",
                    isCorrect = false,
                    explanation = "Incorrect: Teacups on the ground floor show tea consumption downstairs but do not prove presence on the second floor."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to refute the statement.",
                    isCorrect = false,
                    explanation = "Incorrect: The fingerprint evidence on the second floor provides sufficient physical proof."
                )
            )
        ),
        LearningActivity(
            id = "act_c1_2",
            type = ActivityType.INCONSISTENCY,
            title = "Activity 2: Forensic Interpretation of Clothing Evidence",
            instruction = "Regarding the 'torn shirt button with pulled thread', which forensic conclusion is most justified?",
            learningObjectiveRef = "Distinguish justified forensic conclusions from unsupported assumptions.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Thread tension and tearing indicate sudden forceful pulling, consistent with grappling or physical struggle.",
                    isCorrect = true,
                    explanation = "Correct! Mechanical thread deformation indicates rapid external tensile force, supporting a struggle."
                ),
                ActivityOption(
                    id = 2,
                    text = "The torn button proves Somchai is guilty of premeditated murder.",
                    isCorrect = false,
                    explanation = "Incorrect: A torn button indicates physical struggle, but button evidence alone cannot identify a specific suspect or establish premeditation."
                ),
                ActivityOption(
                    id = 3,
                    text = "The floor wax chemical corroded the thread causing it to fall off.",
                    isCorrect = false,
                    explanation = "Incorrect: Floor wax chemicals do not instantly dissolve cotton sewing thread."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to determine the cause of button damage.",
                    isCorrect = false,
                    explanation = "Incorrect: Physical analysis of torn threads allows a justified conclusion regarding tensile force."
                )
            )
        ),
        LearningActivity(
            id = "act_c1_3",
            type = ActivityType.MULTIPLE_CHOICE_REASON,
            title = "Activity 3: Legal Analysis under Thai Criminal Code",
            instruction = "If evidence proves Somchai pushed Kitti during an argument without intending to cause death, but the push caused Kitti to slip on wax and fall fatally, which Thai Criminal Code section applies?",
            learningObjectiveRef = "Apply Thai legal provisions (Sections 288 & 290) to established facts.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Premeditated Murder (Section 288)",
                    isCorrect = false,
                    explanation = "Incorrect: Section 288 requires specific intent to kill."
                ),
                ActivityOption(
                    id = 2,
                    text = "Inflicting Injury Causing Death (Section 290)",
                    isCorrect = true,
                    explanation = "Correct! Intent to assault (push) without intent to kill, where death results, meets Section 290 elements."
                ),
                ActivityOption(
                    id = 3,
                    text = "No criminal liability because the floor wax caused the fall.",
                    isCorrect = false,
                    explanation = "Incorrect: A push that triggers a fatal slip establishes legal causation."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to determine the applicable law.",
                    isCorrect = false,
                    explanation = "Incorrect: Given the stated premise regarding intent and result, Section 290 can be determined."
                )
            )
        )
    )

    private val case1Resolution = CaseResolution(
        reconstructionText = """
            1. At 19:30, Somchai visited Kitti downstairs for tea and business. Sedative medication was placed into Kitti's teacup.
            2. At 20:45, Somchai walked upstairs to the second-floor study. A heated argument ensued, leading to grappling that tore a button from Kitti's shirt.
            3. Somchai pushed the sedated victim, who slipped on uncured floor wax, struck his head on the balcony railing, and fell to his death in the garden below.
        """.trimIndent(),
        evidenceSupportedConclusions = "Latent fingerprints and torn shirt button threads establish Somchai's presence and physical struggle on the second floor. Chemical sediment confirms sedation.",
        remainingUncertainties = "Whether floor wax was applied excessively by accident or deliberately to create a hazard remains scientifically unproven.",
        legalAnalysisSummary = "Based on intent to push without specific intent to kill, Somchai's actions meet the elements of Inflicting Injury Causing Death under Thai Criminal Code Section 290.",
        forensicTakeaways = listOf(
            "Locard's Exchange Principle proves physical interaction leaves traceable material exchange.",
            "Latent fingerprints confirm WHO touched an object, but not WHEN or WITH WHAT INTENT.",
            "Chemical toxicology must be evaluated in conjunction with physical impact spatter."
        ),
        legalTakeaways = listOf(
            "Thai Criminal Code Section 288 requires intent to kill; Section 290 applies when intent is limited to injury.",
            "Criminal Procedure Code Section 227 protects defendants whenever reasonable doubt exists."
        )
    )

    val case1 =                                                                                                                CaseData(
        id = "case_01",
        caseNumber = 1,
        dossierId = "CASE-2024-001",
        title = "The Closed-Room Shadow",
        subtitle = "The Balcony Fall and Floor Polish Mystery",
        synopsis = "A prominent local figure falls from a second-floor balcony. Investigation reveals a heavily waxed floor stain, torn shirt button, and latent glass fingerprints.",
        difficulty = "Easy",
        estimatedMinutes = 12,
        sceneType = SceneType.BALCONY_ROOM,
        premise = "On a rainy evening, Kitti was found dead in the garden below his second-floor study balcony. Police face two possibilities: an accidental slip or a physical push following a quarrel.",
        learningObjectives = listOf(
            "Apply Locard's Exchange Principle to trace physical evidence.",
            "Distinguish observed crime scene facts from forensic interpretations.",
            "Analyze offences under Thai Criminal Code Sections 288 and 290.",
            "Understand the standard of proof beyond reasonable doubt under CPC Section 227."
        ),
        introductionText = """
            Kitti Estate, 21:30.
            
            A dull thud in the garden prompted Pranee, the housekeeper, to rush outside. She discovered the body of Kitti lying motionless beneath his second-floor study balcony.
            
            Forensic investigators inspecting the second-floor study found the sliding glass balcony door standing open. Parquet wood flooring near the doorway was slick with fresh floor wax. A fresh blood stain was found on the metal railing, and a pearl shirt button lay near the desk corner.
            
            Somchai, a business associate who visited earlier, claimed he departed at 21:00 without ever going upstairs. However, physical trace evidence tells a different story...
        """.trimIndent(),
        hotspots = case1Hotspots,
        witnessStatements = case1Witnesses,
        timeline = case1Timeline,
        forensicConcepts = case1ForensicConcepts,
        legalSource = case1LegalSource,
        activities = case1Activities,
        resolution = case1Resolution
    )

    // CASE 2: The Missing Masterpiece
    private val case2Hotspots = listOf(
        EvidenceHotspot(
            id = "c2_h1",
            xRatio = 0.50f,
            yRatio = 0.36f,
            title = "Empty Picture Frame",
            category = EvidenceCategory.DOCUMENT,
            observedFacts = listOf(
                "The canvas was severed neatly along all four inner edges of the wooden frame.",
                "A heavy-duty utility knife blade was found dropped on the floor."
            ),
            forensicInterpretations = listOf(
                "The perpetrator possessed tool familiarity and removed the canvas methodically rather than tearing it in panic.",
                "Uniform cut lines indicate deliberate preparation."
            ),
            limitations = "Cut marks narrow the suspect pool to individuals with tool skills but cannot uniquely identify a specific person."
        ),
        EvidenceHotspot(
            id = "c2_h2",
            xRatio = 0.28f,
            yRatio = 0.82f,
            title = "Muddy Shoeprint Impressions",
            category = EvidenceCategory.FOOTPRINT,
            observedFacts = listOf(
                "Three fresh wet mud shoeprint impressions lead from the rear garden door toward the gallery.",
                "Athletic shoe tread pattern matching Size 42 (EU)."
            ),
            forensicInterpretations = listOf(
                "Establishes that the intruder entered through the rear garden door during heavy rain.",
                "Tread pattern provides class characteristics for footwear model comparison."
            ),
            limitations = "Without unique individual wear marks, class characteristics alone cannot confirm a specific pair of shoes."
        ),
        EvidenceHotspot(
            id = "c2_h3",
            xRatio = 0.82f,
            yRatio = 0.48f,
            title = "Digital Keycard Access Log (22:42)",
            category = EvidenceCategory.DIGITAL,
            observedFacts = listOf(
                "The system logged Keycard M-01 (Master Card) tapping the gallery door lock at 22:42:15.",
                "Keycard M-01 is registered to Curator Methavee."
            ),
            forensicInterpretations = listOf(
                "Keycard M-01 was physically scanned immediately prior to the alarm.",
                "Aligns with the entry timeline into the exhibition room."
            ),
            limitations = "Digital logs record WHICH CARD was scanned, but cannot verify WHO WAS HOLDING THE CARD unless biometric data or video correlates."
        ),
        EvidenceHotspot(
            id = "c2_h4",
            xRatio = 0.15f,
            yRatio = 0.55f,
            title = "Red Jacket Polyester Fibers",
            category = EvidenceCategory.CLOTHING,
            observedFacts = listOf(
                "Two bright red synthetic fibers were snagged on the rear door latch metal burr.",
                "FTIR spectroscopy confirmed red polyester fiber composition."
            ),
            forensicInterpretations = listOf(
                "The intruder wore red synthetic clothing that brushed against the rear door latch during entry.",
                "Matches the red security jacket belonging to Guard Wichai."
            ),
            limitations = "Polyester fibers are mass-produced; spectral comparison against suspect garments is necessary."
        ),
        EvidenceHotspot(
            id = "c2_h5",
            xRatio = 0.70f,
            yRatio = 0.22f,
            title = "Severed CCTV Power Cable",
            category = EvidenceCategory.DIGITAL,
            observedFacts = listOf(
                "The CCTV camera in the blind spot had its power and video cables severed.",
                "Cut marks match diagonal wire-cutting pliers."
            ),
            forensicInterpretations = listOf(
                "The perpetrator knew camera positions and blind spots, indicating inside knowledge or prior reconnaissance.",
                "Cable cutting occurred approximately 10 minutes prior to the alarm."
            ),
            limitations = "General wire cuts lack unique microscopic striations unless tool blade damage exists."
        )
    )

    private val case2Witnesses = listOf(
        WitnessStatement(
            id = "c2_w1",
            witnessName = "Wichai",
            role = "Security Guard / Suspect",
            statement = "I was on night duty from 18:00 to 06:00. During rain at 22:00, I wore my red jacket while inspecting the rear garden, but I did not enter through the rear door and never touched the master keycard.",
            inconsistencies = listOf(
                "His red jacket matches the red fibers snagged on the rear door latch.",
                "His statement denying entering via the rear door is contradicted by fresh mud shoeprints leading inward."
            ),
            timeRecorded = "23:15",
            interviewQuestions = listOf(
                InterviewQuestion("q2_1", "Where were you stationed between 22:00 and 22:45?", "I was on perimeter patrol in the rear garden wearing my red raincoat jacket."),
                InterviewQuestion("q2_2", "Did you open or enter through the rear garden door?", "No, the rear door was locked. I stayed outside on the walkway.", "c2_h4"),
                InterviewQuestion("q2_3", "Did you notice the CCTV video signal drop?", "I saw a flicker on the monitor around 22:35, but assumed it was rain interference.")
            )
        ),
        WitnessStatement(
            id = "c2_w2",
            witnessName = "Methavee",
            role = "Art Gallery Curator",
            statement = "I left for home at 19:00. Master Keycard M-01 is kept in my shoulder bag. However, at 18:30, I left my bag unattended on my ground-floor desk for 20 minutes while visiting the restroom.",
            inconsistencies = listOf(
                "Leaving the bag unattended created a window of opportunity for someone to clone or borrow the keycard."
            ),
            timeRecorded = "23:45",
            interviewQuestions = listOf(
                InterviewQuestion("q2_4", "Where was Master Keycard M-01 kept during the evening?", "In my shoulder bag on my desk while I attended a 20-minute meeting at 18:30."),
                InterviewQuestion("q2_5", "Who had physical access to your desk area during that time?", "The office is open to staff, including security guards on shift.")
            )
        )
    )

    private val case2Timeline = listOf(
        TimelineEvent("t1", "18:30", "Keycard Unattended", "Curator left shoulder bag containing Keycard M-01 on desk for 20 minutes.", "Ground Floor Desk"),
        TimelineEvent("t2", "22:00", "Guard Inspects Garden", "Guard Wichai wore red jacket during rain patrol in the rear garden.", "Rear Garden"),
        TimelineEvent("t3", "22:32", "CCTV Disabled", "CCTV camera cables in gallery blind spot severed.", "Second Floor Corridor"),
        TimelineEvent("t4", "22:42", "Gallery Door Unlocked", "Keycard M-01 tapped to open gallery exhibition room door.", "Gallery Door"),
        TimelineEvent("t5", "22:45", "Alarm Triggered", "Frame pressure sensor detected missing canvas and activated security alarm.", "Exhibition Room")
    )

    private val case2ForensicConcepts = listOf(
        ForensicConcept(
            id = "fc_shoeprint",
            title = "Shoeprint & Tread Analysis",
            category = "Physical Forensics",
            summary = "Examination of footwear impressions to compare class and individual wear characteristics.",
            detailedExplanation = "Class characteristics (brand, pattern, size) narrow footwear models, while individual characteristics (cuts, embedded stones) can uniquely identify a specific shoe.",
            scientificLimitations = "Incomplete mud impressions provide class identification but cannot achieve individual identification without unique wear features.",
            relatedCaseIds = listOf("case_02", "case_03")
        ),
        ForensicConcept(
            id = "fc_digital_log",
            title = "Digital Access Audit Trails",
            category = "Digital Forensics",
            summary = "Analysis of electronic access logs and server timestamp records.",
            detailedExplanation = "Access control logs record precise timestamps and credential IDs, providing verifiable audit trails.",
            scientificLimitations = "Access logs verify WHICH CREDENTIAL was used, but cannot verify WHO HELD THE CREDENTIAL without physical or biometric correlation.",
            relatedCaseIds = listOf("case_02")
        )
    )

    private val case2LegalSource = LegalSource(
        lawTitle = "Criminal Code of Thailand — Offences Against Property",
        sectionArticle = "Criminal Code Sections 334, 335 (1)(8), & 357",
        officialSourceTitle = "Office of the Council of State — Thai",
        officialSourceUrl = "https://www.krisdika.go.th",
        relevantAmendment = "Act Amending Criminal Code (No. 26) B.E. 2560 (2017)",
        effectiveDate = "November 13, 1956 (Original) / February 10, 2017 (Amended)",
        verificationDate = "May 10, 2024",
        verificationStatus = "Verified against official primary source — Unofficial English translation",
        revisionHistory = "v1.2 — Aligned with official Gazette text",
        contentVersion = "1.2",
        statutoryText = """
            [Criminal Code Section 334]
            "Whoever dishonestly takes away property of another person... commits theft."
            
            [Criminal Code Section 335]
            "Whoever commits theft:
            (1) At night...
            (8) By damaging barriers intended for protection of persons/property, or by passing through such barriers...
            shall be punished with imprisonment of one to five years."
            
            [Criminal Code Section 357]
            "Whoever assists in concealing, disposing of, or receiving property obtained through theft... commits receiving stolen property."
        """.trimIndent(),
        educationalExplanation = """
            Under Thai criminal law, basic theft (Section 334) becomes aggravated theft (Section 335) when committed at night (1) or by bypassing protective security barriers (8).
            
            A key legal distinction exists between a principal thief (Section 335) and a receiver of stolen goods (Section 357). If an individual merely assists in hiding stolen property without participating in the theft, Section 357 applies.
        """.trimIndent(),
        caseApplication = "In this fictional case, entering at 22:45 (nighttime) and severing security cables (bypassing protective barriers) elevates basic theft to aggravated theft under Section 335 (1) and (8).",
        legalQuestion = "Does entering a gallery at night, cutting CCTV cables, and stealing artwork constitute Aggravated Theft under Section 335?",
        fictionalFacts = "Wichai duplicated the master keycard, cut CCTV cables at 22:32, entered at 22:42 at night, and cut out the 12-million-baht canvas.",
        conditionsAndElements = listOf(
            "Section 334 requires dishonest taking of another's property.",
            "Section 335(1) requires the theft to occur during night hours.",
            "Section 335(8) requires passing through or damaging protective barriers."
        ),
        missingOrUncertainFacts = "Recovery of the stolen canvas and utility knife fingerprints.",
        limitsOfConclusion = "Educational analysis based on fictional facts. Search warrants are required to recover stolen property."
    )

    private val case2Activities = listOf(
        LearningActivity(
            id = "act_c2_1",
            type = ActivityType.EVIDENCE_REASONING,
            title = "Activity 1: Evaluate Digital Keycard Log Evidence",
            instruction = "The audit log recorded Keycard M-01 at 22:42. What is the scientifically justified conclusion?",
            learningObjectiveRef = "Recognize the limitations of digital credential audit logs.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Proves 100% that Curator Methavee entered the room at 22:42.",
                    isCorrect = false,
                    explanation = "Incorrect: Keycard logs record credential usage, not the physical identity of the user."
                ),
                ActivityOption(
                    id = 2,
                    text = "Confirms Keycard M-01 was scanned, but cannot verify WHO held the card without corroborating physical evidence.",
                    isCorrect = true,
                    explanation = "Correct! Audit logs verify credential authorization, but physical identity correlation requires additional proof."
                ),
                ActivityOption(
                    id = 3,
                    text = "Digital keycard logs are unreliable and should be disregarded.",
                    isCorrect = false,
                    explanation = "Incorrect: Digital logs are highly reliable for credential tracking, though subject to identity verification limits."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information regarding digital keycards.",
                    isCorrect = false,
                    explanation = "Incorrect: The limitation of digital credential tracking is well established."
                )
            )
        ),
        LearningActivity(
            id = "act_c2_2",
            type = ActivityType.INCONSISTENCY,
            title = "Activity 2: Correlate Physical Fiber Evidence",
            instruction = "Which suspect is physically connected to the 'red polyester fibers' found on the rear door latch?",
            learningObjectiveRef = "Correlate physical trace evidence with suspect statements.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Curator Methavee, who wore a tan coat.",
                    isCorrect = false,
                    explanation = "Incorrect: Methavee did not wear red synthetic garments."
                ),
                ActivityOption(
                    id = 2,
                    text = "Guard Wichai, who wore a red polyester jacket during night patrol.",
                    isCorrect = true,
                    explanation = "Correct! Wichai's red polyester jacket matches the color and fiber composition recovered from the latch."
                ),
                ActivityOption(
                    id = 3,
                    text = "The gallery housekeeper.",
                    isCorrect = false,
                    explanation = "Incorrect: No fiber connection exists for the housekeeper."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to identify clothing connection.",
                    isCorrect = false,
                    explanation = "Incorrect: Fiber composition directly correlates with Wichai's jacket."
                )
            )
        ),
        LearningActivity(
            id = "act_c2_3",
            type = ActivityType.MULTIPLE_CHOICE_REASON,
            title = "Activity 3: Legal Classification under Thai Law",
            instruction = "Stealing artwork at 22:45 by cutting CCTV cables and entering via a rear lock falls under which Thai Criminal Code provision?",
            learningObjectiveRef = "Classify aggravated theft offences under Section 335.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Basic Theft (Section 334)",
                    isCorrect = false,
                    explanation = "Incorrect: Nighttime entry and barrier tampering constitute aggravating circumstances."
                ),
                ActivityOption(
                    id = 2,
                    text = "Aggravated Theft at Night & Bypassing Barriers (Section 335 (1) & (8))",
                    isCorrect = true,
                    explanation = "Correct! Occurring at night (1) and damaging/bypassing protective security barriers (8) elevates theft to Section 335."
                ),
                ActivityOption(
                    id = 3,
                    text = "Embezzlement (Section 352)",
                    isCorrect = false,
                    explanation = "Incorrect: Embezzlement requires prior lawful possession of the property."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to classify offence.",
                    isCorrect = false,
                    explanation = "Incorrect: The facts satisfy Section 335 elements."
                )
            )
        )
    )

    private val case2Resolution = CaseResolution(
        reconstructionText = """
            1. At 18:30, Guard Wichai noticed Curator Methavee's bag unattended on her desk and duplicated the master keycard.
            2. At 22:32, Wichai cut CCTV power cables in the gallery blind spot to disable video recording.
            3. At 22:42, Wichai wore his red jacket, tapped the duplicated keycard M-01, cut out the canvas with a utility knife, and fled via the rear garden door, leaving red fibers and muddy shoeprints.
        """.trimIndent(),
        evidenceSupportedConclusions = "Red polyester fibers on the door latch and matching mud shoeprints establish Wichai's entry through the rear door. Keycard log proves credential duplication.",
        remainingUncertainties = "Search warrants are needed to locate the stolen canvas and utility knife handle to obtain fingerprint confirmation.",
        legalAnalysisSummary = "Committed at night by cutting security barriers, the act constitutes Aggravated Theft under Criminal Code Section 335 (1) and (8).",
        forensicTakeaways = listOf(
            "Shoeprint tread pattern provides class characteristics for model matching.",
            "Digital audit logs verify credential usage but require physical identity correlation.",
            "Fiber analysis offers valuable transfer evidence connecting individuals to doors/latches."
        ),
        legalTakeaways = listOf(
            "Theft at night and bypassing barriers elevate penalties under Thai Criminal Code Section 335.",
            "Using stolen or duplicated credentials constitutes theft by trickery."
        )
    )

    val case2 = CaseData(
        id = "case_02",
        caseNumber = 2,
        dossierId = "CASE-2024-002",
        title = "The Missing Masterpiece",
        subtitle = "The Art Gallery Theft and Keycard Audit Mystery",
        synopsis = "A 12-million-baht antique painting vanishes from an art gallery. Investigators find muddy shoeprints, a master keycard log at 22:42, and red fibers on the rear latch.",
        difficulty = "Medium",
        estimatedMinutes = 14,
        sceneType = SceneType.ART_GALLERY,
        premise = "During a rainstorm at 22:45, gallery alarms sounded. Guards found the frame empty, canvas neatly cut out, CCTV cables severed, and wet mud prints on the floor.",
        learningObjectives = listOf(
            "Understand footwear tread pattern analysis.",
            "Evaluate digital audit log evidence and identity correlation limits.",
            "Distinguish Basic Theft (Section 334) from Aggravated Theft (Section 335)."
        ),
        introductionText = """
            Taweesap Art Gallery, 22:45.
            
            Security alarms echoed through the main building. Guard Wichai arrived at the second-floor exhibition room to find 'Morning Light'—a masterpiece valued at 12 million baht—missing from its golden frame.
            
            Investigators found wet mud shoeprints leading from the rear door up to the exhibition hall, red clothing fibers caught on the rear latch, and severed CCTV camera wires.
            
            System logs showed Master Keycard M-01 tapped the door lock at 22:42. Keycard M-01 was registered exclusively to Curator Methavee...
        """.trimIndent(),
        hotspots = case2Hotspots,
        witnessStatements = case2Witnesses,
        timeline = case2Timeline,
        forensicConcepts = case2ForensicConcepts,
        legalSource = case2LegalSource,
        activities = case2Activities,
        resolution = case2Resolution
    )

    // CASE 3: Traces on the Highway
    private val case3Hotspots = listOf(
        EvidenceHotspot(
            id = "c3_h1",
            xRatio = 0.40f,
            yRatio = 0.75f,
            title = "18-Meter Skid Mark",
            category = EvidenceCategory.VEHICLE,
            observedFacts = listOf(
                "A continuous 18-meter straight black tire skid mark was measured on the asphalt prior to impact.",
                "Road surface was wet due to rainfall; friction coefficient (f) estimated at 0.50."
            ),
            forensicInterpretations = listOf(
                "Using speed formula v = √(254 × f × d), minimum pre-braking speed is calculated at not less than 68 km/h.",
                "Indicates vehicle exceeded the 50 km/h intersection speed limit on a wet road."
            ),
            limitations = "ABS braking systems may leave faint or intermittent marks; skid calculation represents MINIMUM pre-braking speed."
        ),
        EvidenceHotspot(
            id = "c3_h2",
            xRatio = 0.65f,
            yRatio = 0.60f,
            title = "Blue Metallic Paint Flakes on Garment",
            category = EvidenceCategory.VEHICLE,
            observedFacts = listOf(
                "Blue metallic paint flakes were recovered from the victim's jacket.",
                "Microscopic layer analysis revealed Clear coat, Blue base coat, and 3 Primer layers."
            ),
            forensicInterpretations = listOf(
                "Resulted from direct vehicle contact; confirms victim was struck by a blue metallic vehicle.",
                "Chemical FTIR spectrum matches paint samples taken from the suspect's pickup truck."
            ),
            limitations = "Factory mass production paint recipes are identical across same-model vehicles; physical fracture fit provides higher specificity."
        ),
        EvidenceHotspot(
            id = "c3_h3",
            xRatio = 0.22f,
            yRatio = 0.70f,
            title = "Headlight Glass & Polycarbonate Fragments",
            category = EvidenceCategory.GLASS,
            observedFacts = listOf(
                "Polycarbonate headlight fragments were scattered in a 45-degree fan pattern.",
                "Fragment code markings identify manufacturer and specific pickup truck model."
            ),
            forensicInterpretations = listOf(
                "Scatter pattern establishes the Point of Impact on the road surface.",
                "Reassembled fragments physically fit (Physical Match) into the suspect vehicle's broken headlight housing."
            ),
            limitations = "Fragile plastic fragments can be displaced by subsequent traffic flow."
        ),
        EvidenceHotspot(
            id = "c3_h4",
            xRatio = 0.50f,
            yRatio = 0.25f,
            title = "Flashing Traffic Signal",
            category = EvidenceCategory.DIGITAL,
            observedFacts = listOf(
                "Traffic camera logs confirm signals were in flashing amber/red caution mode due to system fault at 20:18.",
                "Drivers are required by law to reduce speed and yield at flashing caution signals."
            ),
            forensicInterpretations = listOf(
                "Failure to reduce speed at a flashing caution signal establishes reckless driving behavior.",
                "Environmental caution conditions required speed reduction."
            ),
            limitations = "Traffic cameras recorded from a distance without clear license plate resolution."
        ),
        EvidenceHotspot(
            id = "c3_h5",
            xRatio = 0.80f,
            yRatio = 0.80f,
            title = "Right Leg Bumper Fracture",
            category = EvidenceCategory.INJURY,
            observedFacts = listOf(
                "Victim suffered a right lower leg bumper fracture 42 cm above heel level.",
                "Horizontal contusion present on the right thigh."
            ),
            forensicInterpretations = listOf(
                "Fracture height of 42 cm matches the front bumper height of an elevated pickup truck.",
                "Confirms victim was struck while walking across the road from left to right."
            ),
            limitations = "Impact height varies slightly depending on footwear and posture."
        )
    )

    private val case3Witnesses = listOf(
        WitnessStatement(
            id = "c3_w1",
            witnessName = "Theera",
            role = "Blue Pickup Truck Driver / Suspect",
            statement = "I did not drive through that intersection tonight. I took the bypass road home. The scratch on my bumper and broken headlight happened yesterday when I backed into my gate post.",
            inconsistencies = listOf(
                "Blue paint layer chemistry matches paint flakes on the victim's garment.",
                "Headlight plastic fragments recovered at the scene physically fit (Physical Match) into his truck's broken headlight housing."
            ),
            timeRecorded = "22:00",
            interviewQuestions = listOf(
                InterviewQuestion("q3_1", "Where were you driving at 20:20 on the night of the accident?", "I took the bypass road home and was nowhere near Saimai intersection."),
                InterviewQuestion("q3_2", "How did your pickup truck's headlight and bumper get broken?", "I backed into my home driveway gate post yesterday morning.", "c3_h3"),
                InterviewQuestion("q3_3", "Why is your truck missing blue paint from the front right fender?", "It is an old work truck with paint chips from daily farm driving.")
            )
        ),
        WitnessStatement(
            id = "c3_w2",
            witnessName = "Local Vendor",
            role = "Eyewitness",
            statement = "I was at my stall and heard loud screeching tires. A blue pickup truck sped through, hit the pedestrian, and accelerated away into the dark without stopping to help.",
            inconsistencies = listOf(
                "Confirms hit-and-run conduct and failure to render aid."
            ),
            timeRecorded = "20:50",
            interviewQuestions = listOf(
                InterviewQuestion("q3_4", "What did you observe at the intersection at 20:20?", "Loud screeching tires, followed by a blue pickup truck striking a pedestrian and driving off."),
                InterviewQuestion("q3_5", "Were the traffic signals functioning at the time?", "The signals were flashing amber caution lights due to rain.")
            )
        )
    )

    private val case3Timeline = listOf(
        TimelineEvent("t1", "20:18", "Flashing Caution Signal", "Intersection traffic light faulted to flashing amber caution mode.", "Saimai Intersection"),
        TimelineEvent("t2", "20:20", "Hit-and-Run Incident", "Pickup truck skidded 18m, struck pedestrian, and fled without stopping.", "Intersection Center"),
        TimelineEvent("t3", "20:35", "Emergency Response", "Paramedics transported victim to hospital; forensics secured scene.", "Crime Scene"),
        TimelineEvent("t4", "22:00", "Suspect Vehicle Located", "Police traced blue pickup truck with broken headlight to Theera's residence.", "Theera's Residence")
    )

    private val case3ForensicConcepts = listOf(
        ForensicConcept(
            id = "fc_skidmark",
            title = "Skid Mark Speed Analysis",
            category = "Physics Forensics",
            summary = "Calculating minimum pre-braking vehicle speed from skid mark length and friction coefficients.",
            detailedExplanation = "Formula v = √(254 × f × d) uses road friction (f) and skid length (d) to establish minimum speed on wet/dry asphalt.",
            scientificLimitations = "ABS braking systems reduce skid marks; calculations represent MINIMUM pre-braking speed.",
            relatedCaseIds = listOf("case_03")
        ),
        ForensicConcept(
            id = "fc_paint_analysis",
            title = "Paint Transfer & Physical Fracture Fit",
            category = "Materials Forensics",
            summary = "Spectral paint layer comparison and physical puzzle-piece fragment matching.",
            detailedExplanation = "FTIR spectroscopy compares paint layer chemistry. When broken headlight plastic pieces physically fit into place (Physical Match), it provides conclusive individual identification.",
            scientificLimitations = "Paint flakes without physical fracture fit establish class connection rather than unique match.",
            relatedCaseIds = listOf("case_03")
        )
    )

    private val case3LegalSource = LegalSource(
        lawTitle = "Land Traffic Act B.E. 2522 & Criminal Code",
        sectionArticle = "Land Traffic Act Sections 43 & 78, Criminal Code Section 300",
        officialSourceTitle = "Office of the Council of State — Thai",
        officialSourceUrl = "https://www.krisdika.go.th",
        relevantAmendment = "Land Traffic Act (No. 13) B.E. 2565 (2022)",
        effectiveDate = "May 20, 1979 (Original) / 2022 (Amended)",
        verificationDate = "May 10, 2024",
        verificationStatus = "Verified against official primary source — Unofficial English translation",
        revisionHistory = "v1.2 — Aligned with amended hit-and-run provisions",
        contentVersion = "1.2",
        statutoryText = """
            [Land Traffic Act Section 43]
            "No driver shall drive a vehicle... (4) carelessly or recklessly in a manner that may cause danger to persons or property."
            
            [Land Traffic Act Section 78]
            "Any driver involved in an accident causing damage to persons or property shall stop the vehicle, render assistance, and report to police immediately...
            If the driver fails to stop and render assistance, it shall be presumed that such driver committed the offence."
            
            [Criminal Code Section 300]
            "Whoever by negligence causes serious bodily harm to another person shall be punished with imprisonment not exceeding three years or fine not exceeding sixty thousand baht."
        """.trimIndent(),
        educationalExplanation = """
            Traffic accident offences under Thai law involve two distinct legal issues:
            1. Negligent cause of injury (Criminal Code Section 300 & Land Traffic Act Section 43).
            2. Hit-and-run failure to render aid (Land Traffic Act Section 78).
            
            Section 78 establishes a statutory presumption: if a driver flees without stopping to render assistance, the law presumes the fleeing driver was at fault, shifting the evidentiary burden onto the driver.
        """.trimIndent(),
        caseApplication = "In this fictional case, driving at 68 km/h through a flashing intersection on wet asphalt constitutes negligence under Section 300. Fleeing without rendering aid constitutes a separate offence under Section 78.",
        legalQuestion = "Does speeding through a flashing signal and fleeing after striking a pedestrian constitute Reckless Driving Causing Severe Injury (Section 300) and Hit-and-Run (Section 78)?",
        fictionalFacts = "Theera drove at >68 km/h on wet asphalt, skidded 18m, struck a pedestrian causing leg fractures, and sped away without stopping.",
        conditionsAndElements = listOf(
            "Section 300 requires negligence causing severe bodily harm.",
            "Section 78 requires failure to stop, render aid, and identify oneself after an accident."
        ),
        missingOrUncertainFacts = "Victim's long-term medical recovery status.",
        limitsOfConclusion = "Educational analysis based on fictional facts. Statutory presumption under Section 78 can be rebutted in court."
    )

    private val case3Activities = listOf(
        LearningActivity(
            id = "act_c3_1",
            type = ActivityType.EVIDENCE_REASONING,
            title = "Activity 1: Skid Mark Speed Evaluation",
            instruction = "From the 18-meter skid mark on wet asphalt, what is the justified forensic conclusion?",
            learningObjectiveRef = "Evaluate vehicle speed calculations from physical skid mark length.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Vehicle was traveling at a normal safe speed under 20 km/h.",
                    isCorrect = false,
                    explanation = "Incorrect: An 18m skid mark on wet asphalt indicates a minimum speed of at least 68 km/h."
                ),
                ActivityOption(
                    id = 2,
                    text = "Skid length establishes a minimum pre-braking speed of ~68 km/h, exceeding the intersection limit.",
                    isCorrect = true,
                    explanation = "Correct! Physics calculation establishes minimum pre-braking speed exceeding intersection speed limits."
                ),
                ActivityOption(
                    id = 3,
                    text = "Skid marks on wet roads cannot be evaluated.",
                    isCorrect = false,
                    explanation = "Incorrect: Wet road friction coefficients are standard parameters in accident reconstruction."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to calculate speed.",
                    isCorrect = false,
                    explanation = "Incorrect: 18m length and wet road surface parameters provide sufficient data for minimum speed calculation."
                )
            )
        ),
        LearningActivity(
            id = "act_c3_2",
            type = ActivityType.INCONSISTENCY,
            title = "Activity 2: Physical Fracture Fit Matching",
            instruction = "Theera claimed: 'My headlight broke when I hit my gate post yesterday.' Which evidence conclusively refutes this claim?",
            learningObjectiveRef = "Distinguish conclusive physical fracture fit from general statements.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "Headlight plastic fragments from the crime scene physically fit (Physical Match) into Theera's broken headlight housing.",
                    isCorrect = true,
                    explanation = "Correct! Physical fracture fit matching proves the fragments originated from his specific headlight at the crash scene."
                ),
                ActivityOption(
                    id = 2,
                    text = "An eyewitness heard a loud noise.",
                    isCorrect = false,
                    explanation = "Incorrect: Hearing a noise confirms an impact occurred but does not identify the specific vehicle."
                ),
                ActivityOption(
                    id = 3,
                    text = "Rainy weather conditions.",
                    isCorrect = false,
                    explanation = "Incorrect: Weather conditions do not connect the vehicle to the headlight."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information to refute gate post claim.",
                    isCorrect = false,
                    explanation = "Incorrect: Physical fracture fit provides conclusive physical proof."
                )
            )
        ),
        LearningActivity(
            id = "act_c3_3",
            type = ActivityType.MULTIPLE_CHOICE_REASON,
            title = "Activity 3: Hit-and-Run Legal Presumption (Section 78)",
            instruction = "Under Land Traffic Act Section 78, what is the legal effect of a driver fleeing without rendering aid after an accident?",
            learningObjectiveRef = "Apply Section 78 statutory presumption to hit-and-run conduct.",
            options = listOf(
                ActivityOption(
                    id = 1,
                    text = "No legal effect if the victim survives.",
                    isCorrect = false,
                    explanation = "Incorrect: Failure to render aid is an immediate statutory offence."
                ),
                ActivityOption(
                    id = 2,
                    text = "Section 78 establishes a statutory presumption of fault against the fleeing driver.",
                    isCorrect = true,
                    explanation = "Correct! Section 78 presumes the fleeing driver was at fault, shifting the evidentiary burden."
                ),
                ActivityOption(
                    id = 3,
                    text = "A minor 200 baht traffic fine.",
                    isCorrect = false,
                    explanation = "Incorrect: Hit-and-run involving bodily injury carries imprisonment penalties."
                ),
                ActivityOption(
                    id = 4,
                    text = "Insufficient information regarding Section 78.",
                    isCorrect = false,
                    explanation = "Incorrect: Section 78 statutory presumption is explicitly defined in Thai law."
                )
            )
        )
    )

    private val case3Resolution = CaseResolution(
        reconstructionText = """
            1. At 20:20, Theera drove his blue pickup truck at >68 km/h through a flashing caution intersection during rain.
            2. Spotting a pedestrian, he braked hard, leaving an 18m skid mark before striking the victim, breaking his headlight and transferring blue paint flakes onto the victim's jacket.
            3. Theera fled without stopping. Police located his truck and physically reassembled (Physical Match) the crime scene headlight fragments into his truck's broken housing.
        """.trimIndent(),
        evidenceSupportedConclusions = "Physical fracture fit of headlight fragments and paint layer FTIR match prove the pickup truck was the collision vehicle. Skid length confirms speeding.",
        remainingUncertainties = "Whether the pedestrian maintained proper lookout prior to stepping onto the road.",
        legalAnalysisSummary = "Constitutes Negligent Driving Causing Severe Injury (Criminal Code Section 300) and Hit-and-Run (Land Traffic Act Section 78).",
        forensicTakeaways = listOf(
            "Physical Fracture Fit matching provides conclusive proof of origin.",
            "Skid mark physics calculates minimum pre-braking speed.",
            "Multilayer paint FTIR spectroscopy identifies vehicle coating chemistry."
        ),
        legalTakeaways = listOf(
            "Speeding through flashing caution signals constitutes criminal negligence under Section 300.",
            "Fleeing without rendering aid creates a statutory presumption of fault under Section 78."
        )
    )

    val case3 = CaseData(
        id = "case_03",
        caseNumber = 3,
        dossierId = "CASE-2024-003",
        title = "Traces on the Highway",
        subtitle = "The Intersection Hit-and-Run Mystery",
        synopsis = "A pedestrian is struck at a rainy highway intersection and the driver flees. Evidence includes an 18m skid mark, blue paint transfer, and broken headlight fragments.",
        difficulty = "Challenging",
        estimatedMinutes = 15,
        sceneType = SceneType.HIGHWAY_CROSSING,
        premise = "At 20:20, a pedestrian was struck at a flashing traffic signal intersection. The driver fled into the dark. Investigators recovered an 18-meter skid mark and headlight shards.",
        learningObjectives = listOf(
            "Calculate vehicle pre-braking speed from skid mark length.",
            "Understand Physical Fracture Fit matching of broken plastic fragments.",
            "Analyze offences under Land Traffic Act Section 78 and Criminal Code Section 300."
        ),
        introductionText = """
            Saimai Highway Intersection, 20:20.
            
            Rain fell continuously over the dark junction. Screeching tires echoed across the asphalt, followed by a violent impact. A pedestrian was thrown 10 meters while a vehicle accelerated away into the night.
            
            Paramedics rushed the victim to hospital in critical condition. Traffic police and forensic officers secured the scene, discovering an 18-meter tire skid mark, blue metallic paint flakes on the victim's clothing, and broken headlight fragments.
            
            Police later located a blue pickup truck owned by Theera. Theera claimed he hit a gate post at his home...
        """.trimIndent(),
        hotspots = case3Hotspots,
        witnessStatements = case3Witnesses,
        timeline = case3Timeline,
        forensicConcepts = case3ForensicConcepts,
        legalSource = case3LegalSource,
        activities = case3Activities,
        resolution = case3Resolution
    )

    val allAutopsyReports = listOf(
        AutopsyReport(
            id = "autopsy_c1",
            caseId = "case_01",
            victimName = "Kitti (Fictional Case 1)",
            age = 52,
            gender = "Male",
            examDate = "May 11, 2024",
            examinerTitle = "Department of Forensic Medicine (Educational Examination)",
            externalObservations = listOf(
                "3.5 cm linear laceration over posterior occipital scalp region with surrounding contusion.",
                "Abrasion on left shoulder consistent with secondary impact against soil/grass.",
                "Frayed cotton thread fibers adhering to left collar button area."
            ),
            internalFindings = listOf(
                "Localized subarachnoid hemorrhage corresponding to posterior scalp impact site.",
                "Cervical spine hyper-extension injury without cervical cord transaction.",
                "Lungs show mild pulmonary edema."
            ),
            toxicologySummary = "Qualitative screening positive for central nervous system sedative drug in gastric contents and blood.",
            limitationsAndUncertainties = "Autopsy findings establish blunt trauma and sedation prior to fall, but cannot specify whether the fall was initiated by a push or an accidental slip."
        ),
        AutopsyReport(
            id = "autopsy_c3",
            caseId = "case_03",
            victimName = "Pedestrian Victim (Fictional Case 3)",
            age = 38,
            gender = "Male",
            examDate = "May 11, 2024",
            examinerTitle = "Department of Forensic Medicine (Educational Examination)",
            externalObservations = listOf(
                "Transverse bumper fracture of right lower leg (tibia/fibula) centered 42 cm above heel.",
                "Horizontal contusion across lateral right thigh.",
                "Multiple superficial gravel abrasions across forehead and left arm."
            ),
            internalFindings = listOf(
                "Comminuted fracture of right tibia shaft matching front bumper profile.",
                "No penetrating visceral trauma."
            ),
            toxicologySummary = "Screening negative for ethanol and controlled drugs.",
            limitationsAndUncertainties = "Fracture height matches elevated pickup truck bumper height; precise vehicle velocity requires skid mark physics correlation."
        )
    )

    val allAnatomyLessons = listOf(
        AnatomyLesson(
            id = "anat_head_neck",
            title = "Occipital Scalp & Cranial Trauma",
            regionCategory = "Head & Neck",
            summary = "Anatomical structure of scalp layers, cranial vault, and subarachnoid space during blunt force impact.",
            structuralDescription = "The scalp consists of 5 layers (SCALP: Skin, Connective tissue, Aponeurosis, Loose connective tissue, Pericranium). Impact against hard surfaces causes linear lacerations and underlying subarachnoid hemorrhage.",
            relevantCaseContext = "Relevant to Case 1 (The Closed-Room Shadow) regarding the victim's scalp laceration against the metal railing.",
            anatomicalTerms = listOf("Occipital Bone", "Subarachnoid Space", "Galea Aponeurotica", "Contusion")
        ),
        AnatomyLesson(
            id = "anat_lower_limb",
            title = "Lower Extremity Bumper Fracture Mechanics",
            regionCategory = "Musculoskeletal & Trauma",
            summary = "Biomechanics of pedestrian bumper impacts on tibia and fibula bones.",
            structuralDescription = "When a standing pedestrian is struck laterally by a vehicle bumper, the direction of force causes a characteristic wedge-shaped fracture (Messerer's fracture) on the tibia shaft.",
            relevantCaseContext = "Relevant to Case 3 (Traces on the Highway) where the fracture height of 42 cm matched the suspect vehicle's bumper height.",
            anatomicalTerms = listOf("Tibia", "Fibula", "Patella", "Messerer's Fracture")
        )
    )

    val allRealCases = listOf(
        RealCaseStudy(
            id = "real_01",
            dossierId = "REAL-2021-089",
            title = "The Emerald Theft & Evidence Trail",
            courtStage = "Supreme Court Judgment Confirmed (B.E. 2564 / 2021)",
            summary = "Documented real-case study analyzing high-value gemstone theft involving digital access logs, latent palm prints, and financial transaction tracing under Thai Criminal Code Section 335.",
            documentedFacts = listOf(
                "Gemstone vault opened at 02:15 at night after security hours.",
                "Latent palm print recovered from vault interior door metal plate.",
                "Financial transfers traced between suspect and gemstone broker."
            ),
            forensicEvidenceUsed = listOf("Latent Palm Print Friction Ridge Comparison", "Digital Vault Access Audit Server Logs"),
            thaiLawApplied = "Criminal Code Section 335 (1) & (8) — Aggravated Theft at Night by Bypassing Barriers",
            judgmentOutcome = "Supreme Court affirmed conviction based on corroborating physical palm print evidence and digital access trails.",
            officialSourceCitation = "Supreme Court Judgment No. 3821/2564 — Office of the Judiciary Thailand"
        ),
        RealCaseStudy(
            id = "real_02",
            dossierId = "REAL-2022-014",
            title = "The Expressway Collision Audit",
            courtStage = "Appeal Court Final Decision (B.E. 2565 / 2022)",
            summary = "Documented real-case study evaluating high-speed vehicle impact telemetry, paint transfer layer chemistry, and driver failure to render aid under Land Traffic Act Section 78.",
            documentedFacts = listOf(
                "Vehicle collided with rear of sedan at 138 km/h on Sirat Expressway.",
                "Driver accelerated away without stopping to render assistance or contact emergency 1669.",
                "Paint flakes and bumper plastic fracture pieces recovered at collision site."
            ),
            forensicEvidenceUsed = listOf("Vehicle ECU Telemetry Speed Record", "Paint Layer FTIR Spectroscopy Comparison", "Physical Fracture Fit"),
            thaiLawApplied = "Land Traffic Act B.E. 2522 Section 78 & Criminal Code Section 300 (Negligent driving causing severe injury)",
            judgmentOutcome = "Appeal Court applied Section 78 statutory presumption of fault; convicted defendant of hit-and-run and reckless driving.",
            officialSourceCitation = "Appeal Court Region 1 Judgment No. 1420/2565 — Office of the Council of State records"
        )
    )

    val allCases = listOf(case1, case2, case3)

    fun getCaseById(id: String): CaseData? {
        return allCases.find { it.id == id }
    }

    val allForensicConcepts = listOf(
        ForensicConcept(
            id = "lib_f1",
            title = "Locard's Exchange Principle",
            category = "Basic Forensic Science",
            summary = "Every contact leaves a trace. Material exchange occurs whenever two objects collide.",
            detailedExplanation = "Formulated by Dr. Edmond Locard, this principle dictates that perpetrators leave trace physical evidence at crime scenes and carry scene material away.",
            scientificLimitations = "Trace evidence can be lost or contaminated if the crime scene is not secured promptly.",
            relatedCaseIds = listOf("case_01", "case_02", "case_03")
        ),
        ForensicConcept(
            id = "lib_f2",
            title = "Latent Fingerprint Analysis",
            category = "Personal Identification",
            summary = "Detection and comparison of invisible friction ridge skin impressions.",
            detailedExplanation = "Latent prints are developed using forensic powders or chemical reagents, then evaluated by minutiae ridge characteristics.",
            scientificLimitations = "Fingerprints establish contact but cannot specify the exact timestamp or intent of touching.",
            relatedCaseIds = listOf("case_01")
        ),
        ForensicConcept(
            id = "lib_f3",
            title = "Shoeprint & Tread Pattern Comparison",
            category = "Physical Forensics",
            summary = "Evaluating tread design, size, and wear marks to match footwear.",
            detailedExplanation = "Class characteristics identify shoe brand and size; individual wear marks can uniquely identify a specific shoe.",
            scientificLimitations = "Incomplete impressions provide class matching rather than unique identification.",
            relatedCaseIds = listOf("case_02")
        ),
        ForensicConcept(
            id = "lib_f4",
            title = "Skid Mark Speed Calculation",
            category = "Traffic Accident Reconstruction",
            summary = "Calculating minimum pre-braking speed from tire skid length and road friction.",
            detailedExplanation = "Uses physics formula v = √(254 × f × d) to establish minimum vehicle speed prior to wheel lockup.",
            scientificLimitations = "ABS braking systems lessen visible skid marks, making calculations a MINIMUM estimate.",
            relatedCaseIds = listOf("case_03")
        ),
        ForensicConcept(
            id = "lib_f5",
            title = "Physical & Fracture Fit Analysis",
            category = "Physical Trace Evidence",
            summary = "Matching broken object pieces together like puzzle fragments.",
            detailedExplanation = "When plastic, glass, or metal objects break, random fracture patterns are unique. Reassembling matching edges provides conclusive origin proof.",
            scientificLimitations = "Requires intact fracture edges that have not been pulverized.",
            relatedCaseIds = listOf("case_03")
        )
    )

    val allLegalTopics = listOf(
        LegalSource(
            lawTitle = "Criminal Code of Thailand — Offences Against Life",
            sectionArticle = "Criminal Code Sections 288 & 290",
            officialSourceTitle = "Office of the Council of State — Thai",
            officialSourceUrl = "https://www.krisdika.go.th",
            relevantAmendment = "Act Amending Criminal Code (No. 26) B.E. 2560 (2017)",
            effectiveDate = "November 13, 1956 / February 10, 2017",
            verificationDate = "May 10, 2024",
            verificationStatus = "Verified against official primary source — Unofficial English translation",
            revisionHistory = "v1.2 — Aligned with official Gazette text",
            contentVersion = "1.2",
            statutoryText = "Section 288: Intentional Murder. Penalty: Death, life imprisonment, or 15–20 years. | Section 290: Inflicting injury causing death without intent to kill. Penalty: 3–15 years.",
            educationalExplanation = "Distinguishes specific intent to kill (Section 288) from intent to assault resulting in death (Section 290).",
            caseApplication = "Applied in Case 1 (The Closed-Room Shadow).",
            legalQuestion = "What distinguishes Section 288 from Section 290?",
            fictionalFacts = "Pushing a victim during a balcony argument.",
            conditionsAndElements = listOf("Section 288 requires intent to kill.", "Section 290 requires intent to injure causing death."),
            missingOrUncertainFacts = "Toxicology quantification.",
            limitsOfConclusion = "Subject to judicial discretion."
        ),
        LegalSource(
            lawTitle = "Criminal Code of Thailand — Offences Against Property",
            sectionArticle = "Criminal Code Sections 334, 335, & 357",
            officialSourceTitle = "Office of the Council of State — Thai",
            officialSourceUrl = "https://www.krisdika.go.th",
            relevantAmendment = "Act Amending Criminal Code (No. 26) B.E. 2560 (2017)",
            effectiveDate = "November 13, 1956 / 2017",
            verificationDate = "May 10, 2024",
            verificationStatus = "Verified against official primary source — Unofficial English translation",
            revisionHistory = "v1.2 — Aligned with official Gazette text",
            contentVersion = "1.2",
            statutoryText = "Section 334: Theft. | Section 335: Aggravated theft at night / breaking barriers. | Section 357: Receiving stolen property.",
            educationalExplanation = "Aggravating circumstances (nighttime, bypassing security barriers) elevate penalties for property theft.",
            caseApplication = "Applied in Case 2 (The Missing Masterpiece).",
            legalQuestion = "When does theft become aggravated theft under Section 335?",
            fictionalFacts = "Stealing artwork at night after cutting CCTV wires.",
            conditionsAndElements = listOf("Section 335(1) Nighttime.", "Section 335(8) Bypassing barriers."),
            missingOrUncertainFacts = "Recovery of stolen canvas.",
            limitsOfConclusion = "Requires search warrant execution."
        ),
        LegalSource(
            lawTitle = "Land Traffic Act B.E. 2522 & Criminal Code",
            sectionArticle = "Land Traffic Act Sections 43 & 78, Criminal Code Section 300",
            officialSourceTitle = "Office of the Council of State — Thai",
            officialSourceUrl = "https://www.krisdika.go.th",
            relevantAmendment = "Land Traffic Act (No. 13) B.E. 2565 (2022)",
            effectiveDate = "May 20, 1979 / 2022",
            verificationDate = "May 10, 2024",
            verificationStatus = "Verified against official primary source — Unofficial English translation",
            revisionHistory = "v1.2 — Aligned with amended hit-and-run provisions",
            contentVersion = "1.2",
            statutoryText = "Land Traffic Act Section 43: Reckless driving. | Section 78: Hit-and-run failure to assist presumption of fault. | Criminal Code Section 300: Negligent driving causing severe injury.",
            educationalExplanation = "Failure to stop and render assistance creates a statutory presumption of fault against the driver.",
            caseApplication = "Applied in Case 3 (Traces on the Highway).",
            legalQuestion = "What is the legal effect of fleeing an accident under Section 78?",
            fictionalFacts = "Driving at 68 km/h on wet asphalt, hitting a pedestrian, and fleeing.",
            conditionsAndElements = listOf("Section 300 Negligence causing severe bodily harm.", "Section 78 Hit and run presumption."),
            missingOrUncertainFacts = "Pedestrian's lookout posture.",
            limitsOfConclusion = "Statutory presumption can be rebutted in court."
        )
    )
}
