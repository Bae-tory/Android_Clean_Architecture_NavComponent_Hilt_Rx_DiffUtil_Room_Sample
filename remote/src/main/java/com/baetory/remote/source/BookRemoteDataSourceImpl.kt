package com.baetory.remote.source

import com.baetory.data.model.BookSearchDataModel
import com.baetory.data.source.remote.BookRemoteDataSource
import com.baetory.remote.api.BookApi
import com.baetory.remote.compose
import com.baetory.remote.mapper.book.BookResponseMapper
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


internal class BookRemoteDataSourceImpl @Inject constructor(
    private val bookResponseMapper: BookResponseMapper,
    private val bookApi: BookApi
) : BookRemoteDataSource {

    override fun getBooks(query: String, sort: String, page: Int, target: String): Single<BookSearchDataModel> =
        bookApi
            .queryBooksByTitle(query = query, sort = sort, page = page, target = target)
            .map { response -> bookResponseMapper.toDataModel(response) }
            .compose()
}