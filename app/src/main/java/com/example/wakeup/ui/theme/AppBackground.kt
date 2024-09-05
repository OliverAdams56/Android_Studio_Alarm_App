/*
 * this file is for anything to do with the background color of screens
 */

package com.example.wakeup.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme

@Composable
fun MainBackgroundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    )
}

@Composable
fun SettingBackgroundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    )
}

@Preview(device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420")
@Preview(
    showBackground = true,
    name = "Home Screen",
    fontScale = 1f,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun Preview4() {
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            SettingBackgroundScreen()
        }
    }
}