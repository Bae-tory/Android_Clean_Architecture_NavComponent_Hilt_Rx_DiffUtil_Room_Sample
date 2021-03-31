package com.baetory.remote.mapper.book

import com.baetory.data.model.BookDataModel
import com.baetory.data.model.BookPagingKeyDataModel
import com.baetory.data.model.BookSearchDataModel
import com.baetory.remote.mapper.RemoteMapper
import com.baetory.remote.model.book.BookSearchResponse
import javax.inject.Inject

class BookResponseMapper @Inject constructor(
) : RemoteMapper<BookSearchResponse, BookSearchDataModel> {

    override fun toDataModel(response: BookSearchResponse): BookSearchDataModel =
        BookSearchDataModel(
            response.bookDocuments
                .map {
                    BookDataModel(
                        authors = it.authors,
                        translators = it.bookTranslators,
                        isEnd = response.searchMetaData.isEnd,
                        contents = it.contents,
                        dateTime = it.dateTime,
                        bookNumber = it.bookNumber,
                        price = it.price,
                        publisher = it.publisher,
                        salePrice = it.salePrice,
                        saleStatus = it.saleStatus,
                        thumbnailImageUrl = it.thumbnailImageUrl,
                        title = it.title,
                        bookDetailUrl = it.bookDetailUrl
                    )
                },
            BookPagingKeyDataModel(
                response.searchMetaData.isEnd,
                response.searchMetaData.pageableCount,
                response.searchMetaData.totalCount
            )
        )
}