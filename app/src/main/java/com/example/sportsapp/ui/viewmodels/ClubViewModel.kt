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

    // State untuk daftar klub
    private val _clubs = MutableStateFlow<List<Team>>(emptyList())
    val clubs: StateFlow<List<Team>> = _clubs.asStateFlow()

    // State untuk loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // State untuk klub yang dipilih
    private val _selectedClub = MutableStateFlow<Team?>(null)
    val selectedClub: StateFlow<Team?> = _selectedClub.asStateFlow()

    // State untuk query pencarian
    private val _searchQuery = MutableStateFlow("Arsenal")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadClubs("Arsenal")
    }

    fun searchClubs(query: String) {
        _searchQuery.value = query
        loadClubs(query)
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

    fun selectClub(club: Team) {
        _selectedClub.value = club
    }

    fun clearError() {
        _error.value = null
    }
}