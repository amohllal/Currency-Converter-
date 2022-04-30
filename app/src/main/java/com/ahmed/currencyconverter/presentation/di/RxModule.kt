package com.ahmed.currencyconverter.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RxModule {

    @Provides
    @Named("IO")
    fun provideIOScheduler() = Schedulers.io()

    @Provides
    @Named("Main")
    fun provideMainScheduler() = AndroidSchedulers.mainThread()


}
