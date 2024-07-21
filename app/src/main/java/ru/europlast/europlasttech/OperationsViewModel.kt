package ru.europlast.europlasttech

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.europlast.europlasttech.data.CurrentSocketsState
import ru.europlast.europlasttech.data.RetrofitInstance.networkAPI
import ru.europlast.europlasttech.data.TechCard
import ru.europlast.europlasttech.sockets.SocketViewModel

class OperationsViewModel(techCard: String) : ViewModel() {
    private var _operations = MutableStateFlow(TechCard())
    val operations: StateFlow<TechCard> = _operations
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init{
        loadCheckList(techCard)
    }
    private fun loadCheckList(techCard: String) {
        viewModelScope.launch {
            val response = networkAPI.getTechCard(techCard)
            if (response.isSuccessful) {
                val operats = response.body()
                if (operats != null) {
                    _operations.value = operats
                }
            } else {
                _operations.value = TechCard()
            }
            _isLoading.value = false
        }
    }

    fun getOperationsForMachine(machineName: String): List<String> {
        val operationList = when (machineName) {
            "Telerobot" -> _operations.value.telerobot
            else -> (operations.value.tpa.orEmpty() + operations.value.greenBox.orEmpty()
                    + operations.value.pressform.orEmpty()).distinct()
        }
        Log.d("OperationsVM | operationList", "$operationList")
        return operationList
    }
}

class OperationsViewModelFactory(
    private val techCard: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OperationsViewModel(techCard) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
/*
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
    }*/

