# Hospital Management System (HMS) + AI Analysis

This workspace contains a full-stack Hospital Management System with a separate AI analysis service for medical reports and chest X-ray scans.

## Contents
- `hospital-management-backend`: Spring Boot REST API (MySQL)
- `hospital-management-frontend`: React + Vite UI
- `hospital-management-ai`: FastAPI service for report/X-ray analysis

## High-Level Flow
1. Frontend uploads a report or X-ray via `/analysis`.
2. Spring Boot forwards the file to the FastAPI service.
3. FastAPI runs:
   - Hugging Face text summarization (for reports/notes)
   - Hugging Face zero-shot classification (specialist mapping)
   - Chest X-ray model (DenseNet via `torchxrayvision` when `reportType=xray`)
4. Summary + recommended specialists are returned to the UI.

## Prerequisites
- Node.js 18+
- Java 17+
- Maven (or use `./mvnw`)
- MySQL
- Python 3.11+ (3.11 or 3.13 works with the pinned torch versions)
- GPU optional (CUDA supported for X-ray inference)

## Backend Setup (Spring Boot)
1. Configure MySQL credentials in:
   - `hospital-management-backend/src/main/resources/application.properties`

   Example (already present):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=hospital_user
   spring.datasource.password=hospital123
   server.port=8080
   ai.service.url=http://localhost:8001
   ```

2. Run the backend:
   ```bash
   cd hospital-management-backend
   ./mvnw spring-boot:run
   ```

## Frontend Setup (React)
1. Install dependencies:
   ```bash
   cd hospital-management-frontend
   npm install
   ```

2. Run the frontend:
   ```bash
   npm run dev
   ```

Frontend expects:
- Backend: `http://localhost:8080`
- AI Service: `http://localhost:8001` (via backend proxy)

## AI Service Setup (FastAPI)
The AI service uses Hugging Face for text tasks and a local chest X-ray model for scans.

### 1) Create `.env`
```bash
cd hospital-management-ai
cp .env.example .env
```
Edit `.env` and set:
```
HF_API_TOKEN=hf_your_token_here
```

Optional overrides:
```
HF_SUMMARY_MODEL=sshleifer/distilbart-cnn-12-6
HF_ZERO_SHOT_MODEL=facebook/bart-large-mnli
HF_CAPTION_MODEL=Salesforce/blip-image-captioning-large
XRAY_MODEL_WEIGHTS=densenet121-res224-all
```

### 2) Install dependencies
GPU install (CUDA 12.1 example):
```bash
pip install -r requirements.txt --extra-index-url https://download.pytorch.org/whl/cu121
```

CPU-only install:
```bash
pip install -r requirements.txt --extra-index-url https://download.pytorch.org/whl/cpu
```

### 3) Run FastAPI
```bash
uvicorn main:app --reload --port 8001
```

The first X-ray request will download model weights to:
```
~/.torchxrayvision/models_data/
```

If weights download is corrupted, remove the file and retry:
```bash
rm ~/.torchxrayvision/models_data/nih-pc-chex-mimic_ch-google-openi-kaggle-densenet121-d121-tw-lr001-rot45-tr15-sc15-seed0-best.pt
```

## AI Analysis Usage
- UI route: `/analysis`
- Upload a file and select **Report type = X-ray** for chest X-ray analysis.
- Reports can be PDF/TXT; X-rays should be PNG/JPG.

## Specialist Recommendations
The system uses the following specialist list:
- Cardiologists
- Neurologists
- Dermatologists
- Oncologists
- Pediatricians
- Gastroenterologists
- Orthopedic Surgeons
- Psychiatrists

Recommendations are derived from:
- Hugging Face zero-shot classification (text)
- Chest X-ray findings (when `reportType=xray`)

## API Endpoints
Backend (Spring Boot):
- `POST /api/v1/analysis` (multipart) -> forwards to FastAPI

FastAPI:
- `POST /analyze` (multipart)
- `GET /health`

## Troubleshooting
- **500 from FastAPI**: check terminal logs for Hugging Face auth or model download errors.
- **Hugging Face 401/403**: invalid `HF_API_TOKEN`.
- **X-ray model load failure**: delete the weights file and retry, or ensure torch/torchvision match your Python version.
- **CUDA issues**: install CPU wheels and run on CPU.

## Notes
- This is a research/prototype system. AI outputs are not medical diagnoses.
- Always review results with a qualified clinician.
