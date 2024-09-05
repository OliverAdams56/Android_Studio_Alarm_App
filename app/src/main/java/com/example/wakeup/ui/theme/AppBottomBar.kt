/*
 * this file is for anything to do with the Bottom bar of screens
 */

package com.example.wakeup.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme

@Composable
fun BottomBar(navController: NavController) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate("main_screen") {
                            popUpTo("main_screen") { inclusive = true }
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Home", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        navController.navigate("settings_screen")
                    }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Settings",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
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
fun Preview5() {
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                val navController = rememberNavController()
                BottomBar(navController)
            }
        }
    }
}