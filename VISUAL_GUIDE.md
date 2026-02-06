# DuoGlass Launcher - Visual Flow

## User Experience Flow

```
┌─────────────────────────────────────────────────┐
│              First Launch                       │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│           Setup Wizard (OOBE)                   │
├─────────────────────────────────────────────────┤
│  Page 1: Welcome                                │
│  ┌─────────────────────────────┐               │
│  │ "Welcome."                  │               │
│  │ [Enter your name]           │               │
│  └─────────────────────────────┘               │
│                                      [Next] ──> │
├─────────────────────────────────────────────────┤
│  Page 2: Choose Typeface                       │
│  ┌─────────────────────────────┐               │
│  │ ○ Poppins                   │               │
│  │   "The quick brown fox"     │               │
│  └─────────────────────────────┘               │
│  ┌─────────────────────────────┐               │
│  │ ○ Product Sans Style        │               │
│  │   "The quick brown fox"     │               │
│  └─────────────────────────────┘               │
│                                      [Next] ──> │
├─────────────────────────────────────────────────┤
│  Page 3: Search Engine                         │
│  ○ Google                                      │
│  ○ Bing                                        │
│  ○ DuckDuckGo                                  │
│                                      [Next] ──> │
├─────────────────────────────────────────────────┤
│  Page 4: Grid Density                          │
│  ○ Comfortable (4 columns)                     │
│  ○ Dense - Productivity (6 columns)            │
│                                      [Next] ──> │
├─────────────────────────────────────────────────┤
│  Page 5: Visual Theme                          │
│  ○ Glass Dark                                  │
│  ○ Glass Light                                 │
│  ○ OLED                                        │
│                                      [Next] ──> │
├─────────────────────────────────────────────────┤
│  Page 6: Launch                                │
│  ┌─────────────────────────────┐               │
│  │   "All Set."                │               │
│  │                             │               │
│  │      [Launch]               │               │
│  └─────────────────────────────┘               │
└─────────────────────────────────────────────────┘
                      ↓
            Saves to DataStore
                      ↓
┌─────────────────────────────────────────────────┐
│              Home Screen                        │
└─────────────────────────────────────────────────┘
```

## Home Screen Layouts

### Phone Layout (< 600dp) - "Slab"
```
┌───────────────────────────────┐
│  [Page 0: Dashboard]          │
│  ┌─────────────────────────┐  │
│  │ Good Morning, Alex      │  │
│  └─────────────────────────┘  │
│                               │
│  ┌─────────────────────────┐  │
│  │ [Search the web...]  Go │  │
│  └─────────────────────────┘  │
│                               │
│  ┌─────────────────────────┐  │
│  │    Widgets Area         │  │
│  │                         │  │
│  └─────────────────────────┘  │
└───────────────────────────────┘
      ← Swipe Left →
┌───────────────────────────────┐
│  [Page 1: App Grid]           │
│  ┌───┐ ┌───┐ ┌───┐ ┌───┐    │
│  │📱 │ │📱 │ │📱 │ │📱 │    │
│  │App│ │App│ │App│ │App│    │
│  └───┘ └───┘ └───┘ └───┘    │
│  ┌───┐ ┌───┐ ┌───┐ ┌───┐    │
│  │📱 │ │📱 │ │📱 │ │📱 │    │
│  │App│ │App│ │App│ │App│    │
│  └───┘ └───┘ └───┘ └───┘    │
└───────────────────────────────┘
```

### Tablet/Foldable Layout (≥ 600dp) - "Fold"
```
┌────────────────────────┬──┬────────────────────────┐
│    Dashboard (50%)     │24│    App Grid (50%)      │
│                        │dp│                        │
│  Good Morning, Alex    │  │  ┌──┐┌──┐┌──┐┌──┐    │
│                        │  │  │📱││📱││📱││📱│    │
│  ┌──────────────────┐  │  │  └──┘└──┘└──┘└──┘    │
│  │ Search bar... Go │  │  │  ┌──┐┌──┐┌──┐┌──┐    │
│  └──────────────────┘  │  │  │📱││📱││📱││📱│    │
│                        │  │  └──┘└──┘└──┘└──┘    │
│  ┌──────────────────┐  │  │  ┌──┐┌──┐┌──┐┌──┐    │
│  │   Widgets        │  │  │  │📱││📱││📱││📱│    │
│  │                  │  │  │  └──┘└──┘└──┘└──┘    │
│  └──────────────────┘  │  │  ┌──┐┌──┐┌──┐┌──┐    │
│                        │  │  │📱││📱││📱││📱│    │
└────────────────────────┴──┴────────────────────────┘
```

## Glassmorphism Design

### GlassCard Appearance
```
┌────────────────────────────────┐
│ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░  │ ← 1dp border (10% opacity)
│ ░                          ░  │
│ ░  Content with 70% alpha  ░  │ ← Background blur (30dp on API 31+)
│ ░  overlay on blurred      ░  │
│ ░  background              ░  │
│ ░                          ░  │
│ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░  │
└────────────────────────────────┘
         ↑
    24dp corner radius (strictly)
```

## Color Schemes

### Dark Mode
- Background: #1A1C1E (Dark grey)
- Surface: #1A1C1E
- Primary: #90CAF9 (Light blue)
- Text: #E2E2E6 (Light grey)

### Light Mode
- Background: #FDFCFF (Near white)
- Surface: #FDFCFF
- Primary: #005FA8 (Blue)
- Text: #1A1C1E (Dark grey)

### OLED Mode
- Background: #000000 (Pure black)
- Surface: #000000
- Primary: #90CAF9 (Light blue)
- Text: #E2E2E6 (Light grey)

## Typography Examples

### Poppins Font
```
Display Large: "DuoGlass" (57sp, Bold)
Headline Medium: "Good Morning" (28sp, SemiBold)
Body Large: "Search the web..." (16sp, Normal)
Label Large: "Next" (14sp, Medium)
```

### Outfit Font (Geometric)
```
Display Large: "DuoGlass" (57sp, Bold)
Headline Medium: "Good Morning" (28sp, SemiBold)
Body Large: "Search the web..." (16sp, Normal)
Label Large: "Next" (14sp, Medium)
```

## Interaction Patterns

### Setup Navigation
- Each page validates input before allowing "Next"
- Page 1 requires non-empty name
- Pages 2-5 require selection (default pre-selected)
- Page 6 saves and navigates to MainActivity

### Home Interactions
1. **Search**: Type → Press "Go" → Opens browser
2. **App Launch**: Tap app icon → Launches app
3. **Page Navigation** (Phone): Swipe left/right
4. **Responsive**: Auto-detects screen size and adapts layout

## State Management

```
DataStore (Persistent)
    ↓
UserPreferences Repository
    ↓
Flow<T> (Reactive)
    ↓
Composable State (collectAsState)
    ↓
UI Updates
```

All preferences are:
- Saved immediately on change
- Persisted across app restarts
- Observed reactively via Kotlin Flow
- Applied to UI in real-time
