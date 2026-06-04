package com.example.smarthome.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route:String,
    val title: String,
    val icon: ImageVector
){
    object Dashboard: BottomNavItem("dashboard", "Home", Icons.Default.Home)
    object Appliances : BottomNavItem("appliances", "Devices", Icons.Default.List)
    object Billing : BottomNavItem("billing", "Billing", Icons.Default.Star)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}