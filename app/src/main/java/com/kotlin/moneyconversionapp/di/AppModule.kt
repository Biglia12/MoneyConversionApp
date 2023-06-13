package com.kotlin.moneyconversionapp.di

import com.kotlin.moneyconversionapp.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("baseUrlDolarSi")
    fun provideBaseUrlDolarSi(): String {
        return Constants.BASE_URL_DOLARSI
    }

    @Provides
    @Named("baseUrlHistoric")
    fun provideBaseUrlOther(): String {
        return Constants.BASE_URL_HISTORIC
    }

}