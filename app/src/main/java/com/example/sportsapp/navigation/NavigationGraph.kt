package com.example.sportsapp.navigation

import androidx.compose.runtime.Composable
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
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashEnd = {
                    navController.navigate(Screen.ClubList.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true // Hapus splash dari backstack
                        }
                    }
                }
            )
        }

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