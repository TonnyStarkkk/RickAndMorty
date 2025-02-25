package com.example.aidar_hw_5_2.data.di

import android.content.Context
import androidx.room.Room
import com.example.aidar_hw_5_2.BuildConfig
import com.example.aidar_hw_5_2.data.api.ApiService
import com.example.aidar_hw_5_2.data.local.CharactersDao
import com.example.aidar_hw_5_2.data.local.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CharactersDatabase {
        return Room.databaseBuilder(
            context,
            CharactersDatabase::class.java,
            "characters_db"
        ).build()
    }

    @Provides
    fun provideCharactersDao(database: CharactersDatabase): CharactersDao {
        return database.dao()
    }
}