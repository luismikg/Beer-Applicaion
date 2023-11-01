package com.luis.beerapplication.data.dataSourceBeer

import com.luis.beerapplication.data.model.Bar
import com.luis.beerapplication.data.dataSourceBeer.dataSource.LocalDataSource
import com.luis.beerapplication.data.dataSourceBeer.dataSource.RemoteDataSource
import com.luis.beerapplication.data.model.BarItem
import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.data.model.ItemEntity
import com.luis.beerapplication.domain.Repository
import com.luis.beerapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {
    override suspend fun getDataFromServer(page: String): Resource<Bar> {
        return try {
            when (val result = remoteDataSource.getDataFromServer(page)) { // get data from server
                is Resource.Success -> {
                    var bar = orderResult(result.data)

                    //Save local
                    GlobalScope.launch(Dispatchers.IO) {
                        saveBar(bar)
                    }

                    //Set favorites items
                    bar = this.setFavoriteItems(bar)
                    Resource.Success(bar)
                }

                is Resource.Loading -> Resource.Loading()
                is Resource.Failure -> Resource.Failure(result.exception)
            }
        } catch (e: Exception) {
            try { // get data from database
                getItems()
            } catch (exception: Exception) {
                Resource.Failure(e)
            }
        }
    }


    override suspend fun getFavorites(): Resource<List<BarItem>> {

        when (val result = localDataSource.getFavoriteItemsRoom()) {
            is Resource.Success -> {

                //get bar items list object from each FavoriteItemEntity object
                val barItems: ArrayList<BarItem> = result.data.map { favoriteItemEntity ->
                    BarItem(
                        id = favoriteItemEntity.id
                    )
                } as ArrayList<BarItem>

                //order by id
                barItems.sortedBy { it.id }

                return Resource.Success(barItems)
            }

            is Resource.Loading -> return Resource.Loading()
            is Resource.Failure -> return Resource.Failure(result.exception)
        }
    }

    override suspend fun getItems(): Resource<Bar> {
        when (val result = localDataSource.getItemsRoom()) {
            is Resource.Success -> {

                //get a barItem object from each ItemEntity object
                var bar = result.data.map { item ->
                    BarItem(
                        item.abv,
                        item.attenuation_level,
                        item.boil_volume,
                        item.brewers_tips,
                        item.contributed_by,
                        item.description,
                        item.ebc,
                        item.first_brewed,
                        item.food_pairing,
                        item.ibu,
                        item.id,
                        item.photo,
                        item.ingredients,
                        item.method,
                        item.name,
                        item.ph,
                        item.srm,
                        item.tagline,
                        item.target_fg,
                        item.target_og,
                        item.volume,
                        item.isFavorite
                    )
                }

                //order by id
                bar.sortedBy { it.id }
                bar = setFavoriteItems(bar as Bar)

                return Resource.Success(bar)
            }

            is Resource.Loading -> return Resource.Loading()
            is Resource.Failure -> return Resource.Failure(result.exception)
        }
    }

    override suspend fun insertItem(itemEntity: ItemEntity) {
        localDataSource.insertItemRoom(itemEntity)
    }

    override suspend fun insertFavoriteItem(favoriteItemEntity: FavoriteItemEntity) {
        localDataSource.insertFavoriteItemRoom(favoriteItemEntity)
    }

    override suspend fun removeItemFromFavorite(id: Int) {
        localDataSource.removeItemFromFavoriteRoom(id)
    }

    private suspend fun saveBar(bar: Bar) {

        bar.forEach { barItem ->
            val itemEntity = ItemEntity(
                barItem.abv,
                barItem.attenuation_level,
                barItem.boil_volume,
                barItem.brewers_tips,
                barItem.contributed_by,
                barItem.description,
                barItem.ebc,
                barItem.first_brewed,
                barItem.food_pairing,
                barItem.ibu,
                barItem.id,
                barItem.photo,
                barItem.ingredients,
                barItem.method,
                barItem.name,
                barItem.ph,
                barItem.srm,
                barItem.tagline,
                barItem.target_fg,
                barItem.target_og,
                barItem.volume,
                barItem.isFavorite
            )

            insertItem(itemEntity)
        }
    }

    private suspend fun setFavoriteItems(bar: Bar): Bar {
        //get favorites and setup mark as favorite some elements to item parameter
        return when (val resultFavorites = this.getFavorites()) {
            is Resource.Success -> {
                for (favorite in resultFavorites.data) {
                    if (bar.size > favorite.id) {
                        bar[favorite.id].isFavorite = true
                    }
                }

                bar
            }

            else -> bar
        }
    }

    private fun orderResult(bar: Bar): Bar {
        //order by id
        bar.sortedBy { it.id }
        return bar
    }
}