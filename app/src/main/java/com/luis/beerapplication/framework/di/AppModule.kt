package com.luis.beerapplication.framework.di

import android.content.Context
import androidx.room.Room
import com.luis.beerapplication.data.config.DatabaseModule
import com.luis.beerapplication.framework.presentation.main.adapters.MainAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMainAdapter(
        @ApplicationContext context: Context
    ) = MainAdapter(context)

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            DatabaseModule::class.java,
            "Bar"
        ).build()


    @Singleton
    @Provides
    fun provideItemDao(db: DatabaseModule) = db.itemDao()
}