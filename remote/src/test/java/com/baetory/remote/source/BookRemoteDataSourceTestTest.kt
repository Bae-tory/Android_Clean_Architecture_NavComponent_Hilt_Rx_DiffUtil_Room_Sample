package com.baetory.remote.source

import com.baetory.data.exception.NetworkException
import com.baetory.data.source.remote.BookRemoteDataSource
import com.baetory.remote.DataSourceTest
import com.baetory.remote.api.BookApi
import com.baetory.remote.mapper.book.BookResponseMapper
import com.baetory.remote.mock.dispatcher.BookDispatcher
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertTrue

class BookRemoteDataSourceTestTest : DataSourceTest() {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().silent()

    @Mock
    private lateinit var mockBookApi: BookApi
    private lateinit var mockWebServer: MockWebServer
    private lateinit var bookResponseMapper: BookResponseMapper
    private lateinit var bookRemoteDataSource: BookRemoteDataSource

    @Before
    override fun setup() {
        super.setup()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = BookDispatcher()
        mockWebServer.start()

        bookResponseMapper = BookResponseMapper()
        bookRemoteDataSource = BookRemoteDataSourceImpl(
            bookResponseMapper = bookResponseMapper,
            bookApi = mockBookApi
        )
    }

    @After
    override fun tearDown() {
        super.tearDown()
        mockWebServer.shutdown()
    }

    @Test
    fun `정상적으로 데이터를 가져오는지 테스트`() {
        val books =
            bookRemoteDataSource.getBooks("테스트", sort = "accuracy", page = 1, target = "title")
                .blockingGet()
        assertTrue(books.bookDatas.isNotEmpty())
    }

    @Test
    fun `HttpException 400에러 발생할때 캐치할 수 있는지 테스트`() {
        val httpException = HttpException(Response.error<Nothing>(400, "".toResponseBody(null)))
        doReturn(Single.error<Nothing>(httpException))
            .`when`(mockBookApi)
            .queryBooksByTitle(anyString(), anyString(), anyInt())

        BookRemoteDataSourceImpl(
            bookApi = mockBookApi,
            bookResponseMapper = bookResponseMapper
        )
            .getBooks("테스트", sort = "accuracy", page = 1, target = "title") // When
            .test()
            .assertError(NetworkException.BadRequestException::class.java) // Then

    }

    @Test
    fun `HttpException 401에러 발생할때 캐치할 수 있는지 테스트`() {
        val httpException = HttpException(Response.error<Nothing>(401, "".toResponseBody(null)))
        doReturn(Single.error<Nothing>(httpException))
            .`when`(mockBookApi)
            .queryBooksByTitle(anyString(), anyString(), anyInt())

        BookRemoteDataSourceImpl(
            bookApi = mockBookApi,
            bookResponseMapper = bookResponseMapper
        )
            .getBooks("테스트", sort = "accuracy", page = 1, target = "title") // When
            .test()
            .assertError(NetworkException.UnauthorizedException::class.java) // Then
    }

    @Test
    fun `HttpException 404에러 발생할때 캐치할 수 있는지 테스트`() {
        val httpException = HttpException(Response.error<Nothing>(404, "".toResponseBody(null)))
        doReturn(Single.error<Nothing>(httpException))
            .`when`(mockBookApi)
            .queryBooksByTitle(anyString(), anyString(), anyInt())

        BookRemoteDataSourceImpl(
            bookApi = mockBookApi,
            bookResponseMapper = bookResponseMapper
        )
            .getBooks("테스트", sort = "accuracy", page = 1, target = "title") // When
            .test()
            .assertError(NetworkException.NotFoundException::class.java) // Then
    }

}