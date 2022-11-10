package com.assignment.rnt.ui.screen.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.rnt.R
import com.assignment.rnt.ui.theme.RandomNumberTestTheme
import com.assignment.rnt.ui.viewmodel.RemoteNumberUiState

/**
 * UI for showing status of remote number, loading/error/retry
 */
@Composable
fun RemoteNumberStatus(
    numberUiState: RemoteNumberUiState,
    onRetryClicked: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (numberUiState) {
            is RemoteNumberUiState.Error -> {
                Text(
                    text = stringResource(id = R.string.error_prefix, numberUiState.message),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.error
                )
                Button(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                    onClick = {
                        onRetryClicked?.let { it() }
                    }
                ) {
                    Text(stringResource(id = R.string.number_test_retry_button))
                }
            }
            is RemoteNumberUiState.Loading -> {
                Text(
                    text = stringResource(id = R.string.number_test_loading),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                CircularProgressIndicator(Modifier.padding(horizontal = 16.dp))
            }
            is RemoteNumberUiState.Loaded,
            is RemoteNumberUiState.Empty -> {
                // no ui for this states
            }
        }
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun RemoteNumberStatusLoadingLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RemoteNumberStatus(
                RemoteNumberUiState.Loading
            )
        }
    }
}

@Preview
@Composable
fun RemoteNumberStatusLoadingDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RemoteNumberStatus(
                RemoteNumberUiState.Loading
            )
        }
    }
}

@Preview
@Composable
fun RemoteNumberStatusErrorLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RemoteNumberStatus(
                RemoteNumberUiState.Error("Test")
            )
        }
    }
}

@Preview
@Composable
fun RemoteNumberStatusErrorDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            RemoteNumberStatus(
                RemoteNumberUiState.Error("Test")
            )
        }
    }
}