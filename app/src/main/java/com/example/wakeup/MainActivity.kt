package com.example.wakeup

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.wakeup.data.ThemePreference.setThemePreference
import com.example.wakeup.data.ThemePreference.themePreference
import com.example.wakeup.ui.theme.MainBackgroundScreen
import com.example.wakeup.ui.theme.MyScreen
import com.example.wakeup.ui.theme.NavHostSetup
import com.example.wakeup.ui.theme.SettingsScreen
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity()
{
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
            {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Notification permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            val context = applicationContext
            val themePreferenceFlow = context.themePreference
            val darkTheme by themePreferenceFlow.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()

            val navController = rememberNavController()
            WakeUpTheme(darkTheme = darkTheme) {
                NavHostSetup(navController = navController, darkTheme = darkTheme, onToggleTheme = {
                    coroutineScope.launch {
                        context.setThemePreference(!darkTheme)
                    }
                })
            }
        }
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
fun Preview()
{

    WakeUpTheme(darkTheme = true){
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                MyScreen(navController)
            }
        }
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
fun Preview0()
{
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()

                SettingsScreen(navController, onToggleTheme = {})
            }
        }
    }
}