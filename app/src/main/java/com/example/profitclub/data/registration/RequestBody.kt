package com.example.profitclub.data.registration

data class SetRegionId(
    val region_id: Int
)

data class PostCityBody(
    val sfilter: SetRegionId,
    val lang: String = "en",
    private val token: String = "45b28bfb"
)

data class PostRegionBody(
    val lang: String = "en",
    private val token: String = "3607ac33"
)

data class PostCategoryBody(
    val lang: String = "en",
    private val token: String = "249340fe"
)