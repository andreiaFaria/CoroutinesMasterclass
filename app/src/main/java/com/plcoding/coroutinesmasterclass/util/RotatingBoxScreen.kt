package com.plcoding.coroutinesmasterclass.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun RotatingBoxScreen(
    modifier: Modifier = Modifier,
    birdViewModel: BirdViewModel
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angleRatio by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        ),
        label = ""
    )

    val selectedBird = birdViewModel.selectedBird.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        rotationZ = 360f * angleRatio
                    }
                    .background(selectedBird.bird.color)

            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(onClick = { birdViewModel.setBirdSelected(BirdType.Coo) }) {
                    Text(text = "Coo")
                }
                Button(onClick = { birdViewModel.setBirdSelected (BirdType.Caw) }) {
                    Text(text = "Caw")
                }
                Button(onClick = { birdViewModel.setBirdSelected( BirdType.Chirp)}) {
                    Text(text = "Chirp")
                }
                Button(onClick = { birdViewModel.setBirdSelected( BirdType.NoBird) }) {
                    Text(text = "No Bird")
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = selectedBird.bird.name)
        }
    }
}


sealed class BirdType(val bird: Bird){
    data object Coo : BirdType(Bird(name = "Coo", voice = "Cooing", color = Color.Gray))
    data object Caw : BirdType(Bird(name = "Caw", voice = "Cawing", color = Color.Black))
    data object Chirp : BirdType(Bird(name = "Chirp", voice = "Chirping", color = Color.Yellow))
    data object NoBird: BirdType(Bird(name = "NoBird", voice = "sh sh sh", color = Color.Red))
}

data class Bird (
    val name: String,
    val voice: String,
    val color: Color
)