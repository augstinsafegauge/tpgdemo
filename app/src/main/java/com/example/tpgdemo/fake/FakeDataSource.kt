package com.example.tpgdemo.fake

import com.example.tpgdemo.model.CatsPhoto

object FakeDataSource {

    private const val idOne = "img1"
    private const val idTwo = "img2"
    private const val imgOne = "url.one"
    private const val imgTwo = "url.two"
    val photosList = mutableListOf(
        CatsPhoto(
            id = idOne,
            imageUrl = imgOne
        ),
        CatsPhoto(
            id = idTwo,
            imageUrl = imgTwo
        )
    )

}