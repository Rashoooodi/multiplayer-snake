package com.duoglass.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * SetupActivity - OOBE (Out of Box Experience)
 * 6-page setup wizard using HorizontalPager
 */
class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupWizard()
        }
    }
}

@Composable
fun SetupWizard() {
    val context = LocalContext.current
    val preferences = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()
    
    // State for user selections
    var userName by remember { mutableStateOf("") }
    var selectedFont by remember { mutableStateOf("POPPINS") }
    var selectedEngine by remember { mutableStateOf("GOOGLE") }
    var selectedGridSize by remember { mutableStateOf("COMFORTABLE") }
    var selectedTheme by remember { mutableStateOf("DARK") }
    
    val pagerState = rememberPagerState(pageCount = { 6 })
    
    // Collect current font style to apply theme
    val currentFontStyle by preferences.fontStyle.collectAsState(initial = "POPPINS")
    val currentThemeMode by preferences.themeMode.collectAsState(initial = "DARK")
    
    DuoGlassTheme(
        fontStyle = selectedFont,
        themeMode = selectedTheme
    ) {
        GlassSurface {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Pager
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    when (page) {
                        0 -> Page1Identity(
                            userName = userName,
                            onNameChange = { userName = it }
                        )
                        1 -> Page2Typography(
                            selectedFont = selectedFont,
                            onFontSelect = { selectedFont = it }
                        )
                        2 -> Page3SearchEngine(
                            selectedEngine = selectedEngine,
                            onEngineSelect = { selectedEngine = it }
                        )
                        3 -> Page4GridLayout(
                            selectedGridSize = selectedGridSize,
                            onGridSizeSelect = { selectedGridSize = it }
                        )
                        4 -> Page5Theme(
                            selectedTheme = selectedTheme,
                            onThemeSelect = { selectedTheme = it }
                        )
                        5 -> Page6Launch(
                            onLaunch = {
                                scope.launch {
                                    // Save all preferences
                                    preferences.saveUserName(userName)
                                    preferences.saveFontStyle(selectedFont)
                                    preferences.saveSearchEngine(selectedEngine)
                                    preferences.saveGridSize(selectedGridSize)
                                    preferences.saveThemeMode(selectedTheme)
                                    preferences.setSetupComplete(true)
                                    
                                    // Navigate to MainActivity
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish()
                                }
                            }
                        )
                    }
                }
                
                // Navigation buttons (except on last page)
                if (pagerState.currentPage < 5) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        GlassButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            enabled = if (pagerState.currentPage == 0) userName.isNotBlank() else true
                        ) {
                            Text(
                                text = stringResource(R.string.next),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Page1Identity(
    userName: String,
    onNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(48.dp))
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = userName,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.enter_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun Page2Typography(
    selectedFont: String,
    onFontSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.choose_typeface),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        // Option A: Poppins
        SelectableGlassCard(
            selected = selectedFont == "POPPINS",
            onClick = { onFontSelect("POPPINS") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.poppins),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.preview_text),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        
        // Option B: Outfit (Product Sans style)
        SelectableGlassCard(
            selected = selectedFont == "GEOMETRIC",
            onClick = { onFontSelect("GEOMETRIC") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.product_sans_style),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.preview_text),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun Page3SearchEngine(
    selectedEngine: String,
    onEngineSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.how_do_you_search),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        listOf(
            "GOOGLE" to R.string.google,
            "BING" to R.string.bing,
            "DDG" to R.string.ddg
        ).forEach { (engine, nameRes) ->
            SelectableGlassCard(
                selected = selectedEngine == engine,
                onClick = { onEngineSelect(engine) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
fun Page4GridLayout(
    selectedGridSize: String,
    onGridSizeSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.grid_density),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        listOf(
            "COMFORTABLE" to R.string.comfortable,
            "DENSE" to R.string.dense
        ).forEach { (size, nameRes) ->
            SelectableGlassCard(
                selected = selectedGridSize == size,
                onClick = { onGridSizeSelect(size) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
fun Page5Theme(
    selectedTheme: String,
    onThemeSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.select_vibe),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        
        listOf(
            "DARK" to R.string.glass_dark,
            "LIGHT" to R.string.glass_light,
            "OLED" to R.string.oled
        ).forEach { (theme, nameRes) ->
            SelectableGlassCard(
                selected = selectedTheme == theme,
                onClick = { onThemeSelect(theme) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
fun Page6Launch(
    onLaunch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.all_set),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(64.dp))
        GlassButton(
            onClick = onLaunch,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = stringResource(R.string.launch),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
