package com.example.sportsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportsapp.ui.screens.SplashScreen
import com.example.sportsapp.ui.screens.ClubDetailScreen
import com.example.sportsapp.ui.screens.ClubListScreen
import com.example.sportsapp.ui.screens.AboutScreen

@Composable
fun NavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route // Ubah start destination ke Splash
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashEnd = {
                    // Navigasi ke ClubList setelah splash selesai
                    navController.navigate(Screen.ClubList.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true // Hapus splash dari backstack
                        }
                    }
                }
            )
        }

        // Club List Screen
        composable(Screen.ClubList.route) {
            ClubListScreen(
                onClubClick = { club ->
                    navController.navigate(Screen.ClubDetail.createRoute(club.strTeam ?: ""))
                },
                onAboutClick = {
                    navController.navigate(Screen.About.route) // Navigasi ke About
                }
            )
        }

        // Club Detail Screen
        composable(
            route = Screen.ClubDetail.route,
            arguments = listOf(navArgument("clubName") { type = NavType.StringType })
        ) { backStackEntry ->
            val clubName = backStackEntry.arguments?.getString("clubName") ?: ""
            ClubDetailScreen(
                clubName = clubName,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.About.route) {
            AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

    }
}