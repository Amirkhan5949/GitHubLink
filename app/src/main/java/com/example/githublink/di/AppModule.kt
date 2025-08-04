package com.example.githublink.di

import android.content.Context
import androidx.room.Room
import com.example.githublink.core.Constants
import com.example.githublink.data.UserRepository
import com.example.githublink.data.UserRepositoryImp
import com.example.githublink.data.remote.DataSource
import com.example.githublink.local.GitUserDataBase
import com.example.githublink.local.dao.GitUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Singleton
    @Provides
    @Named("ROOM_DB")
    fun provideDbName(): String = Constants.ROOM_DB

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context,
        @Named("ROOM_DB") name: String,
    )
            : GitUserDataBase =
        Room.databaseBuilder(context, GitUserDataBase::class.java, name).build()

    @Singleton
    @Provides
    fun provideRepoDao(db : GitUserDataBase) : GitUserDao = db.gitUserRepoDao()

}




