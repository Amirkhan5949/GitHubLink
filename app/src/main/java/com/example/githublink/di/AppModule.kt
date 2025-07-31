package com.example.githublink.di

import com.example.githublink.core.Constants
import com.example.githublink.data.UserRepository
import com.example.githublink.data.UserRepositoryImp
import com.example.githublink.data.remote.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBaseUrl(): String = Constants.Base_url

    @Singleton
    @Provides
    fun providesRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesNetworkCall(retrofit: Retrofit): DataSource =
        retrofit.create(DataSource::class.java)

    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository =
        userRepositoryImp
}