package com.assignment.rnt.ui.screen.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assignment.rnt.R
import com.assignment.rnt.ui.theme.RandomNumberTestTheme
import com.assignment.rnt.ui.viewmodel.LocalNumberUiState

/**
 * UI for showing user input field and "compare" button to perform test.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserInput(
    inputUiState: LocalNumberUiState,
    onValidateInput: ((String) -> Unit)? = null,
    onCompareClicked: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            value = inputUiState.numberString,
            onValueChange = { input ->
                onValidateInput?.let { it(input) }
            },
            label = { Text(stringResource(id = R.string.number_test_input_hint)) },
            singleLine = true,
            isError = (inputUiState is LocalNumberUiState.Error) && inputUiState.error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onCompareClicked?.let { it() }
            })
        )

        if (inputUiState is LocalNumberUiState.Error && inputUiState.error != null) {
            Text(
                text = inputUiState.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Button(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            enabled = inputUiState.number != null,
            onClick = {
                onCompareClicked?.let { it() }
            }
        ) {
            Text(stringResource(id = R.string.number_test_compare_button))
        }
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun UserInputEmptyLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(LocalNumberUiState.Normal())
        }
    }
}

@Preview
@Composable
fun UserInputEmptyDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(LocalNumberUiState.Normal())
        }
    }
}

@Preview
@Composable
fun UserInputNormalLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(LocalNumberUiState.Normal("10"))
        }
    }
}

@Preview
@Composable
fun UserInputNormalDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(LocalNumberUiState.Normal("10"))
        }
    }
}

@Preview
@Composable
fun UserInputErrorLight() {
    RandomNumberTestTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(
                LocalNumberUiState.Error(
                    "123ds",
                    stringResource(id = R.string.number_test_error_invalid_number)
                )
            )
        }
    }
}

@Preview
@Composable
fun UserInputErrorDark() {
    RandomNumberTestTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(
                LocalNumberUiState.Error(
                    "123ds",
                    stringResource(id = R.string.number_test_error_invalid_number)
                )
            )
        }
    }
}