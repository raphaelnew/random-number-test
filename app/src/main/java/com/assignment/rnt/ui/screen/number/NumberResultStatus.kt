package com.assignment.rnt.ui.screen.number

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.assignment.rnt.ui.viewmodel.ResultUiState

/**
 * UI for showing result of comparison user input number with remote number.
 */
@Composable
fun NumberResultStatus(resultUiState: ResultUiState.Compared) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NumberResultHeader()
        NumberResults(resultUiState = resultUiState)
        Text(
            text = resultUiState.resultMessage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun NumberResultHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = stringResource(id = R.string.result_header_local),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(id = R.string.result_header_remote),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        })
}

@Composable
private fun NumberResults(resultUiState: ResultUiState.Compared) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = resultUiState.localNumber.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = resultUiState.remoteNumber.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        })
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun NumberResultStatusLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberResultStatus(
                ResultUiState.Compared(1, 2, stringResource(id = R.string.result_lower, 1, 2))
            )
        }
    }
}

@Preview
@Composable
fun NumberResultStatusDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberResultStatus(
                ResultUiState.Compared(1, 2, stringResource(id = R.string.result_lower, 1, 2))
            )
        }
    }
}