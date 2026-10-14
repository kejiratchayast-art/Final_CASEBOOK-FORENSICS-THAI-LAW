# Casebook — implementation verification

## Instructor requirements
- Multiple screens: implemented; navigation to cases, activities, examination, notebook, library and settings retained.
- Data persistence: instrumentation verified saved notes, evidence, case progress and full AI feedback across process termination and restart; switching cases isolates records.
- External connectivity: real Android HTTP requests through coroutine services to the local backend passed.
- AI functionality: real Gemini chat and deduction feedback passed; demo text is not counted as live AI.

## Repairs and verification
- Activity navigation labels and dark-screen controls have explicit readable colors.
- Ask AI has a fixed composer, readable message/error states, saved history and contextual sources.
- Failed requests retain drafts; retry succeeds without duplicating failed user messages.
- Existing forensic lessons ground Forensics answers; hidden resolutions are excluded.
- Changing cases cancels stale deduction work. Offline failure does not overwrite saved live feedback.
- Official-source buttons use full-width readable layouts. Unverified law remains marked pending.
- Debug build and Android instrumentation passed. Emulator manual checks covered activity navigation, live chat, lesson source opening, offline failure and retry.

## Running the demo
Keep the computer and backend running. After starting the emulator, launch backend/START-BACKEND.cmd. It forwards port 3000 to connected Android devices. Then open the installed app and use Ask AI. No user API key is needed in the app.

## Remaining limits
This verifies the tested paths, not every possible defect. Legal wording, penalties, real-case provenance and external website certificates are not comprehensively verified. Backend access retains its existing expiration (18 October 2026, Bangkok time). Free provider quota and availability can limit live answers. The entire visual redesign has not been certified complete.

## Presentation
Include project title, description, motivation, screen designs and a live demo. Show: open a case → collect evidence → Ask AI → save a note → restart and show saved data. Prepare screenshots as backup. Submission remains the student's responsibility.
