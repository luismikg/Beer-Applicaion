package com.luis.beerapplication.framework.di

import com.luis.beerapplication.data.dataSourceBeer.RepositoryImpl
import com.luis.beerapplication.data.dataSourceBeer.dataSource.LocalDataSource
import com.luis.beerapplication.data.dataSourceBeer.dataSource.RemoteDataSource
import com.luis.beerapplication.domain.Repository
import com.luis.beerapplication.framework.implementations.dataSourceLocal.LocalDataSourceImpl
import com.luis.beerapplication.framework.implementations.dataSourceRemote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds //Indicate the class witch interface "Repository" is implemented
    abstract fun bindRepositoryImpl(repositoryImpl: RepositoryImpl): Repository

    @Binds //Indicate the class witch interface "DataSource" is implemented
    abstract fun bindRemoteDataSourceImpl(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds //Indicate the class witch interface "DataSource" is implemented
    abstract fun bindLocalDataSourceImpl(localDataSource: LocalDataSourceImpl): LocalDataSource
}