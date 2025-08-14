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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.mediuser.domain.model.User
import com.iftikar.mediuser.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun WaitingScreen(
    userId: String,
    viewModel: WaitingScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var user by remember {mutableStateOf<User?>(null)}
    var approved by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = userId, key2 = approved) {
        viewModel.startPollingUser(userId = userId)

        user?.let { user ->
            if (approved) {
                delay(5_000L)
                navHostController.navigate(Routes.HomeScreen(user = user)){
                    popUpTo<Routes.WaitingScreen> {
                        inclusive = true
                    }
                }
            }
        }

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
                    user = waitState.user
                    if (waitState.user.isApproved == 0) {
                        Text(
                            "Hello ${waitState.user.name}, please wait for admin to approve you. \n" +
                                    "When approved you will be redirected to next screen automatically"
                        )
                    } else {
                        Text("You are approved, redirecting you to HomeScreen")
                        approved = true
                    }
                }
            }
        }
    }
}
























