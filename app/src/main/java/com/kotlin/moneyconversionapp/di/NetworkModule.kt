package com.kotlin.moneyconversionapp.di

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.services.Services
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

   /* @Singleton
    @Provides
    @Named(Constants.BASE_URL_DOLARSI)
    fun provideBaseUrlDolarSi(): String {
        return Constants.BASE_URL_DOLARSI
    }

    @Singleton
    @Provides
    @Named(Constants.BASE_URL_HISTORIC)
    fun provideBaseUrlOtroServicio(): String {
        return Constants.BASE_URL_HISTORIC
    }*/

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideServices(retrofit: Retrofit): Services{
        return retrofit.create(Services::class.java)
    }


}