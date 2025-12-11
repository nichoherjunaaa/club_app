package com.example.sportsapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AboutScreenTopBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        AboutScreenContent(
            paddingValues = paddingValues,
            onBackClick = onBackClick,
            onShareClick = { shareApp(context) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreenTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "About App",
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun AboutScreenContent(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        AboutScreenHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // App Description Card
            AppDescriptionCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Developer Info Card
            DeveloperInfoCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            ActionButtons(
                onShareClick = onShareClick,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AboutScreenHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 12.dp)
            )
            Text(
                text = "Football Club Info",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun AppDescriptionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "About This App",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Football Club Info adalah aplikasi yang menyediakan informasi lengkap tentang klub-klub sepak bola favorit Anda dari seluruh dunia. Temukan detail klub, pemain, statistik, dan masih banyak lagi.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Features:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val features = listOf(
                "Database klub sepak bola terlengkap",
                "Informasi detail klub dan pemain",
                "Pencarian klub berdasarkan nama",
                "Update informasi real-time",
                "Interface yang user-friendly",
                "Gratis dan tanpa iklan"
            )

            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun DeveloperInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Developer Information",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Email Section
            ContactItem(
                icon = Icons.Outlined.Email,
                title = "Email Support",
                content = "support@footballclubinfo.com"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Website Section
            ContactItem(
                icon = Icons.Outlined.Language,
                title = "Official Website",
                content = "www.footballclubinfo.com"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Social Media Section
            ContactItem(
                icon = Icons.Outlined.Share,
                title = "Social Media",
                content = "@footballclubinfo"
            )
        }
    }
}

@Composable
private fun ContactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            modifier = Modifier.padding(start = 32.dp)
        )
    }
}

@Composable
private fun ActionButtons(
    onShareClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Share Button
        Button(
            onClick = onShareClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Share This App",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.large,
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.5.dp
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Back to Home",
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Back to Club List",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

private fun shareApp(context: Context) {
    val shareText = """
    ⚽ Aplikasi Football Club Info ⚽
    
    Temukan informasi lengkap tentang klub-klub sepak bola dari seluruh dunia!
    
    📱 Fitur:
    • Database klub sepak bola terlengkap
    • Informasi detail klub dan pemain
    • Update real-time
    • Interface yang user-friendly
    • Gratis & tanpa iklan!
    
    Download sekarang dan jangan lewatkan update sepak bola!
    
    Download Link :https://play.google.com/store/apps/details?id=com.example.sportsapp
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Aplikasi Football Club Info ⚽")
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            "Bagikan aplikasi melalui"
        )
    )
}