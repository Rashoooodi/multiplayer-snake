package com.duoglass.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * MainActivity - Entry Point
 * Checks if setup is complete:
 * - If not complete: Launch SetupActivity
 * - If complete: Show HomeScreen
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            LauncherApp()
        }
    }
}

@Composable
fun LauncherApp() {
    val context = LocalContext.current
    val preferences = remember { UserPreferences(context) }
    var isSetupComplete by remember { mutableStateOf<Boolean?>(null) }
    val scope = rememberCoroutineScope()
    
    // Check if setup is complete
    LaunchedEffect(Unit) {
        scope.launch {
            isSetupComplete = preferences.isSetupComplete.first()
        }
    }
    
    // Wait until we know the setup status
    when (isSetupComplete) {
        null -> {
            // Loading - show nothing or a splash screen
        }
        false -> {
            // Setup not complete - launch SetupActivity
            LaunchedEffect(Unit) {
                val intent = Intent(context, SetupActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            }
        }
        true -> {
            // Setup complete - show home screen
            HomeScreen()
        }
    }
}
