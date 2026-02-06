#!/bin/bash
# Build script for DuoGlass Launcher
# This script builds the Android application

set -e  # Exit on error

echo "=========================================="
echo "  DuoGlass Launcher - Build Script"
echo "=========================================="
echo ""

# Check if we're in the right directory
if [ ! -f "settings.gradle.kts" ]; then
    echo "❌ Error: This script must be run from the project root directory"
    exit 1
fi

# Check for Java
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed"
    echo "   Please install JDK 11 or later"
    exit 1
fi

echo "✓ Java found: $(java -version 2>&1 | head -n 1)"

# Create gradlew if it doesn't exist
if [ ! -f "gradlew" ]; then
    echo ""
    echo "Creating Gradle wrapper..."
    if command -v gradle &> /dev/null; then
        gradle wrapper --gradle-version 8.0
    else
        echo "❌ Error: Gradle is not installed and gradlew doesn't exist"
        echo "   Please install Gradle or Android Studio"
        exit 1
    fi
fi

# Make gradlew executable
chmod +x gradlew

echo ""
echo "=========================================="
echo "  Building Application..."
echo "=========================================="
echo ""

# Clean build
echo "Cleaning previous build..."
./gradlew clean

# Build debug APK
echo ""
echo "Building debug APK..."
./gradlew assembleDebug

echo ""
echo "=========================================="
echo "  ✓ Build Successful!"
echo "=========================================="
echo ""
echo "APK Location:"
echo "  app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo "To install on a connected device:"
echo "  adb install app/build/outputs/apk/debug/app-debug.apk"
echo ""
echo "To build release APK:"
echo "  ./gradlew assembleRelease"
echo ""
