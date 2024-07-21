package ru.europlast.europlasttech.sockets

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.europlast.europlasttech.data.CurrentSocketsState
import ru.europlast.europlasttech.data.RetrofitInstance.networkAPI


class SocketViewModel(machineName: String) : ViewModel() {
    private val _buttonColors = MutableStateFlow(CurrentSocketsState())
    val buttonColors: StateFlow<CurrentSocketsState> = _buttonColors
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val parseColors = mapOf(
        "goodSocket" to Color(0xFF37FF00),
        "Underfilling" to Color(0xFF1C71D8),
        "Dirt" to Color(0xFFAC7E64),
        "NeedleChipping" to Color(0xFF7641D1),
        "FlashByClosure" to Color(0xFFA0A71B),
        "Crack" to Color(0xFFFF29EC),
        "Hole" to Color(0xFFE01B24),
        "GateValve" to Color(0xFFC0FF41),
        "NotWarm" to Color(0xFF003287),
        "Geometry" to Color(0xFFD2691E),
        "Water" to Color(0xFF00DAC7)
    )
    init {
            loadCurrentSocketsState(machineName)
    }

    private fun loadCurrentSocketsState(machineName: String) {
        viewModelScope.launch {
            val response = networkAPI.getCurrentSockets(machineName)
            if (response.isSuccessful) {
                val currentState = response.body()
                if (currentState != null) {
                    _buttonColors.value = currentState
                }
            } else {
                _buttonColors.value = CurrentSocketsState()
            }
            _isLoading.value = false

        }
    }

    fun getColorForSocket(category: String, index: Int): Color {

        val socketMap = when (category) {
            "Lid" -> _buttonColors.value.lid
            "Frame" -> _buttonColors.value.frame
            "Cutter" -> _buttonColors.value.cutter
            "Lid12" -> _buttonColors.value.lid12
            "Frame12" -> _buttonColors.value.frame12
            "Cutter12" -> _buttonColors.value.cutter12
            "Husky 11" -> _buttonColors.value.husky11
            "Husky 12" -> _buttonColors.value.husky12
            "Husky 16" -> _buttonColors.value.husky16
            "Husky 22" -> _buttonColors.value.husky22
            else -> null
        }
        val colorKey = socketMap?.get(index.toString())
        return parseColors[colorKey] ?: Color.Gray
    }

    fun updateSocketColor(category: String, index: Int, colorKey: Color) {
        val updatedMap = when (category) {
            "Lid" -> _buttonColors.value.lid?.toMutableMap()
            "Frame" -> _buttonColors.value.frame?.toMutableMap()
            "Cutter" -> _buttonColors.value.cutter?.toMutableMap()
            "Lid12" -> _buttonColors.value.lid12?.toMutableMap()
            "Frame12" -> _buttonColors.value.frame12?.toMutableMap()
            "Cutter12" -> _buttonColors.value.cutter12?.toMutableMap()
            "Husky 11" -> _buttonColors.value.husky11?.toMutableMap()
            "Husky 12" -> _buttonColors.value.husky12?.toMutableMap()
            "Husky 16" -> _buttonColors.value.husky16?.toMutableMap()
            "Husky 22" -> _buttonColors.value.husky22?.toMutableMap()
            else -> null
        }

        updatedMap?.let {
            val newColor = parseColors.entries.firstOrNull { it.value == colorKey }?.key
            if (newColor != null) {
                it[index.toString()] = newColor
                val newState = when (category) {
                    "Lid" -> _buttonColors.value.copy(lid = it)
                    "Frame" -> _buttonColors.value.copy(frame = it)
                    "Cutter" -> _buttonColors.value.copy(cutter = it)
                    "Lid12" -> _buttonColors.value.copy(lid12 = it)
                    "Frame12" -> _buttonColors.value.copy(frame12 = it)
                    "Cutter12" -> _buttonColors.value.copy(cutter12 = it)
                    "Husky 11" -> _buttonColors.value.copy(husky11 = it)
                    "Husky 12" -> _buttonColors.value.copy(husky12 = it)
                    "Husky 16" -> _buttonColors.value.copy(husky16 = it)
                    "Husky 22" -> _buttonColors.value.copy(husky22 = it)
                    else -> _buttonColors.value
                }
                _buttonColors.value = newState
            }
        }
    }
}



class SocketViewModelFactory(
    private val machineName: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SocketViewModel(machineName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


