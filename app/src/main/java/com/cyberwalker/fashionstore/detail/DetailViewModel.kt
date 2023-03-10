/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cyberwalker.fashionstore.detail

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cyberwalker.fashionstore.data.*
import com.cyberwalker.fashionstore.ui.theme.cardColorBlue
import com.cyberwalker.fashionstore.ui.theme.cardColorGreen
import com.cyberwalker.fashionstore.ui.theme.cardColorPeach
import com.cyberwalker.fashionstore.ui.theme.cardColorYellow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    private var _item: MutableLiveData<Item> = MutableLiveData<Item>()
    val item: LiveData<Item> get() = _item

    private var _sizeItem: MutableLiveData<SizeItem> = MutableLiveData<SizeItem>()
    val sizeItem: LiveData<SizeItem> get() = _sizeItem

    private var _colorItem: MutableLiveData<ColorItem> = MutableLiveData<ColorItem>()
    val colorItem: LiveData<ColorItem> get() = _colorItem

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.postValue(isFavorite)
    }

    fun setSizeItem(size: String) {
        when (size) {
            "S" -> _sizeItem.postValue(sizeS)
            "M" -> _sizeItem.postValue(sizeM)
            "L" -> _sizeItem.postValue(sizeL)
            "XL" -> _sizeItem.postValue(sizeXL)
        }
    }
    fun setColorItem(color: Color) {
        when (color) {
            cardColorGreen -> _colorItem.postValue(itemGreen)
            cardColorBlue -> _colorItem.postValue(itemBlue)
            cardColorPeach -> _colorItem.postValue(itemPeach)
            cardColorYellow -> _colorItem.postValue(itemYellow)
        }
    }

    fun setItem(item: Item){
        _item.postValue(item)
    }
}

data class DetailUiState(
    val txt: String? = null
)

class service : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}