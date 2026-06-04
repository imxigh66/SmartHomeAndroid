package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.ui.screens.dashboard.DashboardScreen
import com.example.smarthome.ui.screens.login.LoginScreen
import com.example.smarthome.ui.screens.register.RegisterScreen
import com.example.smarthome.ui.theme.SmartHomeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartHomeTheme {
                val navController=rememberNavController()

                NavHost(
                    navController=navController,
                    startDestination="login"
                ){
                    composable("login"){
                        LoginScreen(
                            onLoginSuccess = { userId ->
                                navController.navigate("dashboard/$userId"){
                                    popUpTo("login"){inclusive=true}
                                }
                            },
                            onRegisterClick = {
                                navController.navigate("register")
                            }
                        )
                    }

                    composable("dashboard/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        DashboardScreen(userId = userId)
                    }

                    composable("register") {
                        RegisterScreen(
                            onRegisterSuccess = {
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            },
                            onLoginClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

