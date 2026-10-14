# Instructor requirement check — 14 October 2026

The current Android application meets the four implementation requirements in the tested emulator paths.

| Requirement | Evidence | Result |
|---|---|---|
| Native Android and multiple screens | Debug APK builds and installs; Home, Cases, Activities, Ask AI and Library opened on Pixel 7 emulator | PASS |
| Save and retrieve data | HardeningVerificationTest seed, process force-stop, verifyAfterRestart; original user records restored afterward | PASS |
| External API using async/coroutines | Coroutine HTTP services through local Node.js backend to Gemini; actual Android network requests passed | PASS |
| AI integrated functions | ChatVerificationTest live question plus follow-up and isolated saved history; LiveAiVerificationTest live deduction review | PASS |

Build tasks assembleDebug, assembleDebugAndroidTest and testDebugUnitTest succeeded. Instrumentation: seed 1 test, restart verification 1 test, live chat and deduction 2 tests, all passed. No app fatal exception appeared in the recent inspected log.

The check does not certify every screen, device, legal provision or scientific explanation. Legal and forensic subject-matter review remains necessary. Source links may depend on external site certificates. These are content/deployment limits, not missing implementations of the four required features.

The presentation contains project title, description, issues and motivation, actual screen designs, and a live-demo sequence. The student must still present in class and submit after presenting, as the instructor requests.

Before the demo: start the emulator, run backend/START-BACKEND.cmd, keep internet available. Current backend access expires 18 October 2026, Bangkok time. If the presentation takes place later, renew configuration before demonstrating live AI. No expiration was changed by this check.
