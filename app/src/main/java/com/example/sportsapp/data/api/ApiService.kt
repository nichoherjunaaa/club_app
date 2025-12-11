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
}

object ApiConstants {
    const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/123/"
}