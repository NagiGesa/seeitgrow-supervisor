package com.seeitgrow.supervisor.di

import android.content.Context
import androidx.room.Room
import com.seeitgrow.supervisor.BuildConfig
import com.seeitgrow.supervisor.DataBase.UserDatabase
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.ApiService
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(context, UserDatabase::class.java, "user_database").build()

    @Singleton
    @Provides
    fun provideFarmerDao(db: UserDatabase) = db.farmerDao()


    @Singleton
    @Provides
    fun provideSuperVisorDao(db: UserDatabase) = db.superVisorDao()

    @Singleton
    @Provides
    fun provideRejectedDao(db: UserDatabase) = db.rejectedDao()




}
