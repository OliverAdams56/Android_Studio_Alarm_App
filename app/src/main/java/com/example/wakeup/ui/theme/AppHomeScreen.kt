/*
 * this file is for anything to do with the home screens
 */

package com.example.wakeup.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyScreen(navController: NavHostController, darkTheme: Boolean, onToggleTheme: () -> Unit)
{
    Box(modifier = Modifier.fillMaxSize()) {
        MainBackgroundScreen()
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            Spacer(modifier = Modifier.height(0.dp))
            Clock()
            Spacer(modifier = Modifier.weight(1f))
            CustomSlider()
            Spacer(modifier = Modifier.height(0.dp))
            BottomBar(navController)
        }
    }
}

@Composable
fun Clock()
{
    var time by remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        while (true)
        {
            time = getCurrentTime()
            delay(1000) // Update every second
        }
    }
    Text(text = time, fontWeight = FontWeight.Bold, fontSize = 48.sp, fontFamily = FontFamily.Serif, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}

fun getCurrentTime(): String
{
    val dateFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    return dateFormat.format(Date())
}
@Composable
fun CustomSlider()
{
    var sliderValue by remember { mutableFloatStateOf(0f) }
    val valueRange = 0f..1f // Slider range from 0 to 1
    val totalDurationInMillis = 90000f // Duration to reach max in milliseconds (1.5 minutes)

    Column(modifier = Modifier
        .padding(horizontal = 30.dp) // Add some horizontal padding
        .padding(vertical = 10.dp), // Add vertical padding for spacing
        verticalArrangement = Arrangement.Center, // Center contents vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center contents horizontally
    ) {
        Slider(value = sliderValue, onValueChange = { newValue -> // Smooth out slider value changes
            sliderValue += ((newValue - sliderValue) / (totalDurationInMillis / 1000))
            sliderValue = sliderValue.coerceIn(valueRange) // Keep value within range
        }, valueRange = valueRange // Set range of the slider
        )
        Spacer(modifier = Modifier.height(0.dp)) // Spacer to separate Slider and Text
        Text(
            text = "Slider Value: ${String.format(Locale.getDefault(), "%.2f", sliderValue)}", // Display slider value
            color = MaterialTheme.colorScheme.primary,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420")
@Preview(
    showBackground = true,
    name = "Home Screen",
    fontScale = 1f,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun Preview1()
{
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar()
                Spacer(modifier = Modifier.height(0.dp))
                Clock()
                Spacer(modifier = Modifier.weight(1f))
                CustomSlider()
                Spacer(modifier = Modifier.height(0.dp))
                val navController = rememberNavController()
                BottomBar(navController)
            }
        }
    }
}