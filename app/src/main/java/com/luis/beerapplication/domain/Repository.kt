package com.luis.beerapplication.domain

import com.luis.beerapplication.data.model.Bar
import com.luis.beerapplication.data.model.BarItem
import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.data.model.ItemEntity
import com.luis.beerapplication.utils.Resource

interface Repository {
    suspend fun getDataFromServer(page: String): Resource<Bar>
    suspend fun getFavorites(): Resource<List<BarItem>>
    suspend fun removeItemFromFavorite(id: Int)
    suspend fun insertFavoriteItem(favoriteItemEntity: FavoriteItemEntity)
    suspend fun insertItem(itemEntity: ItemEntity)
    suspend fun getItems(): Resource<Bar>
}