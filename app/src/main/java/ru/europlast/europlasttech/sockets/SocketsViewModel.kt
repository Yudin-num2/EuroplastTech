package ru.europlast.europlasttech.sockets

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SocketViewModel : ViewModel() {
    private val _buttonColors = MutableStateFlow<Map<Int, Map<Int, Color>>>(emptyMap())
    val buttonColors: StateFlow<Map<Int, Map<Int, Color>>> = _buttonColors

    fun updateButtonColor(page: Int, buttonIndex: Int, color: Color) {
        viewModelScope.launch {
            val pageColors = _buttonColors.value[page]?.toMutableMap() ?: mutableMapOf()
            pageColors[buttonIndex] = color
            _buttonColors.value = _buttonColors.value.toMutableMap().apply {
                this[page] = pageColors
            }
        }
    }
}