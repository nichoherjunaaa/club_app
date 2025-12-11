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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedClub = MutableStateFlow<Team?>(null)
    val selectedClub: StateFlow<Team?> = _selectedClub.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadDefaultTeams()
    }

    fun searchClubs(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            loadDefaultTeams()
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
                _clubs.value = response.teams ?: emptyList()
                if (response.teams.isNullOrEmpty()) {
                    _error.value = "Tidak ada tim yang ditemukan"
                }
            }.onFailure { exception ->
                _error.value = exception.message ?: "Gagal memuat tim default"
                _clubs.value = emptyList()
            }

            _isLoading.value = false
        }
    }

    fun resetToDefault() {
        loadDefaultTeams()
    }

    fun selectClub(club: Team) {
        _selectedClub.value = club
    }

    fun clearError() {
        _error.value = null
    }
}