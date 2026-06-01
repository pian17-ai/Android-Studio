package com.example.kopinusantara.model

import com.example.kopinusantara.Product

data class ProductResponse(
    val status: String,
    val data: List<Product>
)