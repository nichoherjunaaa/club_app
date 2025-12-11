package com.example.sportsapp.data.repository

import com.example.sportsapp.data.api.RetrofitClient
import com.example.sportsapp.data.model.TeamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClubRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun searchClub(query: String): Result<TeamResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchTeams(query)
            if (response.isSuccessful) {
                Result.success(response.body() ?: TeamResponse())
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDefaultTeams(): Result<TeamResponse> = withContext(Dispatchers.IO) {
        try {

             val response = apiService.getAllTeamsByLeague("English_Premier_League")

            if (response.isSuccessful) {
                Result.success(response.body() ?: TeamResponse())
            } else {
                Result.failure(Exception("Failed to load default teams"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}