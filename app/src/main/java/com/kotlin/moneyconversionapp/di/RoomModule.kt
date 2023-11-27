package com.kotlin.moneyconversionapp.di

import android.content.Context
import androidx.room.Room
import com.kotlin.moneyconversionapp.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    const val CASA_DATABASE_NAME = "casa_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, CASA_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCasaDao(db: AppDataBase) = db.getCasaDao()
}