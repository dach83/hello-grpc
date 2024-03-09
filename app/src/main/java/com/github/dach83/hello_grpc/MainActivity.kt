package com.github.dach83.hello_grpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.dach83.hello_grpc.ui.theme.HellogrpcTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HellogrpcTheme {
                Greeting(
                    state = viewModel.uiState,
                    onNameChanged = viewModel::onNameChanged,
                    onSendRequestClick = viewModel::onSendRequestClick
                )
            }
        }
    }
}

@Composable
fun Greeting(
    state: MainUiState,
    onNameChanged: (String) -> Unit,
    onSendRequestClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = onNameChanged,
            modifier = Modifier.fillMaxWidth(.6f)
        )

        Button(onClick = onSendRequestClick) {
            Text(text = "Send request")
        }

        Text(text = state.greeting.ifEmpty { "Empty" })
    }
}