package com.duoglass.launcher

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore extension property for Context
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * User Preferences Repository
 * Manages all user settings using DataStore
 */
class UserPreferences(private val context: Context) {
    
    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val SEARCH_ENGINE = stringPreferencesKey("search_engine")
        private val FONT_STYLE = stringPreferencesKey("font_style")
        private val GRID_SIZE = stringPreferencesKey("grid_size")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val IS_SETUP_COMPLETE = booleanPreferencesKey("is_setup_complete")
        
        // Default values
        const val DEFAULT_SEARCH_ENGINE = "GOOGLE"
        const val DEFAULT_FONT_STYLE = "POPPINS"
        const val DEFAULT_GRID_SIZE = "COMFORTABLE"
        const val DEFAULT_THEME_MODE = "DARK"
    }
    
    /**
     * Save user name
     */
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
    
    /**
     * Get user name as Flow
     */
    val userName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }
    
    /**
     * Save search engine preference
     * Valid values: "GOOGLE", "BING", "DDG"
     */
    suspend fun saveSearchEngine(engine: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_ENGINE] = engine
        }
    }
    
    /**
     * Get search engine as Flow
     */
    val searchEngine: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SEARCH_ENGINE] ?: DEFAULT_SEARCH_ENGINE
    }
    
    /**
     * Save font style preference
     * Valid values: "POPPINS", "GEOMETRIC"
     */
    suspend fun saveFontStyle(font: String) {
        context.dataStore.edit { preferences ->
            preferences[FONT_STYLE] = font
        }
    }
    
    /**
     * Get font style as Flow
     */
    val fontStyle: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[FONT_STYLE] ?: DEFAULT_FONT_STYLE
    }
    
    /**
     * Save grid size preference
     * Valid values: "COMFORTABLE" (4 cols), "DENSE" (6 cols)
     */
    suspend fun saveGridSize(size: String) {
        context.dataStore.edit { preferences ->
            preferences[GRID_SIZE] = size
        }
    }
    
    /**
     * Get grid size as Flow
     */
    val gridSize: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[GRID_SIZE] ?: DEFAULT_GRID_SIZE
    }
    
    /**
     * Save theme mode preference
     * Valid values: "DARK", "LIGHT", "OLED"
     */
    suspend fun saveThemeMode(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = theme
        }
    }
    
    /**
     * Get theme mode as Flow
     */
    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: DEFAULT_THEME_MODE
    }
    
    /**
     * Mark setup as complete
     */
    suspend fun setSetupComplete(isComplete: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_SETUP_COMPLETE] = isComplete
        }
    }
    
    /**
     * Check if setup is complete as Flow
     */
    val isSetupComplete: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_SETUP_COMPLETE] ?: false
    }
    
    /**
     * Get search URL based on engine and query
     */
    fun getSearchUrl(engine: String, query: String): String {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        return when (engine) {
            "GOOGLE" -> "https://www.google.com/search?q=$encodedQuery"
            "BING" -> "https://www.bing.com/search?q=$encodedQuery"
            "DDG" -> "https://duckduckgo.com/?q=$encodedQuery"
            else -> "https://www.google.com/search?q=$encodedQuery"
        }
    }
}
