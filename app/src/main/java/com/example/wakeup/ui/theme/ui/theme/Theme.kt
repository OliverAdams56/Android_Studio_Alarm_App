package com.example.wakeup.ui.theme.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wakeup.R


// Define custom colors
val PrimaryColor = Color(0xFFFFC0CB) // Light pink
val SecondaryColor = Color(0xFF985174) // Main background color
val OnPrimaryColor = Color.DarkGray // Text color on primary surfaces

val KobePurple = Color(0xFF552583) // Lakers Purple
val KobeGold = Color(0xFFFFD700) // Lakers Gold
val OnKobePrimaryColor = Color.White // Text color on Kobe primary surfaces


val QwitcherGrypen = FontFamily(Font(R.font.qwitchergrypen_bold, FontWeight.ExtraBold))

// Define typography styles
val AppTypography = Typography(headlineLarge = TextStyle(fontFamily = QwitcherGrypen, fontWeight = FontWeight.ExtraBold, fontSize = 100.sp, letterSpacing = 0.sp), bodyMedium = TextStyle(fontFamily = QwitcherGrypen, fontWeight = FontWeight.Normal, fontSize = 15.sp))

// Define shapes
val AppShapes = Shapes(small = RoundedCornerShape(8.dp), medium = RoundedCornerShape(16.dp), large = RoundedCornerShape(32.dp))

private val LightAppColorScheme = lightColorScheme(primary = PrimaryColor, onPrimary = OnPrimaryColor, secondary = SecondaryColor, surface = PrimaryColor, background = SecondaryColor, onBackground = OnPrimaryColor)
private val DarkAppColorScheme = darkColorScheme(primary = KobePurple, onPrimary = OnKobePrimaryColor, secondary = KobeGold, surface = KobePurple, background = KobeGold, onBackground = OnKobePrimaryColor)

@Composable
fun WakeUpTheme(darkTheme: Boolean = false, content: @Composable () -> Unit)
{
    val colorScheme = if (darkTheme) DarkAppColorScheme else LightAppColorScheme
    MaterialTheme(colorScheme = colorScheme, typography = AppTypography, shapes = AppShapes, content = content)
}