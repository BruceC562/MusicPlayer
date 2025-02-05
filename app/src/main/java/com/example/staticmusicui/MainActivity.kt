package com.example.staticmusicui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.staticmusicui.ui.theme.StaticMusicUITheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StaticMusicUITheme {
                MusicUI()
            }
        }
    }
}

@Composable
fun MusicUI() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var playing by remember { mutableStateOf(false) }

    /* Fun slider animation functionality created by Gemini */
    LaunchedEffect(playing) {
        while (playing && sliderPosition < 433f) {
            delay(1000) // Wait for 1 second
            sliderPosition = (sliderPosition + 1).coerceAtMost(433f) // Increment smoothly
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(20.dp)
        ) {
            AlbumCover()
            SongArtistInformation()
            CreateSlider(sliderPosition, onPositionChange = {sliderPosition = it})
            CreateButtons(playing, onPlayingChange = {playing = it}) //onPlayingChange written by Gemini
        }
    }
}

@Composable
fun AlbumCover() {
    Box( //Box written by Gemini to help round corners of image
        modifier = Modifier
            .size(400.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.chopin_album_cover),
            contentDescription = "Chopin Album Cover",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun SongArtistInformation() {
    Column (
        modifier = Modifier
            .offset(x = 1.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start

    ) {
        Text (
            text = "Nocturne No.3 in B, Op.9 No.3",
            style = TextStyle(fontSize = 20.sp)
        )
        Text (
            text = "Frédéric Chopin, Claudio Arrau",
            style = TextStyle(fontSize = 15.sp, color = Color.Gray)
        )
    }
}


@Composable
fun CreateSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    /* Slider animation functionality created by Gemini */
    val animatedPosition by animateFloatAsState(
        targetValue = sliderPosition,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "sliderAnimation"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Slider(
            value = animatedPosition, // Use animated position for smoothness
            onValueChange = { onPositionChange(it) },
            valueRange = 0f..433f,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-14).dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = floatToTime(sliderPosition),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = floatToTime(433f),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

/* Function takes in the slider position and
   returns either the current time or remaining time */
@SuppressLint("DefaultLocale")
fun floatToTime(position: Float): String {
    val totalSeconds = position.toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds) //String format statement written by Gemini
}

@Composable
fun CreateButtons(playing: Boolean, onPlayingChange: (Boolean) -> Unit) {
    val iconID = if (playing) R.drawable.ic_pause else R.drawable.ic_play
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomButton(icon = R.drawable.ic_back, onClick = {})
        CustomButton(icon = iconID, circleSize = 100, iconSize = 75, onClick = { onPlayingChange(!playing) })
        CustomButton(icon = R.drawable.ic_next, onClick = {})
    }
}

@Composable
fun CustomButton(icon: Int, circleSize: Int = 60, iconSize: Int = 25 ,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(circleSize.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(iconSize.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MusicUIPreview() {
    StaticMusicUITheme {
        MusicUI()
    }
}