package com.assignment.rnt.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.rnt.R
import com.assignment.rnt.ui.theme.RandomNumberTestTheme

/**
 * Home screen of app with List of Coin exchange rates.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToNumberTest: (() -> Unit)? = null) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.home_hint)
            )
            Button(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                onClick = {
                    onNavigateToNumberTest?.let { it() }
                }
            ) {
                Text(stringResource(id = R.string.home_start_button))
            }
        }
    })
}

@Preview
@Composable
fun HomePreviewLight() {
    RandomNumberTestTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun HomePreviewDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        HomeScreen()
    }
}