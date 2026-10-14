# Verified hardening — 11 October 2026

Updated APK: outputs/Casebook-debug.apk. Built through Android Studio, installed successfully on Pixel 7 API 35, and launched successfully.

Verified on Android:
- HardeningVerificationTest#seed: OK (1 test).
- Backend stopped; #offlineDoesNotOverwriteSavedFeedback: OK (1 test). Failed network request preserves the hypothesis and does not overwrite saved live feedback.
- App process force-stopped; #verifyAfterRestart: OK (1 test). Notes, discovered evidence, complete structured AI feedback and saved stage survive restart. Case 1 resumes Notebook with Cases selected. Switching to Case 2 does not show Case 1 feedback; switching back restores it. Previous user data restored after testing.
- Backend restored; LiveAiVerificationTest: OK (1 test), 4.758 seconds. Real Gemini response through Android; demo fallback would fail the test.
- Latest APK installed and launched after the final layout-only change. Cases screen runtime hierarchy confirms difficulty and duration are on a readable line rather than narrow vertical wrapping. Screenshots: home-verified.png and cases-verified.png.

Fixes: case/tab consistency, duplicate request guard, request-generation protection against stale loading/results, per-case feedback isolation, full feedback JSON storage, accurate legacy display, cancellation propagation, connection cleanup, response validation, rotation-saveable notebook draft, and case-card metadata layout.

The provider returned transient 503 errors during initial live checks; subsequent backend and Android checks succeeded. Free-tier service availability is not guaranteed. Keep backend/START-BACKEND.cmd running for emulator demos; no billing or access-expiry changes were made.

These results cover the listed hardening fixes. Full visual redesign, chat UI, legal-source certification, every stage in all cases, and enlarged-font/rotation interaction have not been comprehensively runtime-tested in this revision. No broader certification is claimed.
