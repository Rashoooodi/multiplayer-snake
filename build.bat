@echo off
REM Build script for DuoGlass Launcher (Windows)
REM This script builds the Android application

setlocal

echo ==========================================
echo   DuoGlass Launcher - Build Script
echo ==========================================
echo.

REM Check if we're in the right directory
if not exist "settings.gradle.kts" (
    echo ERROR: This script must be run from the project root directory
    exit /b 1
)

REM Check for Java
where java >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed
    echo    Please install JDK 11 or later
    exit /b 1
)

echo Java found: 
java -version 2>&1 | findstr /C:"version"

REM Create gradlew if it doesn't exist
if not exist "gradlew.bat" (
    echo.
    echo Creating Gradle wrapper...
    where gradle >nul 2>&1
    if %ERRORLEVEL% EQU 0 (
        gradle wrapper --gradle-version 8.0
    ) else (
        echo ERROR: Gradle is not installed and gradlew.bat doesn't exist
        echo    Please install Gradle or Android Studio
        exit /b 1
    )
)

echo.
echo ==========================================
echo   Building Application...
echo ==========================================
echo.

REM Clean build
echo Cleaning previous build...
call gradlew.bat clean

REM Build debug APK
echo.
echo Building debug APK...
call gradlew.bat assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==========================================
    echo   Build Successful!
    echo ==========================================
    echo.
    echo APK Location:
    echo   app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo To install on a connected device:
    echo   adb install app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo To build release APK:
    echo   gradlew.bat assembleRelease
    echo.
) else (
    echo.
    echo ==========================================
    echo   Build Failed!
    echo ==========================================
    echo.
    echo Check the error messages above for details.
    exit /b 1
)

endlocal
