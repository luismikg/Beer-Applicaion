package com.luis.beerapplication.data.dataSourceBeer.dataSource

import com.luis.beerapplication.data.model.Bar
import com.luis.beerapplication.utils.Resource

interface RemoteDataSource {
    suspend fun getDataFromServer(page: String): Resource<Bar>
}