package com.seeitgrow.supervisor.di

import android.content.Context
import androidx.room.Room
import com.seeitgrow.supervisor.DataBase.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
