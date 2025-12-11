package com.example.sportsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.data.model.Team
import com.example.sportsapp.data.repository.ClubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClubViewModel : ViewModel() {
    private val repository = ClubRepository()

    private val _clubs = MutableStateFlow<List<Team>>(emptyList())
    val clubs: StateFlow<List<Team>> = _clubs.asStateFlow()

    private val _clubDetail = MutableStateFlow<Team?>(null)
    val clubDetail: StateFlow<Team?> = _clubDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Cache untuk default teams
    private var defaultTeamsCache: List<Team> = emptyList()

    init {
        loadDefaultTeams()
    }

    fun searchClubs(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            if (defaultTeamsCache.isNotEmpty()) {
                _clubs.value = defaultTeamsCache
            } else {
                loadDefaultTeams()
            }
        } else {
            loadClubs(query)
        }
    }

    private fun loadClubs(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.searchClub(query)
            result.onSuccess { response ->
                _clubs.value = response.teams ?: emptyList()
                if (response.teams.isNullOrEmpty()) {
                    _error.value = "Klub tidak ditemukan"
                }
            }.onFailure { exception ->
                _error.value = exception.message ?: "Gagal memuat data"
                _clubs.value = emptyList()
            }

            _isLoading.value = false
        }
    }

    fun loadDefaultTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _searchQuery.value = ""

            val result = repository.getDefaultTeams()
            result.onSuccess { response ->
                val teams = response.teams ?: emptyList()
                _clubs.value = teams
                defaultTeamsCache = teams // Simpan ke cache

                if (teams.isEmpty()) {
                    _error.value = "Tidak ada tim yang ditemukan"
                }
            }.onFailure { exception ->
                _error.value = exception.message ?: "Gagal memuat tim default"
                _clubs.value = emptyList()
                defaultTeamsCache = emptyList()
            }

            _isLoading.value = false
        }
    }

    fun loadClubDetail(clubName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _clubDetail.value = null

            // Search klub spesifik
            val result = repository.searchClub(clubName)
            result.onSuccess { response ->
                // Cari klub yang tepat sesuai nama
                val club = response.teams?.find {
                    it.strTeam?.equals(clubName, ignoreCase = true) == true
                }
                _clubDetail.value = club

                if (club == null) {
                    _error.value = "Klub '$clubName' tidak ditemukan"
                }
            }.onFailure { exception ->
                _error.value = exception.message ?: "Gagal memuat detail klub"
                _clubDetail.value = null
            }

            _isLoading.value = false
        }
    }

    fun clearClubDetail() {
        _clubDetail.value = null
    }

    fun clearError() {
        _error.value = null
    }

    fun clearClubs() {
        _clubs.value = emptyList()
    }

    fun resetToDefault() {
        if (defaultTeamsCache.isNotEmpty()) {
            _clubs.value = defaultTeamsCache
            _searchQuery.value = ""
        } else {
            loadDefaultTeams()
        }
    }
}