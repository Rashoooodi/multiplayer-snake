package com.duoglass.launcher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import java.util.*

/**
 * App data class
 */
data class AppInfo(
    val label: String,
    val packageName: String,
    val icon: android.graphics.drawable.Drawable
)

/**
 * Responsive Home Screen
 * Uses BoxWithConstraints to detect screen size
 * Breakpoint: 600dp
 */
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val preferences = remember { UserPreferences(context) }
    
    // Collect user preferences
    val userName by preferences.userName.collectAsState(initial = "")
    val searchEngine by preferences.searchEngine.collectAsState(initial = "GOOGLE")
    val gridSize by preferences.gridSize.collectAsState(initial = "COMFORTABLE")
    val fontStyle by preferences.fontStyle.collectAsState(initial = "POPPINS")
    val themeMode by preferences.themeMode.collectAsState(initial = "DARK")
    
    // Fetch installed apps
    val apps by remember { mutableStateOf(fetchInstalledApps(context)) }
    
    DuoGlassTheme(
        fontStyle = fontStyle,
        themeMode = themeMode
    ) {
        GlassSurface {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val isTablet = maxWidth >= 600.dp
                
                if (isTablet) {
                    // B. The "Fold" Layout (> 600dp)
                    FoldLayout(
                        userName = userName,
                        searchEngine = searchEngine,
                        gridSize = gridSize,
                        apps = apps,
                        preferences = preferences
                    )
                } else {
                    // A. The "Slab" Layout (< 600dp)
                    SlabLayout(
                        userName = userName,
                        searchEngine = searchEngine,
                        gridSize = gridSize,
                        apps = apps,
                        preferences = preferences
                    )
                }
            }
        }
    }
}

/**
 * Slab Layout - Phone/Portrait
 * HorizontalPager with 2 pages
 */
@Composable
fun SlabLayout(
    userName: String,
    searchEngine: String,
    gridSize: String,
    apps: List<AppInfo>,
    preferences: UserPreferences
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> DashboardPage(userName, searchEngine, preferences)
            1 -> AppGridPage(gridSize, apps)
        }
    }
}

/**
 * Fold Layout - Tablet/Foldable
 * Row layout with 50/50 split
 */
@Composable
fun FoldLayout(
    userName: String,
    searchEngine: String,
    gridSize: String,
    apps: List<AppInfo>,
    preferences: UserPreferences
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Left: Dashboard (50%)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            DashboardPage(userName, searchEngine, preferences)
        }
        
        // Spacer: Hinge (24dp)
        Spacer(modifier = Modifier.width(24.dp))
        
        // Right: App Grid (50%)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            AppGridPage(gridSize, apps)
        }
    }
}

/**
 * Dashboard Page - Greeting, Search, Widgets
 */
@Composable
fun DashboardPage(
    userName: String,
    searchEngine: String,
    preferences: UserPreferences
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        
        // Greeting
        Text(
            text = getGreeting(userName),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Search Bar
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(stringResource(R.string.search_hint)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            TextButton(
                                onClick = {
                                    // Open browser with search query
                                    val url = preferences.getSearchUrl(searchEngine, searchQuery)
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                    searchQuery = ""
                                }
                            ) {
                                Text("Go")
                            }
                        }
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Widgets placeholder
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Widgets Area",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

/**
 * App Grid Page - Displays installed apps
 */
@Composable
fun AppGridPage(
    gridSize: String,
    apps: List<AppInfo>
) {
    val context = LocalContext.current
    val columns = if (gridSize == "COMFORTABLE") 4 else 6
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(apps) { app ->
            AppIcon(app) {
                launchApp(context, app.packageName)
            }
        }
    }
}

/**
 * App Icon Composable
 */
@Composable
fun AppIcon(
    app: AppInfo,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App icon
        val bitmap = remember(app.icon) {
            app.icon.toBitmap(width = 192, height = 192)
        }
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = app.label,
            modifier = Modifier.size(64.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // App label
        Text(
            text = app.label,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(72.dp)
        )
    }
}

/**
 * Fetch installed apps from PackageManager
 */
fun fetchInstalledApps(context: Context): List<AppInfo> {
    val packageManager = context.packageManager
    val launcherPackage = context.packageName
    
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    
    return packageManager.queryIntentActivities(intent, 0)
        .mapNotNull { resolveInfo ->
            val activityInfo = resolveInfo.activityInfo
            val packageName = activityInfo.packageName
            
            // Filter out the launcher itself
            if (packageName == launcherPackage) {
                null
            } else {
                try {
                    val appInfo = packageManager.getApplicationInfo(packageName, 0)
                    AppInfo(
                        label = packageManager.getApplicationLabel(appInfo).toString(),
                        packageName = packageName,
                        icon = packageManager.getApplicationIcon(appInfo)
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
        .sortedBy { it.label.lowercase() }
}

/**
 * Launch an app by package name
 */
fun launchApp(context: Context, packageName: String) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Get greeting based on time of day
 */
fun getGreeting(userName: String): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    return if (userName.isNotBlank()) {
        "$greeting, $userName"
    } else {
        greeting
    }
}
