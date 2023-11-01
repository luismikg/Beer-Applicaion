package com.luis.beerapplication.framework.implementations.dataSourceLocal

import com.luis.beerapplication.data.dataSourceBeer.dataSource.LocalDataSource
import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.data.model.ItemEntity
import com.luis.beerapplication.domain.ItemDao
import com.luis.beerapplication.utils.Resource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val itemDao: ItemDao) : LocalDataSource {

    override suspend fun getFavoriteItemsRoom(): Resource<List<FavoriteItemEntity>> {
        return Resource.Success(itemDao.getAllFavoriteItems())
    }

    override suspend fun insertFavoriteItemRoom(favoriteItemEntity: FavoriteItemEntity) {
        itemDao.insertFavoriteItem(favoriteItemEntity)
    }

    override suspend fun insertItemRoom(itemEntity: ItemEntity) {
        itemDao.insertItem(itemEntity)
    }

    override suspend fun getItemsRoom(): Resource<List<ItemEntity>> {
        return Resource.Success(itemDao.getAllItems())
    }

    override suspend fun removeItemFromFavoriteRoom(id: Int) {
        itemDao.removeItemFromFavorite(id)
    }
}