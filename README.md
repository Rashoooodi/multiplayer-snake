# DuoGlass Launcher

A flagship-quality Android launcher application featuring Windows 11 Acrylic x Material 3 design language with glassmorphism effects.

## Features

### Design Language
- **Glassmorphism**: Blur effects (30.dp) on Android 12+, high-alpha scrims on older APIs
- **Corner Radius**: Strictly 24.dp for all cards
- **Material 3**: Modern, expressive design
- **Dynamic Theming**: Dark, Light, and OLED modes

### Target Hardware Support
- **Samsung Z Fold 3/4/5/6**: Tabletop Mode and Unfolded split-screen layouts
- **Pixel 8 Pro / S20+**: Seamless, paginated swipe interface

### Core Features
1. **Setup Wizard (OOBE)**: 6-page onboarding experience
   - Identity (User name)
   - Typography selection (Poppins or Outfit fonts)
   - Search engine preference (Google, Bing, DuckDuckGo)
   - Grid density (Comfortable 4-col or Dense 6-col)
   - Visual theme selection
   
2. **User Preferences**: DataStore-based persistence
   - User name
   - Search engine choice
   - Font style (Poppins or Geometric/Outfit)
   - Grid size
   - Theme mode
   - Setup completion status

3. **Responsive Home Screen**
   - **Slab Layout** (< 600dp): Two-page horizontal pager with Dashboard and App Grid
   - **Fold Layout** (≥ 600dp): Split-screen with Dashboard (50%) + 24dp hinge + App Grid (50%)

4. **App Management**
   - Fetch all installed apps
   - Filter out launcher itself
   - Alphabetical sorting
   - Launch apps on click

## Technical Stack

- **Language**: Kotlin
- **Framework**: Jetpack Compose with Material 3
- **Dependencies**:
  - `androidx.compose.ui:ui-text-google-fonts` - For Poppins and Outfit typography
  - `androidx.datastore:datastore-preferences` - User preferences storage
  - `androidx.window:window` - Foldable device support
  - `io.coil-kt:coil-compose` - Image loading

## Project Structure

```
app/src/main/java/com/duoglass/launcher/
├── MainActivity.kt      - Entry point, setup check
├── SetupWizard.kt      - 6-page OOBE flow
├── Home.kt             - Responsive home screen (Slab/Fold)
├── Store.kt            - DataStore preferences repository
├── Type.kt             - Google Fonts provider setup
├── Theme.kt            - Dynamic theme engine
└── GlassUI.kt          - Reusable glassmorphism components
```

## Building

### Quick Start

**Using the build scripts:**
```bash
# Linux/Mac
./build.sh

# Windows
build.bat
```

**Using Android Studio (Recommended):**
1. Open Android Studio
2. File → Open → Select this directory
3. Wait for Gradle sync
4. Build → Build Bundle(s) / APK(s) → Build APK(s)

**Using Gradle directly:**
```bash
# Linux/Mac
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

📖 **For detailed build instructions, see [BUILD_GUIDE.md](BUILD_GUIDE.md)**

This is a standard Android project. To build:

1. Open in Android Studio
2. Sync Gradle files
3. Build and run on device or emulator

**Requirements**:
- Android Studio (recommended) OR Gradle 8.0+ with Android SDK
- JDK 11 or later
- Internet connection (for first build to download dependencies)
- Android SDK 26-34

### Output Location
After building, the APK will be at:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

## Design Specifications

### Glassmorphism Components
- **GlassCard**: Reusable card with blur/scrim effect
- **GlassButton**: Interactive button with glass styling
- **GlassSurface**: Full-screen glass surface
- **SelectableGlassCard**: Glass card with selection state

### Typography
- **Poppins**: Modern, geometric sans-serif
- **Outfit**: Product Sans style alternative

### Color Schemes
- **Dark**: Material 3 dark color scheme
- **Light**: Material 3 light color scheme  
- **OLED**: Pure black background for OLED displays

## License

This is a demonstration project created as part of the multiplayer-snake repository.
