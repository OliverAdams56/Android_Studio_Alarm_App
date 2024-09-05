/*
 * this file is for anything to do with the navigation's between screens
 */

package com.example.wakeup.ui.theme

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHostSetup(navController: NavHostController, darkTheme: Boolean, onToggleTheme: () -> Unit) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen", enterTransition = {
            if (initialState.destination.route != "main_screen") {
                fadeIn(animationSpec = tween(1500)) + scaleIn(
                    initialScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, exitTransition = {
            if (targetState.destination.route != "main_screen") {
                fadeOut(animationSpec = tween(1500)) + scaleOut(
                    targetScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, popEnterTransition = {
            if (initialState.destination.route != "main_screen") {
                fadeIn(animationSpec = tween(1500)) + scaleIn(
                    initialScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, popExitTransition = {
            if (targetState.destination.route != "main_screen") {
                fadeOut(animationSpec = tween(1500)) + scaleOut(
                    targetScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }) {
            MyScreen(navController)
        }

        composable("settings_screen", enterTransition = {
            if (initialState.destination.route != "settings_screen") {
                fadeIn(animationSpec = tween(1500)) + scaleIn(
                    initialScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, exitTransition = {
            if (targetState.destination.route != "settings_screen") {
                fadeOut(animationSpec = tween(1500)) + scaleOut(
                    targetScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, popEnterTransition = {
            if (initialState.destination.route != "settings_screen") {
                fadeIn(animationSpec = tween(1500)) + scaleIn(
                    initialScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }, popExitTransition = {
            if (targetState.destination.route != "settings_screen") {
                fadeOut(animationSpec = tween(1500)) + scaleOut(
                    targetScale = 1f, animationSpec = tween(1500)
                )
            } else {
                null
            }
        }) {
            SettingsScreen(navController, onToggleTheme = onToggleTheme)
        }
    }
}
