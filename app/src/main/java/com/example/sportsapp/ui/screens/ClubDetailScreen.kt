package com.example.sportsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.sportsapp.ui.viewmodels.ClubViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDetailScreen(
    clubName: String,
    onBackClick: () -> Unit,
    viewModel: ClubViewModel = viewModel()
) {
    val clubDetail by viewModel.clubDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(clubName) {
        // Load detail klub secara spesifik
        viewModel.loadClubDetail(clubName)
    }

    // Clear data ketika keluar dari screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearClubDetail()
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = clubDetail?.strTeam ?: clubName,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Error",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error!!,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            androidx.compose.material3.Button(
                                onClick = {
                                    viewModel.clearError()
                                    onBackClick()
                                }
                            ) {
                                Text("Kembali")
                            }
                        }
                    }
                }

                clubDetail != null -> {
                    ClubDetailContent(club = clubDetail!!)
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Klub tidak ditemukan",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Klub '$clubName' tidak ditemukan dalam database.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            androidx.compose.material3.Button(
                                onClick = onBackClick
                            ) {
                                Text("Kembali ke Daftar Klub")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClubDetailContent(club: com.example.sportsapp.data.model.Team) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Banner/Logo Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                if (!club.strBanner.isNullOrEmpty()) {
                    AsyncImage(
                        model = club.strBanner,
                        contentDescription = "Club Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (!club.strBadge.isNullOrEmpty()) {
                    AsyncImage(
                        model = club.strBadge,
                        contentDescription = "Club Badge",
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Fallback jika tidak ada gambar
                    Text(
                        text = club.strTeam ?: "Club",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Basic Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = club.strTeam ?: "Unknown Club",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // League Info
                    InfoRow(
                        icon = Icons.Default.Info,
                        title = "League",
                        value = club.strLeague ?: "Unknown"
                    )

                    // Country Info
                    InfoRow(
                        icon = Icons.Default.LocationOn,
                        title = "Country",
                        value = club.strCountry ?: "Unknown"
                    )

                    // Founded Year
                    club.intFormedYear?.let { year ->
                        if (year.isNotEmpty() && year != "0") {
                            InfoRow(
                                icon = Icons.Default.Info,
                                title = "Founded",
                                value = year
                            )
                        }
                    }

                    // Stadium Info
                    club.strStadium?.let { stadium ->
                        if (stadium.isNotEmpty()) {
                            InfoRow(
                                icon = Icons.Default.Stadium,
                                title = "Stadium",
                                value = stadium
                            )

                            club.intStadiumCapacity?.let { capacity ->
                                if (capacity.isNotEmpty() && capacity != "0") {
                                    Text(
                                        text = "Capacity: $capacity",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(start = 40.dp, top = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Website
                    club.strWebsite?.let { website ->
                        if (website.isNotEmpty()) {
                            InfoRow(
                                icon = Icons.Default.Web,
                                title = "Website",
                                value = website
                            )
                        }
                    }

                    // Sport Type
                    club.strSport?.let { sport ->
                        if (sport.isNotEmpty()) {
                            InfoRow(
                                icon = Icons.Default.Info,
                                title = "Sport",
                                value = sport
                            )
                        }
                    }
                }
            }
        }

        // Description Section
        club.strDescriptionEN?.let { description ->
            if (description.isNotEmpty() && description.length > 10) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "About",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Justify,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}