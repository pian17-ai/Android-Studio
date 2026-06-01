package com.example.kopinusantara.model

data class ProductDetailResponse(
    val status: String,
    val message: String,
    val data: ProductDetail
)

data class ProductDetail(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val image_url: String,
    val category: String
)