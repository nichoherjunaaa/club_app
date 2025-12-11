package com.example.sportsapp.data.api

import com.example.sportsapp.data.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("searchteams.php")
    suspend fun searchTeams(
        @Query("t") teamName: String
    ): Response<TeamResponse>

    @GET("search_all_teams.php")
    suspend fun getAllTeamsByLeague(
        @Query("l") leagueName: String = ApiConstants.DEFAULT_LEAGUE_NAME
    ): Response<TeamResponse>
}

object ApiConstants {
    const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/123/"
    const val DEFAULT_LEAGUE_NAME = "English_Premier_League"
}