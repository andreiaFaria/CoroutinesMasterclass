package com.plcoding.coroutinesmasterclass.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BirdViewModel: ViewModel() {

    private var _selectedBird = MutableStateFlow<BirdType>(BirdType.NoBird)
    val selectedBird = _selectedBird.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedBird.collect(){
                this.coroutineContext.cancelChildren()
                if (selectedBird.value != BirdType.NoBird){
                    birdSinging(selectedBird.value.bird.voice, this)
                }else {
                    println("Any bird selected!")
                }
            }
        }
    }

    fun setBirdSelected(birdType: BirdType){
        _selectedBird.value = birdType
    }


    private suspend fun birdSinging(birdSong: String, scope: CoroutineScope){
        scope.launch {
            while (true){
                println(birdSong)
                delay(1000)
            }
        }
    }
}