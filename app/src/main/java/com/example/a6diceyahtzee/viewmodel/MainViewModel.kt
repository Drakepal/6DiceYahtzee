package com.example.a6diceyahtzee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a6diceyahtzee.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "MainViewModel"


@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _currentPlayersMLD = MutableLiveData<ArrayList<Player>>()
    val currentPlayersLD: LiveData<ArrayList<Player>>
    get() = _currentPlayersMLD

    private val _toastSetMLB = MutableLiveData<Boolean?>()
    val toastSetLD: LiveData<Boolean?>
    get() = _toastSetMLB
}