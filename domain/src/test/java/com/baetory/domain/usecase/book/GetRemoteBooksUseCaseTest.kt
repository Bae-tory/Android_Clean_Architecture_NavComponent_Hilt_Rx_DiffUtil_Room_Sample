package com.baetory.domain.usecase.book

import com.baetory.domain.BookRepository
import com.baetory.domain.UseCaseTest
import com.baetory.domain.exception.EntityNotFoundException
import com.baetory.mock.MockEntity
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertEquals

class GetRemoteBooksUseCaseTest : UseCaseTest() {

    private lateinit var getRemoteBooksUseCase: GetRemoteBooksUseCase
    private lateinit var searchParams: SearchParams

    @Mock
    private lateinit var bookRepository: BookRepository

    override fun setup() {
        super.setup()
        getRemoteBooksUseCase = GetRemoteBooksUseCase(
            bookRepository = bookRepository,
            executorProvider = testExecutors
        )
        searchParams = SearchParams(query = "", sort = "", page = 0, target = "")
    }

    @Test
    fun `정상 작동 여부 테스트`() {
        Mockito.`when`(
            bookRepository.getBooks(
                searchParams.query,
                searchParams.sort,
                searchParams.page,
                searchParams.target
            )
        )
            .thenReturn(Single.just(MockEntity.bookEntity))

        val result = getRemoteBooksUseCase.execute(searchParams).blockingGet()
        assertEquals(result.books.first(), MockEntity.bookEntity.books.first())
    }

    @Test
    fun `에러 발생시 캐치 여부 테스트`() {
        val exception = EntityNotFoundException("this is test exception")
        Mockito.`when`(
            bookRepository.getBooks(
                searchParams.query,
                searchParams.sort,
                searchParams.page,
                searchParams.target
            )
        ).thenReturn(Single.error(exception))

        getRemoteBooksUseCase.execute(searchParams)
            .test()
            .assertError(exception)
    }
}