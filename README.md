# URL Shortener

The repository is split into `frontend/` (React + Vite) and `backend/` (Spring Boot + Maven).

## Run locally

1. Copy `backend/.env.example` to `backend/.env` and set `DB_USERNAME` and `DB_PASSWORD`. Set `DB_URL` if your PostgreSQL address differs from the local default.
2. Start PostgreSQL and Redis.
3. Run the backend from `backend/` with `./mvnw spring-boot:run`.
4. In another terminal, run the frontend from `frontend/` with `npm install && npm run dev`.

The Vite development server proxies `/api` requests to `http://localhost:8080`.

## Deploy on Render

Deploy the two folders as separate Render services:

**Backend web service**
- Choose **Docker** and set the Root Directory to `backend`.
- Add `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `REDIS_URL` as environment variables using your PostgreSQL and Redis provider's connection details. `DB_URL` must use JDBC format, for example `jdbc:postgresql://<host>:5432/<database>`.
- Set `FRONTEND_URL` to the deployed frontend's full origin, such as `https://your-site.onrender.com`.

**Frontend static site**
- Set the Root Directory to `frontend`, Build Command to `npm install && npm run build`, and Publish Directory to `dist`.
- Set `VITE_BACKEND_URL` to the deployed backend's full origin, such as `https://your-api.onrender.com`.

Keep database credentials and connection URLs in Render's environment settings (or the ignored local `backend/.env`), never in committed source files.
# url-shortener
