package com.hayde117.diaryapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.hayde117.mongo.database.ImagesDatabase
import com.hayde117.util.Constants.IMAGES_DATABASE
import com.hayde117.util.connectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(
//        @ApplicationContext context: Context
//    ): ImagesDatabase {
//        return Room.databaseBuilder(
//            context = context,
//            klass = ImagesDatabase::class.java,
//            name = IMAGES_DATABASE
//        ).build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideFirstDao(database: ImagesDatabase) = database.imageToUploadDao()
//
//
//    @Singleton
//    @Provides
//    fun provideSecondDao(database: ImagesDatabase) = database.imageToDeleteDao()
//
//    @Singleton
//    @Provides
//    fun provideNetworkConnectivityObserver(
//        @ApplicationContext context: Context
//    ) = NetworkConnectivityObserver(context = context)
//}

import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),  // get() gets the application context
            ImagesDatabase::class.java,
            IMAGES_DATABASE
        ).build()
    }

    single { get<ImagesDatabase>().imageToUploadDao() }
    single { get<ImagesDatabase>().imageToDeleteDao() }

    single { NetworkConnectivityObserver(get<Application>()) }
}
