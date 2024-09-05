/*
 * this file is for anything to do with the Top bar of screens
 */

package com.example.wakeup.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wakeup.R
import com.example.wakeup.ui.theme.ui.theme.WakeUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp)),
        color = Color.Transparent
    ) {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ), modifier = Modifier.fillMaxWidth(), title = {
            Text(
                text = "Wake Up",
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.sp,
                fontFamily = FontFamily(Font(R.font.qwitchergrypen_bold, FontWeight.ExtraBold)),
                fontSize = 100.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        })
    }
}

@Preview(device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420")
@Preview(
    showBackground = true,
    name = "Top Bar",
    fontScale = 1f,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
fun Preview3() {
    WakeUpTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            MainBackgroundScreen()
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar()
            }
        }
    }
}