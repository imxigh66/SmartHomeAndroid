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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.ui.navigation.BottomNavBar
import com.example.smarthome.ui.screens.appliances.AppliancesScreen
import com.example.smarthome.ui.screens.billing.BillingScreen
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
                val navController = rememberNavController()
                var currentUserId by remember { mutableStateOf("") }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute != "login" && currentRoute != "register"

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController = navController, userId = currentUserId)
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { id ->
                                    currentUserId = id
                                    navController.navigate("dashboard/$id") {
                                        popUpTo("login") { inclusive = true }
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

                        composable("appliances") {
                            AppliancesScreen()
                        }

                        composable("billing") {
                            BillingScreen()
                        }

                        composable("profile") {
                            // TODO
                        }
                    }


            }
            }
        }
    }
}

