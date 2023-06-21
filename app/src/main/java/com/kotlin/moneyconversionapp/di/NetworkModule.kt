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


    @Singleton
    @Provides
    @Named("baseUrlDolarSi")
    fun provideRetrofit(): Retrofit{
        return createRetrofit(Constants.BASE_URL_DOLARSI)
    }

    @Singleton
    @Provides
    @Named("baseurlHistoric")
    fun provideRetrofitHistoric(): Retrofit{
        return createRetrofit(Constants.BASE_URL_HISTORIC)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("baseUrlDolarSi")
    fun provideServices(@Named("baseUrlDolarSi")retrofit: Retrofit): Services{
        return retrofit.create(Services::class.java)
    }

    @Singleton
    @Provides
    @Named("baseurlHistoric")
    fun provideServicesHistoric(@Named("baseurlHistoric")retrofit: Retrofit): Services{
        return retrofit.create(Services::class.java)
    }


}