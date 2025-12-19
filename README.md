# Movie Database App (TMDB)

A simple Android app to browse movie genres, discover movies by genre, and view movie details including trailer and user reviews.

## 1. Features
1. Browse movie genres.
2. Discover movies by selected genre (infinite scroll / pagination).
3. Movie detail:
   - Poster image.
   - Overview, release date, rating.
   - Watch YouTube trailer (opens YouTube / browser).
   - Read user reviews (includes review date).

## 2. Tech Stack
- Kotlin
- Jetpack Compose (UI)
- Material 3
- MVVM (ViewModel + UI state)
- Kotlin Coroutines + Flow
- Retrofit (Networking)
- OkHttp (Interceptor for auth header)
- Moshi (JSON parsing)
- Coil (Image loading for posters)

## 3. API Source
This app uses **The Movie Database (TMDB) API v3** for movie data:
- Genres: `GET /genre/movie/list`
- Discover movies: `GET /discover/movie`
- Movie details: `GET /movie/{movie_id}`
- Reviews: `GET /movie/{movie_id}/reviews`
- Videos/Trailers: `GET /movie/{movie_id}/videos`

Posters are loaded using TMDB image base URL:
- `https://image.tmdb.org/t/p/{size}{poster_path}`  
Example sizes: `w342`, `w500`.

Trailers are opened via YouTube:
- `https://www.youtube.com/watch?v={key}`

## 4. Project Structure (High Level)
- `data/remote` → Retrofit API interface + DTOs
- `data/repository` → Repository implementations (calls TMDB)
- `domain/model` → App models (Movie, Genre, Review, Video, MoviePage)
- `ui/screen` → Compose screens
- `ui/viewmodel` → ViewModels + UI state
- `ui/utils` → Helpers (image url builder, date formatting)

## 5. Setup (TMDB Token)
This project reads the TMDB token from `local.properties` so it’s not committed to Git.

1) Create/edit `local.properties` in the project root:
TMDB_ACCESS_TOKEN=PASTE_YOUR_TMDB_V4_READ_ACCESS_TOKEN_HERE

2) Sync Gradle, then run the app.

Notes:
- Use **TMDB v4 Read Access Token** (Bearer token), not the v3 API key.
- If the token is missing/empty, API calls will return **401 Unauthorized**.

## 6. Requirements
- Android Studio
- Android SDK / Emulator or physical device
- Internet connection

## 7. Credits
- Movie data and images provided by **TMDB**.
