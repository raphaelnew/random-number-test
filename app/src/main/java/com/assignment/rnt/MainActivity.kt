package com.assignment.rnt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.assignment.rnt.ui.navigation.MainNavHost
import com.assignment.rnt.ui.theme.RandomNumberTestTheme
import com.assignment.rnt.ui.theme.SystemUi
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main window of application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomNumberTestTheme {
                SystemUi(window)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
                }
            }
        }
    }
}