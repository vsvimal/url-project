# URL Shortener

## Configure PostgreSQL

Copy `.env.example` to `.env` and set `DB_USERNAME` and `DB_PASSWORD` to your PostgreSQL credentials. Set `DB_URL` too if your database is not at the default local address. `.env` is ignored by Git; keep real credentials there and never commit it.

Run the application from the project root with:

```sh
./mvnw spring-boot:run
```
# url-shortener
