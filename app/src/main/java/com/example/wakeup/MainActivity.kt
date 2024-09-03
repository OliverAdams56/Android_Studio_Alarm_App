package com.example.wakeup

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.wakeup.data.ThemePreference.setThemePreference
import com.example.wakeup.data.ThemePreference.themePreference
import com.example.wakeup.ui.theme.MainBackgroundScreen
import com.example.wakeup.ui.theme.NavHostSetup
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = applicationContext
            val themePreferenceFlow = context.themePreference
            val darkTheme by themePreferenceFlow.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()


            //var darkTheme by remember { mutableStateOf(false) }
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
    Box(modifier = Modifier.fillMaxSize()) {
        MainBackgroundScreen()
        Column(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController() //MyScreen(navController)
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
                val navController = rememberNavController() //SettingsScreen(navController)
            }
        }
    }

}