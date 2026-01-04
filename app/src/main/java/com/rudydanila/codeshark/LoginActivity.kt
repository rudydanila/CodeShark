package com.rudydanila.codeshark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudydanila.codeshark.ui.theme.CodeSharkTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        val webClientId = "113583699572-crfodb6gqvjfntqm0h3q2br4sc4u1jpq.apps.googleusercontent.com"

        setContent {
            CodeSharkTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "home" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            webClientId = webClientId,
                            onLoginClick = {
                                prefs.edit().putBoolean("isLoggedIn", true).apply()
                                navController.navigate("knowledge")
                            },
                            onGoogleClick = { request ->
                                lifecycleScope.launch {
                                    val result = signIn(request, this@LoginActivity)
                                    if (result == null) {
                                        prefs.edit().putBoolean("isLoggedIn", true).apply()
                                        navController.navigate("knowledge")
                                    }
                                }
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
                        HomeScreen()
                    }
                }
            }
        }

    }
}

