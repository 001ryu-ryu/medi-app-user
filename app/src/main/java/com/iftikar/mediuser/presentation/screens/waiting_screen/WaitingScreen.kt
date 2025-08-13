package com.iftikar.mediuser.presentation.screens.waiting_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WaitingScreen(userId: String, viewModel: WaitingScreenViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = userId) {
        viewModel.startPollingUser(userId = userId)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Text(userId)
            Spacer(Modifier.height(12.dp))

            when (val waitState = state.value) {
                is WaitingScreenState.Error -> {
                    Text(waitState.error)
                }

                WaitingScreenState.Loading -> {
                    Text("Verifying if you are approved")
                }

                is WaitingScreenState.Success -> {
                    Log.d("Approve", waitState.user.isApproved.toString())
                    if (waitState.user.isApproved == 0) {
                        Text("Hello ${waitState.user.name}, please wait for admin to approve you. \n" +
                                "When approved you will be redirected to next screen automatically")
                    } else {
                        Text("${waitState.user.name} you will be navigated to next when it's written")
                    }
                }

            }
        }
    }
}
























