# Oxygen Monitor / Air Quality Monitor

A Spring Boot + HTML/CSS/JavaScript application that uses OpenWeather Geocoding and Air Pollution APIs to retrieve the current ozone (O₃) concentration for a city.

> Important: OpenWeather's `o3` field is ozone concentration, measured in μg/m³. It is **not** the percentage of atmospheric oxygen (O₂).

## Requirements

- Java 17 or later
- Maven 3.8+ (or use `mvnw.cmd` on Windows)
- An OpenWeather API key with access to the required APIs

## Configure the API key

Do not put the real API key in `application.properties` or GitHub.

### Windows CMD
```text
set OPENWEATHER_API_KEY=YOUR_REAL_KEY
```

### PowerShell
```powershell
$env:OPENWEATHER_API_KEY="YOUR_REAL_KEY"
```

### Git Bash
```bash
export OPENWEATHER_API_KEY=YOUR_REAL_KEY
```

## Run

From the project folder:

```bash
mvn spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

## API

```text
GET /api/oxygen/{city}
```

Example:

```text
http://localhost:8080/api/oxygen/Pune
```

## What was fixed

- Removed the duplicate nested Spring Boot project.
- Removed the unused JPA/MySQL configuration that prevented startup without a database.
- Removed the unnecessary manually bundled JSON JAR.
- Added a proper `RestTemplate` bean.
- Added safe URL encoding for city names.
- Fixed the frontend/backend response mismatch.
- Added proper API error handling.
- Moved the API key to an environment variable.
- Added a Spring Boot context test.
- Updated the UI to correctly display ozone rather than calling it oxygen percentage.
