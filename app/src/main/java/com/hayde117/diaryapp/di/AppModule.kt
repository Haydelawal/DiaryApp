package com.hayde117.diaryapp.di

import com.hayde117.mongo.database.ImagesDatabase
import com.hayde117.util.connectivity.NetworkConnectivityObserver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.hayde117.home.HomeViewModel
import com.hayde117.write.WriteViewModel

val appModule = module {
    // Provide the HomeViewModel
    viewModel { HomeViewModel(get(), get()) }
    viewModel { WriteViewModel(get(), get(), get()) }

    // Provide other dependencies like NetworkConnectivityObserver and DAOs
    single { NetworkConnectivityObserver(get()) }
    single { get<ImagesDatabase>().imageToDeleteDao() }
    single { get<ImagesDatabase>().imageToUploadDao() }
}
