package com.example.wakeup.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SliderState {
    private val _isSliderEnabled = MutableStateFlow(false)
    val isSliderEnabled: StateFlow<Boolean> get() = _isSliderEnabled

    fun setSliderEnabled(enabled: Boolean) {
        _isSliderEnabled.value = enabled
    }
}
