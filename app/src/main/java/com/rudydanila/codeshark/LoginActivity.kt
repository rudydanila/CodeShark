package com.rudydanila.codeshark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudydanila.codeshark.ui.theme.CodeSharkTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        setContent {
            CodeSharkTheme {

                val navController = rememberNavController()
                val context = LocalContext.current

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            context = context,
                            onLoginSuccess = { email ->
                                navController.navigate("knowledge")
                            }
                        )
                    }
                    composable("knowledge") {
                        KnowledgeLevelScreen { level ->
                            prefs.edit().putString("user_level", level).apply()
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                    composable("home") {
                        HomeScreen(context)
                    }
                }

            }
        }
    }
}

