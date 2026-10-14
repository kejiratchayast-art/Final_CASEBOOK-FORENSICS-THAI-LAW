# Casebook Backend Server

Small Express backend proxy for the Deduction Coach AI feature in **Casebook: Forensics & Thai Law**.

## Setup Instructions

1. Navigate to the `backend/` directory:
   ```bash
   cd backend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Create a `.env` file or set environment variables:
   ```bash
   cp .env.example .env
   ```
4. Start the server:
   ```bash
   npm start
   ```

## Endpoints
- `POST /api/deduction-coach`
  - Body: `{ "caseId": "case_01", "hypothesis": "...", "discoveredEvidenceIds": [...] }`
  - Returns structured AI reasoning feedback (Supporting Evidence, Assumptions to Reconsider, Remaining Uncertainties, Guiding Question).
