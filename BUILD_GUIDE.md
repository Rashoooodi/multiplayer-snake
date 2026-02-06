# Building DuoGlass Launcher

## Prerequisites

Before building the DuoGlass Launcher application, ensure you have:

1. **Android Studio** (Recommended)
   - Download from: https://developer.android.com/studio
   - Version: Arctic Fox or later
   - Includes: Android SDK, Gradle, and all necessary build tools

2. **OR Command Line Tools**
   - JDK 11 or later
   - Android SDK (API 26-34)
   - Gradle 8.0+

3. **Internet Connection**
   - Required to download dependencies from:
     - Google Maven Repository
     - Maven Central  
     - Gradle Plugin Portal

## Build Instructions

### Method 1: Android Studio (Recommended)

1. **Open the Project**
   ```
   File → Open → Navigate to project directory → OK
   ```

2. **Wait for Gradle Sync**
   - Android Studio will automatically sync Gradle files
   - This downloads all dependencies (first time may take 5-10 minutes)
   - Watch the progress bar at the bottom of the IDE

3. **Build the Application**
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```
   
   Or use the keyboard shortcut:
   - Mac: `⌘ + B`
   - Windows/Linux: `Ctrl + B`

4. **Locate the APK**
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```
   
   Android Studio will show a notification with a link to the file location.

### Method 2: Command Line

1. **Navigate to Project Directory**
   ```bash
   cd /path/to/multiplayer-snake
   ```

2. **Make Gradle Wrapper Executable** (Linux/Mac only)
   ```bash
   chmod +x gradlew
   ```

3. **Build Debug APK**
   ```bash
   # Linux/Mac
   ./gradlew assembleDebug
   
   # Windows
   gradlew.bat assembleDebug
   ```

4. **Build Release APK** (for distribution)
   ```bash
   # Linux/Mac
   ./gradlew assembleRelease
   
   # Windows
   gradlew.bat assembleRelease
   ```

5. **Locate the APK**
   - Debug: `app/build/outputs/apk/debug/app-debug.apk`
   - Release: `app/build/outputs/apk/release/app-release.apk`

## Build Variants

### Debug Build
- Includes debugging symbols
- Larger file size
- Not optimized
- Suitable for development and testing

```bash
./gradlew assembleDebug
```

### Release Build
- Optimized and minified
- Smaller file size
- Requires code signing for distribution
- Suitable for production

```bash
./gradlew assembleRelease
```

## Troubleshooting

### "Gradle sync failed"
**Cause**: Network issues or missing dependencies

**Solution**:
1. Check internet connection
2. Try `File → Invalidate Caches / Restart` in Android Studio
3. Delete `.gradle` folder and sync again

### "SDK not found"
**Cause**: Android SDK not installed or not configured

**Solution**:
1. Open `File → Settings → Appearance & Behavior → System Settings → Android SDK`
2. Install required SDK platforms (API 26-34)
3. Install Android SDK Build-Tools

### "Kotlin version mismatch"
**Cause**: Incompatible Kotlin compiler version

**Solution**:
1. The project uses Kotlin 1.8.20
2. Ensure Android Studio has compatible Kotlin plugin
3. Update via `File → Settings → Plugins`

### "Unable to resolve dependencies"
**Cause**: Repository connection issues

**Solution**:
1. Check internet connection
2. Verify `settings.gradle.kts` has correct repositories:
   ```kotlin
   repositories {
       google()
       mavenCentral()
   }
   ```
3. Try with VPN if in restricted region

## Build Configuration

### Current Versions
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Kotlin**: 1.9.10
- **Gradle**: 8.0
- **AGP**: 8.1.4
- **Compose Compiler**: 1.5.3

### Key Dependencies
```kotlin
// Compose & Material 3
implementation("androidx.compose:compose-bom:2023.10.01")
implementation("androidx.compose.material3:material3")

// Google Fonts
implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")

// Window API (foldable support)
implementation("androidx.window:window:1.2.0")

// Coil (image loading)
implementation("io.coil-kt:coil-compose:2.5.0")
```

## Installation

After building, install the APK on an Android device:

### Via Android Studio
1. Connect device via USB with USB debugging enabled
2. Click the "Run" button (green triangle)
3. Select your device from the list

### Via Command Line
```bash
# Install debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Install release APK  
adb install app/build/outputs/apk/release/app-release.apk
```

### Manual Installation
1. Transfer APK file to device
2. Open file manager on device
3. Tap APK file
4. Allow "Install from Unknown Sources" if prompted
5. Tap "Install"

## Setting as Default Launcher

After installation:
1. Press the Home button
2. Android will ask "Which app do you want to use?"
3. Select "DuoGlass"
4. Choose "Always" to set as default launcher
5. Grant required permissions (Query All Packages)

## Build Times

Expected build times (first build):
- **Clean build**: 2-5 minutes
- **Incremental build**: 10-30 seconds
- **Gradle sync**: 30-60 seconds

Subsequent builds are much faster due to Gradle caching.

## Additional Commands

### Clean Build
Removes all build artifacts and rebuilds from scratch:
```bash
./gradlew clean assembleDebug
```

### Check Dependencies
View all project dependencies:
```bash
./gradlew :app:dependencies
```

### Lint Check
Run Android Lint to check for issues:
```bash
./gradlew lint
```

### Generate Reports
Create build reports:
```bash
./gradlew build --scan
```

## Support

If you encounter issues:
1. Check the [ARCHITECTURE.md](ARCHITECTURE.md) for system details
2. Review the [IMPLEMENTATION.md](IMPLEMENTATION.md) for requirements
3. Ensure all prerequisites are installed
4. Try building with Android Studio for better error messages

## Notes

- First build downloads ~500MB of dependencies
- Gradle daemon speeds up subsequent builds
- Use Android Studio for best development experience
- Release builds require code signing configuration
