package com.iftikar.mediuser.presentation.screens.waiting_screen

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.mediuser.domain.model.User
import com.iftikar.mediuser.navigation.Routes
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.sin
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity

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
        modifier = Modifier
            .fillMaxSize()
    ) {

        WaitingScreenAnimation(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
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
    }


@Composable
private fun WaitingScreenAnimation(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradientWave")
    val theta by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "theta"
    )
    val wave = sin(theta)
    val doubleWave = sin(theta * 2)
    val tripleWave = sin(theta * 3)
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    val baseY = screenHeightPx * 0.82f // near bottom
    val bandHeight = 120f
    val colorStops = listOf(
        androidx.compose.ui.geometry.Offset(screenWidthPx * (0.25f + 0.13f * wave), baseY),
        androidx.compose.ui.geometry.Offset(
            screenWidthPx * (0.5f + 0.10f * doubleWave),
            baseY + bandHeight * 0.35f
        ),
        androidx.compose.ui.geometry.Offset(
            screenWidthPx * (0.75f + 0.13f * tripleWave),
            baseY + bandHeight
        )
    )
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.55f),
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.45f)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = gradientColors,
                    start = colorStops.first(),
                    end = colorStops.last()
                )
            )
    ) {
        content()
    }
}
