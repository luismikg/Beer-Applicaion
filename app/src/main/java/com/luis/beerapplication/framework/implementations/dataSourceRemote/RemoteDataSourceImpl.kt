package com.luis.beerapplication.framework.implementations.dataSourceRemote

import com.luis.beerapplication.domain.ItemDao
import com.luis.beerapplication.data.config.NetworkModule
import com.luis.beerapplication.data.dataSourceBeer.dataSource.RemoteDataSource
import com.luis.beerapplication.data.model.Bar
import com.luis.beerapplication.utils.Resource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val itemDao: ItemDao) : RemoteDataSource {

    override suspend fun getDataFromServer(page: String): Resource<Bar> {
        return Resource.Success(NetworkModule.webService.getData(page))
    }
}