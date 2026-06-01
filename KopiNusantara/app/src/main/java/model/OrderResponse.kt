package com.example.kopinusantara.model

data class OrderResponse(
    val status: String,
    val message: String,
    val data: OrderData
)

data class OrderData(
    val orders: List<OrderItem>
)

data class OrderItem(
    val product_name: String,
    val image: String,
    val price: Int,
    val quantity: Int,
    val subtotal: Int
)