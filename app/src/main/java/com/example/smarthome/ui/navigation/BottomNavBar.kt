package com.example.smarthome.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smarthome.ui.theme.Blue
import com.example.smarthome.ui.theme.NavBackground
import com.example.smarthome.ui.theme.NavIndicator
import com.example.smarthome.ui.theme.NavUnselected

@Composable
fun BottomNavBar(navController: NavController,userId: String) {

    val items=listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Appliances,
        BottomNavItem.Billing,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 0.dp
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute=navBackStackEntry?.destination?.route

        items.forEach { item->
            NavigationBarItem(
                icon = {Icon(item.icon,contentDescription=item.title)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ),
                label = { Text(item.title) },
                selected = currentRoute == item.route ||
                        (item is BottomNavItem.Dashboard && currentRoute?.startsWith("dashboard") == true),
                onClick = {
                    when (item) {
                        is BottomNavItem.Dashboard -> {
                            if (userId.isNotEmpty()) {
                                navController.navigate("dashboard/$userId") {
                                    popUpTo("dashboard/$userId") { inclusive = true }
                                }
                            }
                        }
                        else -> {
                            navController.navigate(item.route) {
                                popUpTo(item.route) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    }
}