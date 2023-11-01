package com.luis.beerapplication.data.dataSourceBeer.dataSource

import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.data.model.ItemEntity
import com.luis.beerapplication.utils.Resource

interface LocalDataSource {
    suspend fun getFavoriteItemsRoom(): Resource<List<FavoriteItemEntity>>
    suspend fun insertFavoriteItemRoom(favoriteItemEntity: FavoriteItemEntity)
    suspend fun insertItemRoom(itemEntity: ItemEntity)
    suspend fun getItemsRoom(): Resource<List<ItemEntity>>
    suspend fun removeItemFromFavoriteRoom(id: Int)
}