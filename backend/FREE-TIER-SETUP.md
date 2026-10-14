# Gemini free-tier configuration
Use a Google AI Studio project on Free Tier with billing disabled. Do not link a billing account.
Store GEMINI_API_KEY in backend/.env, never in Android source or chat.
Run START-BACKEND.cmd after configuring the key. Stop the old project server first if port 3000 is occupied.
The Android client uses the same endpoint, so this backend migration does not require an APK change.
AI_ACCESS_EXPIRES_AT blocks this backend after the chosen deadline; it does NOT revoke the Google key. Revoke the key in AI Studio when finished.
Free quota depends on the account and model. Quota failure returns an error, never a paid fallback.
Free-tier inputs may be used to improve Google products. Submit fictional case material only, without personal data.
Live backend verified on 2026-10-11 using gemini-3.8-flash: status LIVE, isDemoResponse false. Android integration test results are recorded in app/build/reports/androidTests/connected/debug. Backend access expires 2026-10-18 23:59:59 Asia/Bangkok.
