package com.smartplant.smartplantandroid.main.ui.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StartViewModel : ViewModel() {
    sealed class StartPageState(val id: String) {
        object Welcome : StartPageState("welcome")
        object Features : StartPageState("features")
        object Start : StartPageState("start")
    }

    private val _pages = MutableStateFlow<List<StartPageState>>(emptyList())
    val pages: StateFlow<List<StartPageState>> get() = _pages.asStateFlow()

    init {
        _pages.value = listOf(
            StartPageState.Welcome,
            StartPageState.Features,
            StartPageState.Start
        )
    }
}