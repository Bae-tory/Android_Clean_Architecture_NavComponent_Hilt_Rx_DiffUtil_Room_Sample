package com.baetory.mock

import com.baetory.data.model.BookDataModel
import com.baetory.local.room.BookRoomObject
import java.util.*

object MockLocalData {

    val bookRoomObject1 = BookRoomObject(
        id = 1,
        authors = listOf("작가1", "작가2"),
        translators = listOf("번역가1", "번역가2"),
        isEnd = false,
        contents = "컨텐츠입니다.",
        dateTime = Date(),
        bookNumber = "책번호",
        price = 100,
        publisher = "출판사",
        salePrice = "100",
        saleStatus = "판매중",
        bookImageUrl = "imageUrl1",
        title = "제목",
        bookDetailUrl = "detailImageUrl"
    )

    val bookDataModel = BookDataModel(
        authors = listOf("작가1", "작가2"),
        translators = listOf("번역가1", "번역가2"),
        isEnd = false,
        contents = "컨텐츠입니다.",
        dateTime = Date(),
        bookNumber = "책번호",
        price = 100,
        publisher = "출판사",
        salePrice = "100",
        saleStatus = "판매중",
        thumbnailImageUrl = "imageUrl1",
        title = "제목",
        bookDetailUrl = "detailImageUrl"
    )
    val bookRoomObjects = listOf(bookRoomObject1, bookRoomObject1)
    val bookDataModels = listOf(bookDataModel, bookDataModel)
}