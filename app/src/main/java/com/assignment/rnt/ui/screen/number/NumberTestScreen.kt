package com.assignment.rnt.ui.screen.number

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.rnt.R
import com.assignment.rnt.ui.theme.RandomNumberTestTheme
import com.assignment.rnt.ui.viewmodel.LocalNumberUiState
import com.assignment.rnt.ui.viewmodel.NumberTestViewModel
import com.assignment.rnt.ui.viewmodel.RemoteNumberUiState
import com.assignment.rnt.ui.viewmodel.ResultUiState

/**
 * Screen for fetching random number, take input from user, show result.
 */
@Composable
fun NumberTestScreen(
    viewModel: NumberTestViewModel,
    onNavigateBack: () -> Unit
) {
    NumberTestScreen(
        numberUiState = viewModel.numberUiState.collectAsState().value,
        inputUiState = viewModel.inputUiState.collectAsState().value,
        resultUiState = viewModel.resultUiState.collectAsState().value,
        onValidateInput = { input -> viewModel.validateInput(input) },
        onCompareClicked = { viewModel.compare() },
        onRetryClicked = { viewModel.fetchNumber(true) },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NumberTestScreen(
    numberUiState: RemoteNumberUiState,
    inputUiState: LocalNumberUiState,
    resultUiState: ResultUiState,
    onValidateInput: ((String) -> Unit)? = null,
    onCompareClicked: (() -> Unit)? = null,
    onRetryClicked: (() -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.number_test_title)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack?.let { it() } }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_description_nav_back)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (resultUiState) {
                    is ResultUiState.Empty -> {
                        if (numberUiState is RemoteNumberUiState.Loaded) {
                            Text(
                                text = stringResource(
                                    id = R.string.number_test_remote_number,
                                    numberUiState.number
                                ),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                            )
                            UserInput(
                                inputUiState = inputUiState,
                                onValidateInput = onValidateInput,
                                onCompareClicked = onCompareClicked
                            )
                        } else {
                            RemoteNumberStatus(
                                numberUiState = numberUiState,
                                onRetryClicked = onRetryClicked
                            )
                        }
                    }
                    is ResultUiState.Compared -> {
                        NumberResultStatus(resultUiState)
                    }
                }
            }
        }
    )
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun NumberTestScreenLoadingLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loading,
                inputUiState = LocalNumberUiState.Normal(),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenLoadingDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loading,
                inputUiState = LocalNumberUiState.Normal(),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenLoadedLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal(),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenLoadedDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal(),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenInputLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal("22"),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenInputDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal("22"),
                resultUiState = ResultUiState.Empty,
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenResultLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal("22"),
                resultUiState = ResultUiState.Compared(
                    22,
                    55,
                    stringResource(id = R.string.result_lower, 22, 55)
                ),
            )
        }
    }
}

@Preview
@Composable
fun NumberTestScreenResultDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NumberTestScreen(
                numberUiState = RemoteNumberUiState.Loaded(55),
                inputUiState = LocalNumberUiState.Normal("22"),
                resultUiState = ResultUiState.Compared(
                    22,
                    55,
                    stringResource(id = R.string.result_lower, 22, 55)
                ),
            )
        }
    }
}