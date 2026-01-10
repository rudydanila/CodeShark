package com.rudydanila.codeshark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudydanila.codeshark.ui.theme.CodeSharkTheme
import kotlinx.coroutines.launch


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        setContent {
            CodeSharkTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            context = this@LoginActivity,
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
                        HomeScreen(
                            drawerState = drawerState,
                            onBackClick = {
                                if (drawerState.isOpen) {
                                    scope.launch { drawerState.close() }
                                } else {
                                    finish()
                                }
                            },
                            onLevelClick = { levelId ->
                                if (levelId == 1) {
                                    navController.navigate("exercise1")
                                } else if (levelId == 2) {
                                    navController.navigate("exercise2")
                                } else if (levelId == 3) {
                                    navController.navigate("exercise3")
                                } else if (levelId == 4) {
                                    navController.navigate("exercise4")
                                }
                            }
                        )
                    }

                    composable("exercise1") {
                        ExerciseScreen1(onBackClick = { navController.popBackStack() })
                    }
                    composable("exercise2") {
                        ExerciseScreen2(onBackClick = { navController.popBackStack() })
                    }
                    composable("exercise3") {
                        ExerciseScreen3(onBackClick = { navController.popBackStack() })
                    }
                    composable("exercise4") {
                        ExerciseScreen4(onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
