package com.example.sportsapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object ClubList : Screen("club_list")
    object ClubDetail : Screen("club_detail/{clubName}") {
        fun createRoute(clubName: String) = "club_detail/$clubName"
    }
    object About : Screen("about")

}