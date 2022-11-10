package com.assignment.rnt.ui.screen.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.assignment.rnt.R
import com.assignment.rnt.ui.viewmodel.LocalNumberUiState
import com.assignment.rnt.ui.viewmodel.NumberTestViewModel
import com.assignment.rnt.ui.viewmodel.RemoteNumberUiState

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
                if (numberUiState is RemoteNumberUiState.Loaded) {
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
        }
    )
}