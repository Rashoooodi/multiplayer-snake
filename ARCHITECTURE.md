# DuoGlass Launcher - Architecture & Flow

## Application Flow

1. **App Launch** → MainActivity
   - Checks `isSetupComplete` from DataStore
   - If `false`: Redirect to SetupActivity
   - If `true`: Display HomeScreen

2. **Setup Wizard** (First Launch)
   - **Page 1**: User enters their name
   - **Page 2**: Choose typography (Poppins or Outfit)
   - **Page 3**: Select search engine (Google/Bing/DDG)
   - **Page 4**: Choose grid density (4 or 6 columns)
   - **Page 5**: Select visual theme (Dark/Light/OLED)
   - **Page 6**: Confirm and launch
   - Saves all preferences to DataStore
   - Marks setup as complete
   - Navigates to MainActivity

3. **Home Screen** (Post-Setup)
   - Reads preferences from DataStore
   - Applies selected theme and font
   - Detects screen size (600dp breakpoint)
   - Displays appropriate layout (Slab or Fold)

## Data Layer

### UserPreferences (Store.kt)
```kotlin
- userName: String
- searchEngine: String (GOOGLE/BING/DDG)
- fontStyle: String (POPPINS/GEOMETRIC)
- gridSize: String (COMFORTABLE/DENSE)
- themeMode: String (DARK/LIGHT/OLED)
- isSetupComplete: Boolean
```

Stored using AndroidX DataStore (Preferences)

## UI Components

### GlassUI.kt
- `GlassCard`: Base glass container with blur effect
- `GlassButton`: Interactive glass button
- `GlassSurface`: Full-screen glass background
- `SelectableGlassCard`: Glass card with selection state

### Theme.kt
- `DuoGlassTheme`: Wrapper composable
  - Accepts `fontStyle` and `themeMode`
  - Creates appropriate Typography
  - Applies correct ColorScheme
  - Wraps content in MaterialTheme

### Type.kt
- Google Fonts Provider configuration
- `PoppinsFontFamily`: Poppins font in multiple weights
- `OutfitFontFamily`: Outfit font (Product Sans style)
- `createTypography()`: Builds Material 3 Typography

## Responsive Layouts

### Slab Layout (< 600dp)
```
┌─────────────────┐
│   Dashboard     │ ← Page 0
└─────────────────┘
    [Swipe] →
┌─────────────────┐
│   App Grid      │ ← Page 1
└─────────────────┘
```

### Fold Layout (≥ 600dp)
```
┌──────────┬──┬──────────┐
│          │  │          │
│Dashboard │24│ App Grid │
│          │dp│          │
└──────────┴──┴──────────┘
  50%      Hinge    50%
```

## Key Features

### Dashboard
- Time-based greeting ("Good Morning, {name}")
- Search bar with web search functionality
- Widget area (placeholder)

### App Grid
- Fetches installed apps via PackageManager
- Filters out launcher itself
- Sorts alphabetically
- Adaptive column count (4 or 6)
- Launch apps on tap

### Search
- Opens selected search engine in browser
- URL encoding for queries
- Supports Google, Bing, DuckDuckGo

## Design Philosophy

1. **Minimal but Flagship**: Focus on core launcher features with premium UX
2. **Responsive**: Adapts to phone, tablet, and foldable form factors
3. **Accessible**: Material 3 design with clear typography hierarchy
4. **Performant**: Lazy loading for app grid, efficient state management
5. **Consistent**: 24dp corner radius, glassmorphism throughout

## Extensibility

Future enhancements could include:
- Widget support
- App shortcuts
- Gesture customization
- Icon pack support
- Search provider plugins
- Weather widget
- Calendar integration
