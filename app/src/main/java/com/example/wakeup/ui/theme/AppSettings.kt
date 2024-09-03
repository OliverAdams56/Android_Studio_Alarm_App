/*
 * this file is for anything to do with the Setting screens
 */

package com.example.wakeup.ui.theme

import android.app.TimePickerDialog
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme
import java.util.Calendar
import java.util.Locale

@Composable
fun SettingsScreen(navController: NavHostController, darkTheme: Boolean, onToggleTheme: () -> Unit)
{
    var showAlarmBox by remember { mutableStateOf(false) }
    var alarmTime by remember { mutableStateOf("Set Alarm Time") }

    Box(modifier = Modifier.fillMaxSize()) {
        SettingBackgroundScreen() // Use the same background
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar() // Add the TopBar
            Spacer(modifier = Modifier.height(0.dp))
            Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = 48.sp, fontFamily = FontFamily.Serif, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp), verticalArrangement = Arrangement.spacedBy(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                BasicOutlinedButton(text = alarmTime, icon = Icons.Default.Settings, onClick = {
                    showAlarmBox = !showAlarmBox
                })
                BasicOutlinedButton(text = "Change App Theme", icon = Icons.Default.Info, onClick = {
                    onToggleTheme() // Ensure only one box is visible at a time
                })
                Spacer(modifier = Modifier.weight(1f))

                AlarmBox(isVisible = showAlarmBox, onTimeSelected = { time ->
                    alarmTime = "Alarm time is: $time"
                    showAlarmBox = false
                })

                BottomBar(navController)
            }
        }
    }
}

@Composable
fun BasicOutlinedButton(text: String, icon: ImageVector, onClick: () -> Unit)
{
    OutlinedButton(onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ), shape = RoundedCornerShape(100.dp), // Rounded corners
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp) // Padding to align with screen width
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
        Text(text = text, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }

}

@Composable
fun AlarmTimePicker(onTimeSelected: (String) -> Unit)
{
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        val formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
        onTimeSelected(formattedTime)
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)

    LaunchedEffect(Unit) {
        timePickerDialog.show()
    }
}

@Composable
fun AlarmBox(isVisible: Boolean, onTimeSelected: (String) -> Unit)
{
    AnimatedVisibility(visible = isVisible, enter = expandVertically(expandFrom = Alignment.Bottom, initialHeight = { 0 }), exit = shrinkVertically(shrinkTowards = Alignment.Bottom, targetHeight = { 0 })) {
        AlarmTimePicker(onTimeSelected = onTimeSelected)
    }
}

@Preview(device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420")
@Preview(
    showBackground = true,
    name = "Setting Screen",
    fontScale = 1f,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun Preview()
{
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SettingBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                val navController = rememberNavController()
                //SettingsScreen(navController)
            }
        }
    }
}