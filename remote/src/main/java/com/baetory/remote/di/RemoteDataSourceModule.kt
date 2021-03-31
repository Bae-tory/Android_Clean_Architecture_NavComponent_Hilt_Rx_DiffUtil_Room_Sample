package com.baetory.remote.di

import com.baetory.data.source.remote.BookRemoteDataSource
import com.baetory.remote.api.BookApi
import com.baetory.remote.mapper.book.BookResponseMapper
import com.baetory.remote.source.BookRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(
    includes = [
        ApiModule::class,
        RemoteMapperModule::class,
        RemoteNetworkModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideBookRemoteDataSoruce(
        bookResponseMapper: BookResponseMapper,
        bookApi: BookApi
    ): BookRemoteDataSource =
        BookRemoteDataSourceImpl(
            bookResponseMapper = bookResponseMapper,
            bookApi = bookApi
        )

}