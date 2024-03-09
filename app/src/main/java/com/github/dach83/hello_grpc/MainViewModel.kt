package com.github.dach83.hello_grpc

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.grpc.okhttp.OkHttpChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    var uiState by mutableStateOf(MainUiState())
        private set

    fun onNameChanged(name: String) {
        uiState = uiState.copy(name = name)
    }

    fun onSendRequestClick() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val host = "10.0.2.2"
                val port = 50051
                val channel = OkHttpChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build()
                val stub = GreeterGrpc.newBlockingStub(channel)
                val request = HelloRequest.newBuilder()
                    .setName(uiState.name)
                    .build()
                val reply = stub.sayHello(request)
                uiState = uiState.copy(greeting = reply.message)
                channel.shutdown()
            } catch (e: Exception) {
                uiState = uiState.copy(greeting = "Error")
                e.printStackTrace()
            }
        }
    }

}

@Immutable
data class MainUiState(
    val name: String = "",
    val greeting: String = "undefined"
)
