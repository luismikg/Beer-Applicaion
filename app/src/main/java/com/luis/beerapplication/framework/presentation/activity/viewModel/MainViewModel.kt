package com.luis.beerapplication.framework.presentation.activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.luis.beerapplication.data.model.BarItem
import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.domain.Repository
import com.luis.beerapplication.framework.presentation.main.adapters.MainAdapter
import com.luis.beerapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    //Main Fragment:
    val SHOW_ALL = 0
    val SHOW_FAVORITES = 1

    //Login Fragment
    private val _eventLoginMade = MutableLiveData(false)
    val eventLoginMade: LiveData<Boolean>
        get() = _eventLoginMade

    //Detail Fragment
    var item: BarItem? = null

    private val _eventAddFavorite = MutableLiveData(false)
    val eventAddFavorite: LiveData<Boolean>
        get() = _eventAddFavorite

    //Login Fragment
    fun goToMainScreen() {
        _eventLoginMade.value = true
    }

    fun goToMainScreenComplete() {
        _eventLoginMade.value = false
    }

    //Main Fragment:
    private val _eventOnNavigationItemSelected = MutableLiveData(this.SHOW_ALL)
    val eventOnNavigationItemSelected: LiveData<Int>
        get() = _eventOnNavigationItemSelected


    val fetchData = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getDataFromServer("10"))
        } catch (exception: Exception) {
            emit(Resource.Failure<Exception>(exception))
        }
    }

    fun selectedAll() {
        _eventOnNavigationItemSelected.value = this.SHOW_ALL
    }

    fun selectedFavorites() {
        _eventOnNavigationItemSelected.value = this.SHOW_FAVORITES
    }

    fun getFavorites(mainAdapter: MainAdapter) {
        viewModelScope.launch {
            try {
                val result = repository.getFavorites()
                mainAdapter.setFavorites((result as Resource.Success).data)
            } catch (_: Exception) {
            }
        }
    }

    //Detail fragment:
    fun pressAddFavorite() {
        this.item?.let {
            if (!it.isFavorite) {
                it.isFavorite = true
                this.addToFavorite(
                    FavoriteItemEntity(
                        id = it.id
                    )
                )
            } else {
                it.isFavorite = false
                this.removeItemFromFavorite(it)
            }

            this._eventAddFavorite.value = true
        }
    }

    fun eventAddFavoriteComplete() {
        this._eventAddFavorite.value = false
    }

    private fun addToFavorite(favoriteItemEntity: FavoriteItemEntity) {
        viewModelScope.launch {
            repository.insertFavoriteItem(favoriteItemEntity)
        }
    }

    private fun removeItemFromFavorite(item: BarItem) {
        viewModelScope.launch {
            repository.removeItemFromFavorite(item.id)
        }
    }
}