package com.baetory.remote.mock.dispatcher

import com.baetory.remote.mock.getJsonFromFile
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection

internal class BookDispatcher : Dispatcher(){

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when{
            (request.path?.startsWith("/v3/search/book") == true) ->
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJsonFromFile("response/get_books_response.json"))

            else-> throw IllegalArgumentException("invalid request path: ${request.path}")
        }
    }
}