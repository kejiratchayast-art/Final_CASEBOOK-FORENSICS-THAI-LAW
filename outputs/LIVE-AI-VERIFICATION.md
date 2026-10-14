# Live AI verification — 11 October 2026

Android emulator: Pixel 7, Android 15, emulator-5554.

- Gemini backend returned status LIVE and isDemoResponse=false.
- Android instrumented test called DeductionCoachService with one discovered observation from Case 1 and received real AI feedback in all four fields. Demo fallback causes this test to fail.
- The same test saved a note and live feedback through UserPreferencesRepository and verified retrieval through a newly created repository. This verifies persisted storage reload, not an end-to-end UI process-restart test.
- connectedDebugAndroidTest: BUILD SUCCESSFUL, 1 test, 0 failures, 0 errors.
- Fixed malformed Android Content-Type header; set application/json; charset=utf-8.
- Model: gemini-3.8-flash. The previous model rejected this new account.
- Updated APK installed and launched successfully after testing.

Start backend/START-BACKEND.cmd before using the emulator. Keep its window open during the demonstration. The emulator connects to the computer through 10.0.2.2:3000. A physical phone requires a different network setup.

Credentials remain in backend/.env and are excluded from Git. No billing was enabled by this work. Free-tier quota limits can still interrupt AI requests. Backend access ends on 18 October 2026 at 23:59:59 Bangkok time; this does not revoke the Google key.

This verification covers the AI integration and storage reload. It does not certify real-case citations, legal penalties, or every screen's content.
