/*
 * this file is for anything to do with the Setting screens
 */

package com.example.wakeup.ui.theme

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.wakeup.data.AlarmReceiver
import com.example.wakeup.data.ThemePreference.alarmTimePreference
import com.example.wakeup.data.ThemePreference.clearAlarmTimePreference
import com.example.wakeup.data.ThemePreference.setAlarmTimePreference
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SettingsScreen(navController: NavHostController, onToggleTheme: () -> Unit) {
    val context = LocalContext.current
    val alarmTime by context.alarmTimePreference.collectAsState(initial = "Set Alarm Time")
    var showAlarmBox by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        SettingBackgroundScreen()
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            Spacer(modifier = Modifier.height(0.dp))
            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicOutlinedButton(text = alarmTime, icon = Icons.Default.Settings, onClick = {
                    showAlarmBox = !showAlarmBox
                })
                BasicOutlinedButton(
                    text = "Clear Alarm Time",
                    icon = Icons.Default.Clear,
                    onClick = {
                        coroutineScope.launch {
                            context.clearAlarmTimePreference()
                        }
                    })
                BasicOutlinedButton(
                    text = "Change App Theme", icon = Icons.Default.Info, onClick = onToggleTheme
                )
                Spacer(modifier = Modifier.weight(1f))
                AlarmBox(isVisible = showAlarmBox) { time ->
                    coroutineScope.launch {
                        context.setAlarmTimePreference("Alarm time is: $time")
                        scheduleAlarm(context, time)
                    }
                    showAlarmBox = false
                }
                BottomBar(navController)
            }
        }
    }
}

@Composable
fun BasicOutlinedButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AlarmTimePicker(onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
        val selectedCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        val formattedTime =
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(selectedCalendar.time)
        onTimeSelected(formattedTime)
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)

    LaunchedEffect(Unit) {
        timePickerDialog.show()
    }
}

@Composable
fun AlarmBox(isVisible: Boolean, onTimeSelected: (String) -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(expandFrom = Alignment.Bottom, initialHeight = { 0 }),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom, targetHeight = { 0 })
    ) {
        AlarmTimePicker(onTimeSelected = onTimeSelected)
    }
}

fun scheduleAlarm(context: Context, alarmTime: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
            return
        }
    }
    val calendar = Calendar.getInstance()
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    try {
        val parsedTime = timeFormat.parse(alarmTime) ?: run {
            Toast.makeText(context, "Invalid alarm time format", Toast.LENGTH_SHORT).show()
            return
        }

        calendar.apply {
            val currentCalendar = Calendar.getInstance()
            time = parsedTime
            set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
            set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH))
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error parsing alarm time", Toast.LENGTH_SHORT).show()
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
fun Preview() {
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SettingBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                val navController = rememberNavController()
                SettingsScreen(navController, onToggleTheme = {})
            }
        }
    }
}
