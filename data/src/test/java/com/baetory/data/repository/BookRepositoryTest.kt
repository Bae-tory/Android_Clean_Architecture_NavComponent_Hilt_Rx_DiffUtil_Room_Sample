package com.baetory.data.repository

import com.baetory.data.RepositoryTest
import com.baetory.data.mapper.book.BookEntityMapper
import com.baetory.data.mapper.book.BookInfoEntityMapper
import com.baetory.data.mock.mockdata.MockData
import com.baetory.data.mock.local.MockBookLocalDataSourceTest
import com.baetory.data.mock.remote.MockBookRemoteDataSourceTest
import com.baetory.data.source.local.BookLocalDataSource
import com.baetory.data.source.remote.BookRemoteDataSource
import com.baetory.data.source.repository.BookRepositoryImpl
import com.baetory.domain.BookRepository
import org.junit.Test
import kotlin.test.assertEquals

class BookRepositoryTest : RepositoryTest() {

    /**
     * Repository CRUD 상황을 가정해서 테스트 케이스를 구성한다.
     */
    private lateinit var bookLocalDataSource: BookLocalDataSource
    private lateinit var bookRemoteDataSource: BookRemoteDataSource
    private lateinit var bookEntityMapper: BookEntityMapper
    private lateinit var bookRepository: BookRepository

    override fun setup() {
        super.setup()
        bookLocalDataSource = MockBookLocalDataSourceTest()
        bookRemoteDataSource = MockBookRemoteDataSourceTest()
        bookEntityMapper = BookEntityMapper(BookInfoEntityMapper())
        bookRepository = BookRepositoryImpl(
            bookRemoteDataSource = bookRemoteDataSource,
            bookLocalDataSource = bookLocalDataSource,
            bookEntityMapper = bookEntityMapper
        )
    }

    /*remote*/
    @Test
    fun `BookRepository getBooks 동작 테스트`() {
        val result =
            bookRepository.getBooks(query = "", sort = "", page = 0, target = "").blockingGet()
        assertEquals(true, result.books.isNotEmpty())
        assertEquals(true, result.pageMeta != null)
        assertEquals(MockData.bookSearchDataModel.bookDatas, MockData.bookSearchDataModel.bookDatas)
        assertEquals(
            MockData.bookSearchDataModel.pagingMeta,
            MockData.bookSearchDataModel.pagingMeta
        )
        assertEquals(
            MockData.bookSearchDataModel.bookDatas.count(),
            MockData.bookSearchDataModel.bookDatas.count()
        )
    }

    /*local*/
    @Test
    fun `BookRepository dropBooks 동작 테스트`() {
        bookRepository.dropBooks()
    }

    @Test
    fun `getCachedBooks 동작 테스트`() {
        bookRepository.getCachedBooks()
    }
}